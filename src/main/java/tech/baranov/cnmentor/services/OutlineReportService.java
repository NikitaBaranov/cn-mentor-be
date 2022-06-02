package tech.baranov.cnmentor.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import tech.baranov.cnmentor.enums.TopicType;
import tech.baranov.cnmentor.models.Progress;
import tech.baranov.cnmentor.models.Section;
import tech.baranov.cnmentor.models.Topic;
import tech.baranov.cnmentor.properties.MoodleProperties;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Slf4j
public class OutlineReportService {

    private final Pattern topicTypePattern = Pattern.compile("maker\\/(?<type>[a-z]+)\\/(?<id>[0-9]+)\\/icon");
    private final Pattern dateTimePattern = Pattern.compile("^[A-Za-z]+,(?<dateTime>[^(]+)");

    private final MoodleProperties moodleProperties;

    private Connection moodleConnection;

    public Progress update(Integer studentId, Integer courseId) {
        log.info("Student {}, course {} - updating course progress", studentId, courseId);
        Progress progress = new Progress();
        progress.setCourseId(courseId);

        Document outline;

        try {
            log.info("Student {}, course {} - fetching data", studentId, courseId);
            outline = getConnection().newRequest()
                    .timeout(100000)
                    .url(String.format(moodleProperties.getUrl() + "/report/outline/user.php?id=%s&course=%s&mode=outline", studentId, courseId))
                    .get();
            log.info(outline.location());

        } catch (IOException e) {
            log.error(String.format("Student %s, course %s - failed to fetch", studentId, courseId), e);
            throw new RuntimeException(e);
        }

        log.info("Student {}, course {} - parsing", studentId, courseId);
        Elements sections = outline.getElementsByAttributeValue("role", "main")
                .get(0)
                .getElementsByClass("section");

        int sectionOrder = 0;
        List<Section> sectionList = new LinkedList<>();

        for (Element sectionEl : sections) {

            Section section = new Section();
            section.setOrder(sectionOrder++);
            section.setName(sectionEl.getElementsByTag("h2").get(0).wholeText());

            int topicOrder = 0;
            for (Element row : sectionEl.getElementsByTag("tr")) {
                Elements cells = row.getElementsByTag("td");

                Topic topic = new Topic();
                topic.setOrder(topicOrder++);

                Matcher topicTypeMatcher = topicTypePattern.matcher(cells.get(0).getElementsByTag("img").get(0).attr("src").strip());
                if (topicTypeMatcher.find()) {
                    topic.setType(TopicType.valueOf(topicTypeMatcher.group("type").toUpperCase()));
                    topic.setImgId(Integer.parseInt(topicTypeMatcher.group("id")));
                }
                topic.setTopic(cells.get(1).getElementsByTag("a").get(0).wholeText().strip());

                if (cells.size() > 5) {
                    topic.setComment(cells.get(3).wholeText().strip());
                    topic.setDate(parseDate(cells.get(5).wholeText().strip()));
                }

                if (section.getTopics() == null) {
                    section.setTopics(new ArrayList<>());
                }

                section.getTopics().add(topic);
            }
            sectionList.add(section);
        }
        progress.setSections(sectionList);
        log.info("Student {}, course {} - done", studentId, courseId);

        return progress;
    }

    private synchronized Connection getConnection() {
        if (!Objects.isNull(moodleConnection)) {
            try {
                Document my = moodleConnection.newRequest().url(moodleProperties.getUrl() + "/my").get();
                if (my.location().endsWith("/my/")) {
                    log.info("Use current connection");
                    return moodleConnection;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        log.info("Create new connection");
        Connection connection = Jsoup.newSession();
        try {
            Document document = connection.newRequest().url(moodleProperties.getUrl() + "/login/index.php").get();
            String sessionToken = document.getElementsByAttributeValue("name", "logintoken").get(0).val();

            connection.newRequest()
                    .url(moodleProperties.getUrl() + "/login/index.php")
                    .data("username", moodleProperties.getUsername())
                    .data("password", moodleProperties.getPassword())
                    .data("logintoken", sessionToken)
                    .post();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        moodleConnection = connection;
        return moodleConnection;
    }

    private LocalDateTime parseDate(String str) {
        Matcher dateTimeMatcher = dateTimePattern.matcher(str);
        if (dateTimeMatcher.find()) {
            return LocalDateTime.parse(dateTimeMatcher.group("dateTime").strip(), DateTimeFormatter.ofPattern("d MMMM yyyy, h:mm a").withLocale(Locale.US));
        }
        return null;
    }

}
