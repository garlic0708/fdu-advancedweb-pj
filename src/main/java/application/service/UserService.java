package application.service;

import application.entity.Role;
import application.entity.Student;
import application.entity.Teacher;
import application.entity.User;

import java.util.Optional;
import java.util.Set;

public interface UserService {

    User addUser(String name, String email, String password, Role role);

    User getById(long id);

    User getByName(String name);

    Optional<User> getByEmail(String email);

    Teacher getTeacherById(long id);

    Teacher getTeacherByName(String name);

    Student getStudentById(long id);

    Student getStudentByName(String name);

    void deleteUser(long id);

    void deleteAll();

    void updateUser(User user);

    Set<User> getAll();

    Teacher getTeacherByCourseId(long courseId);

    Teacher getTeacherByNodeId(long nodeId);

    Set<Student> getStudentsByCourseId(long courseId);
}
