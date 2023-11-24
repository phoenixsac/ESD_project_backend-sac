package com.example.backend.controller;
import com.example.backend.exception.CourseNotFoundException;
import com.example.backend.repository.CourseRepository;
import com.example.backend.model.Course;
import com.example.backend.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("http://localhost:5173")
public class CourseController {
    @Autowired
    private CourseService courseService;
    @Autowired
    CourseRepository courseRepository;
    @PostMapping("/course")
    Course newCourse(@RequestBody Course newCourse){
        return courseService.addCourse(newCourse);
    }

    @GetMapping("/courses")
    private List<Course> getAllCourses(){
        return courseService.getAllCourses();
    }

    @GetMapping("/course/{course_id}")
    Course getCourseById(@PathVariable Long course_id) {
        return courseRepository.findById(course_id)
                .orElseThrow(() -> new CourseNotFoundException(course_id));
    }

    @PutMapping("/course/{course_id}")
    Course updateCourse(@RequestBody Course newCourse, @PathVariable Long course_id) {
        return courseRepository.findById(course_id)
                .map(course -> {
                    course.setFaculty(newCourse.getFaculty());
                    course.setName(newCourse.getName());
                    course.setCourse_code(newCourse.getCourse_code());
                    course.setCredits(newCourse.getCredits());
                    course.setDescription(newCourse.getDescription());
                    course.setYear(newCourse.getYear());
                    course.setTerm(newCourse.getTerm());
                    course.setName(newCourse.getName());
                    course.setCapacity(newCourse.getCapacity());
                    return courseRepository.save(course);
                }).orElseThrow(() -> new CourseNotFoundException(course_id));
    }

    @DeleteMapping("/course/{course_id}")
    String deleteCourse(@PathVariable Long course_id) {
        if (!courseRepository.existsById(course_id)) {
            throw new CourseNotFoundException(course_id);
        }
        courseRepository.deleteById(course_id);
        return "course with id " + course_id + " has been deleted!.";
    }
}

