package application.service.impl;

import application.entity.*;
import application.repository.CourseRepository;
import application.repository.StudentRepository;
import application.repository.TeacherRepository;
import application.repository.UserRepository;
import application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private TeacherRepository teacherRepository;

    @Override
    public User addUser(String name, String email, String password, Role role) {
        User user;
        if (role == Role.TEACHER)
            user = new Teacher();
        else
            user = new Student();
        user.setName(name);
        user.setEmail(email);
        user.setRole(role);
        user.setPasswordHash(getPasswordHash(password));
        userRepository.save(user);
//        if (role == Role.TEACHER)
//            teacherRepository.save(user);
//        else
//            studentRepository
        return user;
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
    public User getByEmail(String email) {
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
    public Set<Student> getStudentsByCourseId(long courseId) {
        Course course = courseRepository.findById(courseId);
        return course.getStudents();
    }

    /**
     * 生成32位md5码
     * @param password
     * @return
     */
    private String getPasswordHash(String password) {
        try {
            // 得到一个信息摘要器
            MessageDigest digest = MessageDigest.getInstance("md5");
            byte[] result = digest.digest(password.getBytes());
            StringBuilder buffer = new StringBuilder();
            // 把每一个byte 做一个与运算 0xff;
            for (byte b : result) {
                // 与运算
                int number = b & 0xff;// 加盐
                String str = Integer.toHexString(number);
                if (str.length() == 1) {
                    buffer.append("0");
                }
                buffer.append(str);
            }

            // 标准的md5加密后的结果
            return buffer.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }
}
