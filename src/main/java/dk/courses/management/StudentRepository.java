package dk.courses.management;

import org.springframework.data.repository.CrudRepository;


public interface StudentRepository extends CrudRepository<Student, Integer>
{
    Student findByName(String name);
}
