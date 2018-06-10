package application.service.impl;

import application.entity.*;
import application.repository.CourseRepository;
import application.repository.StudentRepository;
import application.repository.TeacherRepository;
import application.repository.UserRepository;
import application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, CourseRepository courseRepository, StudentRepository studentRepository, TeacherRepository teacherRepository) {
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
    }

    @Override
    public User addUser(UserCreateForm form) {

        User user;
        Role role = form.getRole();
        if (role == Role.TEACHER)
            user = new Teacher();
        else
            user = new Student();
        user.setName(form.getName());
        user.setEmail(form.getEmail());
        user.setRole(role);
        user.setPasswordHash(new BCryptPasswordEncoder().encode(form.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User getById(long id) {
        return userRepository.findById(id);
    }

    @Override
    public User getByName(String name) {
        return userRepository.findByName(name);
    }

    @Override
    public Optional<User> getByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Teacher getTeacherById(long id) {
        return teacherRepository.findById(id);
    }

    @Override
    public Teacher getTeacherByName(String name) {
        return teacherRepository.findByName(name);
    }

    @Override
    public Student getStudentById(long id) {
        return studentRepository.findById(id);
    }

    @Override
    public Student getStudentByName(String name) {
        return studentRepository.findByName(name);
    }

    @Override
    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        userRepository.deleteAll();
    }

    @Override
    public void updateUser(User user) {
        userRepository.save(user);
    }

    @Override
    public Set<User> getAll() {
        List<User> users = (List<User>) userRepository.findAll();
        return new HashSet<>(users);
    }

    @Override
    public Teacher getTeacherByCourseId(long courseId) {
        Course course = courseRepository.findById(courseId);
        return course.getTeacher();
    }

    @Override
    public Teacher getTeacherByNodeId(long nodeId) {
        return teacherRepository.findByNodeId(nodeId);
    }

    @Override
    public Set<Student> getStudentsByCourseId(long courseId) {
        Course course = courseRepository.findById(courseId);
        return course.getStudents();
    }
}
