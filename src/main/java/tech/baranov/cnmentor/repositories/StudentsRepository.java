package tech.baranov.cnmentor.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import tech.baranov.cnmentor.models.Student;

public interface StudentsRepository extends MongoRepository<Student, Integer> {
}
