package dk.courses.management;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
@Controller
public class StudentController
{

    @Autowired
    private StudentRepository studentRepo;

    @Autowired
    private CourseRepository courseRepo;

    @GetMapping("/")
    public String firstScreen()
    {
        return "index";
    }

    @GetMapping("/student")
    public String studentView(Model model)
    {
        model.addAttribute("courses", courseRepo.findAll());
        return "student";
    }

    @PostMapping("/student/add")
    public String addStudent(
            @RequestParam(name = "name", defaultValue = "NO_NAME")
            String name,
            @RequestParam(name = "email", defaultValue = "NO_EMAIL")
            String email,
            @RequestParam(name = "courseName", defaultValue = "NO_COURSENAME")
            String courseName,
            Model model)
    {

        Course course = courseRepo.findByCourseName(courseName);

        if (course == null)
        {
            course = new Course();
            course.setCourseName(courseName);
            courseRepo.save(course);
        }

        Student student = new Student();
        student.setName(name);
        student.setEmail(email);
        student.setCourse(course);
        studentRepo.save(student);

        model.addAttribute("students", studentRepo.findAll());

        return "studentData";
    }

    @GetMapping("/students/modify")
    public String deleteStudent(@RequestParam(name = "id", defaultValue = "NO_NAME")
                                            String studentId,
                                @RequestParam(name = "modify", defaultValue = "null")
                                            String modify, Model model)
    {

        int id = Integer.parseInt(studentId);

        System.out.println("******************"+ id +"***************"+ modify +"**********");

        if(modify.equals("delete"))
        {
            studentRepo.delete(id);
        }else
        {
            model.addAttribute(studentRepo.findOne(id));
            model.addAttribute("courses", courseRepo.findAll());
            return "studentUpdate";
        }

        model.addAttribute("students", studentRepo.findAll());

        return "studentData";
    }

    @PostMapping("/student/update")
    public String updateStudent(
            @RequestParam(name = "id", defaultValue = "NO_ID")
                    int id,
            @RequestParam(name = "name", defaultValue = "NO_NAME")
                    String name,
            @RequestParam(name = "email", defaultValue = "NO_EMAIL")
                    String email,
            @RequestParam(name = "courseName", defaultValue = "NO_COURSE")
                    String courseName,
            Model model)
    {

        Course course = courseRepo.findByCourseName(courseName);

        if (course == null)
        {
            course = new Course();
            course.setCourseName(courseName);
            courseRepo.save(course);
        }

        Student student = studentRepo.findOne(id);
        student.setName(name);
        student.setEmail(email);
        student.setCourse(course);
        studentRepo.save(student);

        model.addAttribute("students", studentRepo.findAll());

        return "studentData";
    }
}
