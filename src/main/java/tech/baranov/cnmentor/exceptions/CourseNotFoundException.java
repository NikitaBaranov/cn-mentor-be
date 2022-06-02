package tech.baranov.cnmentor.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Course not found")
public class CourseNotFoundException extends RuntimeException {
}