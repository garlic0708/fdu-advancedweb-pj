package application.entity;

import org.neo4j.ogm.annotation.Relationship;

import java.util.HashSet;
import java.util.Set;

/**
 * Creator: DreamBoy
 * Date: 2018/6/1.
 */
public class Teacher extends User {

    @Relationship(type = "Open Course")
    private Set<Course> courses;

    public Teacher() {
        this.setRole(Role.TEACHER);
        courses = new HashSet<>();
    }

    public Teacher(String name, String email, String passwordHash) {
        this.setName(name);
        this.setEmail(email);
        this.setPasswordHash(passwordHash);
        courses = new HashSet<>();
    }

    public void addCourse(Course course) {
        courses.add(course);
    }
}
