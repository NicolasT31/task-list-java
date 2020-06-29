package com.codurance.training.tasks;

import java.util.ArrayList;
import java.util.List;

public class Project {
    String name;
    List<Task> tasks;

    Project(String name) {
        this.name = name;
        this.tasks = new ArrayList<Task>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public void addTask(Task task) {
        this.tasks.add(task);
    }
}
