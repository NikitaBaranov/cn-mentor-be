package tech.baranov.cnmentor.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tech.baranov.cnmentor.models.GitHubEvent;
import tech.baranov.cnmentor.models.Student;
import tech.baranov.cnmentor.services.StudentService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class StudentsController {

    private final StudentService studentService;

    @GetMapping("/students")
    public List<Student> getAll() {
        return studentService.getAll();
    }

    @GetMapping("/students/{id}")
    public Student get(@PathVariable Integer id) {
        return studentService.get(id);
    }

    @GetMapping("/students/{id}/gitHubEvents")
    public List<GitHubEvent> getGitHubEvents(@PathVariable Integer id) {
        return studentService.getStudentGitHubEvents(id);
    }

    @GetMapping("/students/update")
    public List<Student> update() {
        return studentService.updateAll();
    }

    @GetMapping("/students/{studentId}/update/course/{courseId}")
    public Student update(@PathVariable Integer studentId, @PathVariable Integer courseId) {
        return studentService.update(studentId, courseId);
    }

    @PostMapping("/students")
    public Student create(@RequestBody Student students) {
        return studentService.create(students);
    }

    @PutMapping("/students")
    public Student edit(@RequestBody Student students) {
        return studentService.edit(students);
    }

    @DeleteMapping("/students/{id}")
    public void delete(@PathVariable Integer id) {
        studentService.delete(id);
    }

}
