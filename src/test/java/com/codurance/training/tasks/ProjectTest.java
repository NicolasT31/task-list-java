package com.codurance.training.tasks;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

public class ProjectTest extends TestCase {
    @Test
    public void testAddTask() {
        Project project = new Project("test");
        Task task = new Task(1, "Première tâche du projet", false);
        project.addTask(task);

        Assert.assertEquals(project.getTasks().size(),1);
    }
}