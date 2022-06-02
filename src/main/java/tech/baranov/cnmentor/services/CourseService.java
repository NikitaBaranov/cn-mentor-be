package tech.baranov.cnmentor.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.baranov.cnmentor.exceptions.CourseNotFoundException;
import tech.baranov.cnmentor.models.Course;
import tech.baranov.cnmentor.repositories.CourseRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;

    public List<Course> getAll() {
        return courseRepository.findAll();
    }

    public Course get(Integer id) {
        return courseRepository.findById(id).orElseThrow(CourseNotFoundException::new);
    }

    public List<Course> create(List<Course> courses) {
        List<Course> allCourses = courseRepository.findAll();
        for (Course course : courses) {
            if (!allCourses.contains(course)) {
                courseRepository.insert(course);
            }
        }
        return getAll();
    }

    public void delete(Integer id) {
        courseRepository.deleteById(id);
    }
}
