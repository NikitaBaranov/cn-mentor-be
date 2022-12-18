package tech.baranov.cnmentor.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.baranov.cnmentor.exceptions.StudentNotFoundException;
import tech.baranov.cnmentor.models.Course;
import tech.baranov.cnmentor.models.GitHubEvent;
import tech.baranov.cnmentor.models.Progress;
import tech.baranov.cnmentor.models.Student;
import tech.baranov.cnmentor.repositories.StudentsRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentService {

    private final StudentsRepository studentsRepository;

    private final CourseService courseService;
    private final GitHubService gitHubService;
    private final OutlineReportService outlineReportService;

    public List<Student> getAll() {
        return studentsRepository.findAll();
    }

    public Student get(Integer id) {
        return studentsRepository.findById(id).orElseThrow(StudentNotFoundException::new);
    }

    public List<GitHubEvent> getStudentGitHubEvents(Integer id) {
        if (get(id).getGitHubUser() == null) {
            return new ArrayList<>();
        }
        return gitHubService.getStudentEvents(get(id).getGitHubUser().getId());
    }

    public Student create(Student student) {
        student.setCoursesProgress(new HashMap<>());
        student.setGitHubUser(gitHubService.fetchUser(student.getGutHubUserName()));
        student.setGitHubRepos(gitHubService.fetchRepos(student.getGutHubUserName()));
        return studentsRepository.insert(student);
    }

    public Student edit(Student student) {
        student.setGitHubUser(gitHubService.fetchUser(student.getGutHubUserName()));
        student.setGitHubRepos(gitHubService.fetchRepos(student.getGutHubUserName()));

        return studentsRepository.save(student);
    }

    public List<Student> updateAll() {
        log.info("updateAll");

        List<Course> courses = courseService.getAll();

        log.info("create futures");
        List<CompletableFuture<Student>> updateCN = studentsRepository.findAll().stream()
                .map(student -> courses.stream()
                        .map(course -> CompletableFuture.supplyAsync(() -> update(student, course.getId())))
                        .collect(Collectors.toList()))
                .flatMap(List::stream)
                .collect(Collectors.toList());

        log.info("join futures");
        updateCN.forEach(CompletableFuture::join);

        studentsRepository.findAll().forEach(student -> {
            student.setGitHubUser(gitHubService.fetchUser(student.getGutHubUserName()));
            student.setGitHubRepos(gitHubService.fetchRepos(student.getGutHubUserName()));
            studentsRepository.save(student);

            gitHubService.updateGitHubEvents(student.getGutHubUserName());
        });

        return getAll();
    }

    public Student update(Integer studentId, Integer courseId) {
        Student student = studentsRepository.findById(studentId).orElseThrow(StudentNotFoundException::new);
        return update(student, courseId);
    }

    public Student update(Student student, Integer courseId) {
        Progress progress = outlineReportService.update(student.getId(), courseId);

        if (student.getCoursesProgress() == null) {
            student.setCoursesProgress(new HashMap<>());
        }

        student.getCoursesProgress().put(courseId, progress);

        student.setGitHubUser(gitHubService.fetchUser(student.getGutHubUserName()));
        student.setGitHubRepos(gitHubService.fetchRepos(student.getGutHubUserName()));

        studentsRepository.save(student);

        gitHubService.updateGitHubEvents(student.getGutHubUserName());

        return student;
    }

    public void delete(Integer studentId) {
        studentsRepository.deleteById(studentId);
    }

}
