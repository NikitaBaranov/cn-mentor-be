package tech.baranov.cnmentor.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tech.baranov.cnmentor.models.Course;
import tech.baranov.cnmentor.services.CourseService;

import java.util.List;

@RestController
//@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class CoursesController {

    private final CourseService courseService;

    @GetMapping("/courses")
    public List<Course> getAll() {
        return courseService.getAll();
    }

    @GetMapping("/courses/{id}")
    public Course get(@PathVariable Integer id) {
        return courseService.get(id);
    }

    @PostMapping("/courses")
    public List<Course> create(@RequestBody List<Course> courses) {
        return courseService.create(courses);
    }

    @DeleteMapping("/courses/{id}")
    @CrossOrigin(origins = "*")
    public void delete(@PathVariable Integer id) {
        courseService.delete(id);
    }

}
