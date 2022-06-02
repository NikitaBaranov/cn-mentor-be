package tech.baranov.cnmentor.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import tech.baranov.cnmentor.models.Course;

public interface CourseRepository extends MongoRepository<Course, Integer> {
}
