package tech.baranov.cnmentor.clients;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tech.baranov.cnmentor.models.GitHubEvent;
import tech.baranov.cnmentor.models.GitHubRepo;
import tech.baranov.cnmentor.models.GitHubUser;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class GitHubClient {

    private final RestTemplate gitHubRestTemplate;

    public GitHubUser fetchUser(String userName) {
        if (userName == null || userName.isEmpty()) {
            return null;
        }

        log.info("fetch user {} - start", userName);
        ResponseEntity<GitHubUser> response = gitHubRestTemplate.getForEntity("https://api.github.com/users/{userName}", GitHubUser.class, userName);
        log.info("fetch user {} - done, gh limit left {}", userName, response.getHeaders().get("X-RateLimit-Remaining"));

        return response.getBody();
    }

    public List<GitHubRepo> fetchRepos(String userName) {
        if (userName == null || userName.isEmpty()) {
            return null;
        }

        //Query parameters
        //Name, Type, Description
        //typestring
        //Limit results to repositories of the specified type.
        //
        //Default: owner
        //
        //Can be one of: all, owner, member
        //
        //sortstring
        //The property to sort the results by.
        //
        //Default: full_name
        //
        //Can be one of: created, updated, pushed, full_name
        //
        //directionstring
        //The order to sort by. Default: asc when using full_name, otherwise desc.
        //
        //Can be one of: asc, desc
        //
        //per_pageinteger
        //The number of results per page (max 100).
        //
        //Default: 30
        //
        //pageinteger
        //Page number of the results to fetch.
        //
        //Default: 1


        log.info("fetch repos for user {} - start", userName);
        ResponseEntity<GitHubRepo[]> response = gitHubRestTemplate.getForEntity("https://api.github.com/users/{userName}/repos", GitHubRepo[].class, userName);
        log.info("fetch repos for user {} - done, gh limit left {}", userName, response.getHeaders().get("X-RateLimit-Remaining"));

        return Arrays.stream(response.getBody()).sorted(Comparator.comparing(GitHubRepo::getCreatedAt)).collect(Collectors.toList());
    }

    public List<GitHubEvent> fetchGitHubEvents(String userName) {
        //Query parameters
        //Name, Type, Description
        //per_pageinteger
        //The number of results per page (max 100).
        //
        //Default: 30
        //
        //pageinteger
        //Page number of the results to fetch.
        //
        //Default: 1

        log.info("fetch events for user {} - start", userName);
        ResponseEntity<GitHubEvent[]> response = gitHubRestTemplate.getForEntity("https://api.github.com/users/{userName}/events", GitHubEvent[].class, userName);
        log.info("fetch events for user {} - done, gh limit left {}", userName, response.getHeaders().get("X-RateLimit-Remaining"));

        return Arrays.stream(response.getBody()).collect(Collectors.toList());
    }

}
