package application.service;

import application.entity.Course;

import java.util.Set;

/**
 * Creator: DreamBoy
 * Date: 2018/6/5.
 */
public interface CourseService {
    Course getById(long id);

    Course getByName(String name);

    Set<Course> getByTeacherId(long id);

    Set<Course> getByStudentId(long id);

    Course getByMindMapId(long mindMapId);

    Course addCourse(long teacherId, String courseName);

    Course selectCourse(long studentId, long courseId);

    void deselectCourse(long studentId, long courseId);

    void deleteCourse(long id);

    void deleteAll();

    void updateCourse(Course course);

    Set<Course> queryByName(String name, long studentId);
}
