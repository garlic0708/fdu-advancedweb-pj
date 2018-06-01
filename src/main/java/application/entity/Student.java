package application.entity;

import org.neo4j.ogm.annotation.Relationship;

import java.util.HashSet;
import java.util.Set;

/**
 * Creator: DreamBoy
 * Date: 2018/6/1.
 */
public class Student extends User {
    @Relationship(type = "Select Course")
    private Set<Course> courses;

    @Relationship(type = "resolve")
    private StudentAnswerForMutipleChoice studentAnswerForMutipleChoice;

    @Relationship(type = "resolve")
    private StudentAnswerForShortAnswer studentAnswerForShortAnswer;

    public Student() {
        this.setRole(Role.STUDENT);

        courses = new HashSet<>();
    }

    public Student(String name, String email, String passwordHash) {
        this.setName(name);
        this.setEmail(email);
        this.setPasswordHash(passwordHash);
        courses = new HashSet<>();
    }

    public void addCourse(Course course) {
        courses.add(course);
    }
}
