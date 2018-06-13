package application.entity.view;

import java.util.Set;

public class NodeAttachments {

    private Set<TypeDescriptionView> questions;
    private Set<TypeDescriptionView> resources;
    private Set<DescriptionView> coursewares;

    public NodeAttachments() {
    }

    public NodeAttachments(Set<TypeDescriptionView> questions, Set<TypeDescriptionView> resources, Set<DescriptionView> coursewares) {
        this.questions = questions;
        this.resources = resources;
        this.coursewares = coursewares;
    }

    public Set<TypeDescriptionView> getQuestions() {
        return questions;
    }

    public void setQuestions(Set<TypeDescriptionView> questions) {
        this.questions = questions;
    }

    public Set<TypeDescriptionView> getResources() {
        return resources;
    }

    public void setResources(Set<TypeDescriptionView> resources) {
        this.resources = resources;
    }

    public Set<DescriptionView> getCoursewares() {
        return coursewares;
    }

    public void setCoursewares(Set<DescriptionView> coursewares) {
        this.coursewares = coursewares;
    }
}
