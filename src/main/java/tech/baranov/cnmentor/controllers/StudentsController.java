package tech.baranov.cnmentor.controllers;

import org.springframework.web.bind.annotation.*;
import tech.baranov.cnmentor.models.Student;
import tech.baranov.cnmentor.services.StudentService;

import java.util.List;

@RestController
//@CrossOrigin(origins = "*")
public class StudentsController {

    private final StudentService studentService;

    public StudentsController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/students")
    public List<Student> getAll() {
        return studentService.getAll();
    }

    @GetMapping("/students/{id}")
    public Student get(@PathVariable Integer id) {
        return studentService.get(id);
    }

    @GetMapping("/students/update")
    public List<Student> update() {
        return studentService.updateAll();
    }

    @GetMapping("/students/{studentId}/update/course/{courseId}")
    public Student update(@PathVariable Integer studentId,
                          @PathVariable Integer courseId) {
        return studentService.update(studentId, courseId);
    }

    @PostMapping("/students")
    public List<Student> create(@RequestBody List<Student> students) {
        return studentService.create(students);
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping("/students/{id}")
    public void delete(@PathVariable Integer id) {
        studentService.delete(id);
    }

}
