package application.service.impl;

import application.entity.Course;
import application.entity.MindMap;
import application.entity.Student;
import application.entity.Teacher;
import application.repository.*;
import application.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * Creator: DreamBoy
 * Date: 2018/6/6.
 */
@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MindMapRepository mindMapRepository;
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private StudentRepository studentRepository;

    @Override
    public Course getById(long id) {
        return courseRepository.findById(id);
    }

    @Override
    public Course getByName(String name) {
        return courseRepository.findByName(name);
    }

    @Override
    public Set<Course> getByTeacherId(long id) {
        Teacher teacher = teacherRepository.findById(id);
        Set<Course> courseSet = teacher.getCourses();
        for (Course course: courseSet) {
            courseRepository.findById(course.getId().longValue());
        }
        return courseSet;
    }

    @Override
    public Set<Course> getByStudentId(long id) {
        Student student = studentRepository.findById(id);
        Set<Course> courseSet = student.getCourses();
        for (Course course: courseSet) {
            courseRepository.findById(course.getId().longValue());
        }
        return courseSet;
    }

    @Override
    public Course getByMindMapId(long mindMapId) {
        MindMap mindMap = mindMapRepository.findById(mindMapId);
        return mindMap.getCourse();
    }

    @Override
    public Course addCourse(long teacherId, String courseName) {
        Teacher teacher = (Teacher) userRepository.findById(teacherId);
        Course course = new Course();
        course.setName(courseName);
        course.setTeacher(teacher);
        return courseRepository.save(course);
    }

    @Override
    public void selectCourse(long studentId, long courseId) {
        Student student = (Student) userRepository.findById(studentId);
        Course course = courseRepository.findById(courseId);
        student.addCourses(course);
        userRepository.save(student);
    }

    @Override
    public void deleteCourse(long id) {
        courseRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        courseRepository.deleteAll();
    }

    @Override
    public void updateCourse(Course course) {
        courseRepository.save(course);
    }
}
