package com.codurance.training.tasks;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

public class TaskTest extends TestCase {
    @Test
    public void testAddTask() {
        Task task = new Task(1, "Description d'une tâche", false);
        Assert.assertEquals("Description d'une tâche", task.getDescription());
    }
}