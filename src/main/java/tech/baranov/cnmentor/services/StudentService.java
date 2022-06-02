package tech.baranov.cnmentor.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.baranov.cnmentor.exceptions.StudentNotFoundException;
import tech.baranov.cnmentor.models.Course;
import tech.baranov.cnmentor.models.Progress;
import tech.baranov.cnmentor.models.Student;
import tech.baranov.cnmentor.repositories.StudentsRepository;

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
    private final OutlineReportService outlineReportService;

    public List<Student> getAll() {
        return studentsRepository.findAll();
    }

    public Student get(Integer id) {
        return studentsRepository.findById(id).orElseThrow(StudentNotFoundException::new);
    }

    public List<Student> create(List<Student> students) {
        for (Student student : students) {
            student.setCoursesProgress(new HashMap<>());
            studentsRepository.insert(student);
        }
        return getAll();
    }

    public List<Student> updateAll() {
        log.info("updateAll");

        List<Course> courses = courseService.getAll();

        log.info("create futures");
        List<CompletableFuture<Student>> completableFutures = studentsRepository.findAll().stream()
                .map(student -> courses.stream()
                        .map(course -> CompletableFuture.supplyAsync(() -> update(student, course.getId())))
                        .collect(Collectors.toList()))
                .flatMap(List::stream)
                .collect(Collectors.toList());


        log.info("join futures");
        completableFutures.forEach(CompletableFuture::join);

        return getAll();
    }

    public Student update(Integer studentId, Integer courseId) {
        Student student = studentsRepository.findById(studentId).orElse(null);
        return update(student, courseId);
    }

    public Student update(Student student, Integer courseId) {
        Progress progress = outlineReportService.update(student.getId(), courseId);

        if (student.getCoursesProgress() == null) {
            student.setCoursesProgress(new HashMap<>());
        }

        student.getCoursesProgress().put(courseId, progress);

        studentsRepository.save(student);
        return student;
    }

    public void delete(Integer studentId) {
        studentsRepository.deleteById(studentId);
    }

}
