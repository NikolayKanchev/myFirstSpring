package dk.courses.management;

import org.springframework.data.repository.CrudRepository;

public interface CourseRepository extends CrudRepository<Course, Integer>
{
    Course findByCourseName(String courseName);
}
