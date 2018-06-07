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

    @Relationship(type = "resolve" )
    private Set<StudentAnswerForMultipleChoice> studentAnswerForMultipleChoice;

    @Relationship(type = "resolve")
    private Set<StudentAnswerForShortAnswer> studentAnswerForShortAnswer;

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

    public void addCourses(Course course) {
        courses.add(course);
    }

    public Set<Course> getCourses() {
        return this.courses;
    }

    public Set<StudentAnswerForMultipleChoice> getStudentAnswerForMultipleChoice() {
        return studentAnswerForMultipleChoice;
    }

    public void setStudentAnswerForMultipleChoice(Set<StudentAnswerForMultipleChoice> studentAnswerForMultipleChoice) {
        this.studentAnswerForMultipleChoice = studentAnswerForMultipleChoice;
    }

    public Set<StudentAnswerForShortAnswer> getStudentAnswerForShortAnswer() {
        return studentAnswerForShortAnswer;
    }

    public void setStudentAnswerForShortAnswer(Set<StudentAnswerForShortAnswer> studentAnswerForShortAnswer) {
        this.studentAnswerForShortAnswer = studentAnswerForShortAnswer;
    }
}
