package com.codurance.training.tasks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class TaskList implements Runnable {
    private static final String QUIT = "quit";

    private final Map<String, List<Task>> tasks = new LinkedHashMap<>();
    private final Map<String, Project> projects = new LinkedHashMap<>();

    private final BufferedReader in;
    private final PrintWriter out;

    private long lastId = 0;

    public Map<String, Project> getProjects() {
        return projects;
    }

    public Map<String, List<Task>> getTasks() {
        return tasks;
    }

    public static void main(String[] args) throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        new TaskList(in, out).run();
    }

    public TaskList(BufferedReader reader, PrintWriter writer) {
        this.in = reader;
        this.out = writer;
    }

    public void run() {
        while (true) {
            out.print("> ");
            out.flush();
            String command;
            try {
                command = in.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (command.equals(QUIT)) {
                break;
            }
            execute(command);
        }
    }

    private void execute(String commandLine) {
        String[] commandRest = commandLine.split(" ", 2);
        String[] commandsWithArguments = {"add", "check", "uncheck"};
        String command = commandRest[0];

        if(commandRest.length <= 1 && in_array(commandsWithArguments, command)) {
            invalidArgument();
            return;
        }

        switch (command) {
            case "show":
                show();
                break;
            case "add":
                add(commandRest[1]);
                break;
            case "check":
                check(commandRest[1]);
                break;
            case "uncheck":
                uncheck(commandRest[1]);
                break;
            case "help":
                help();
                break;
            default:
                error(command);
                break;
        }
    }

    private boolean in_array(String[] array, String value) {
        for (Object element : array) {
            if (element.toString().equals(value)) {
                return true;
            }
        }

        return false;
    }

    private void show() {
        for (Map.Entry<String, Project> project : projects.entrySet()) {
            out.println(project.getKey());
            for (Task task : project.getValue().getTasks()) {
                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                out.println(formatter.format(task.getCreatedAt()));
                out.printf("    [%c] %d: %s%n", (task.isDone() ? 'x' : ' '), task.getId(), task.getDescription());
            }
            out.println();
        }
    }

    private void add(String commandLine) {
        String[] subcommandRest = commandLine.split(" ", 2);
        String subcommand = subcommandRest[0];
        String information = subcommandRest[1];
        if (subcommand.equals("project")) {
            addProject(information);
        } else if (subcommand.equals("task")) {
            String[] projectTask = information.split(" ", 2);
            String project = projectTask[0];
            String description = projectTask[1];
            addTask(project, description);
        }
    }

    private void addProject(String name) {
        Project project = new Project(name);
        projects.put(name, project);
    }

    private void addTask(String project, String description) {
        Project currentProject = projects.get(project);
        if (currentProject == null) {
            out.printf("Could not find a project with the name \"%s\".", project);
            out.println();
            return;
        }
        currentProject.addTask(new Task(nextId(), description, false));
    }

    private void check(String idString) {
        setDone(idString, true);
    }

    private void uncheck(String idString) {
        setDone(idString, false);
    }

    private void setDone(String idString, boolean done) {
        int id = Integer.parseInt(idString);
        for (Map.Entry<String, List<Task>> project : tasks.entrySet()) {
            for (Task task : project.getValue()) {
                if (task.getId() == id) {
                    task.setDone(done);
                    return;
                }
            }
        }
        out.printf("Could not find a task with an ID of %d.", id);
        out.println();
    }

    private void help() {
        out.println("Commands:");
        out.println("  show");
        out.println("  add project <project name>");
        out.println("  add task <project name> <task description>");
        out.println("  check <task ID>");
        out.println("  uncheck <task ID>");
        out.println();
    }

    private void error(String command) {
        out.printf("I don't know what the command \"%s\" is.", command);
        out.println();
    }

    private void invalidArgument() {
        out.printf("Arguments invalides");
        out.println();
    }

    private long nextId() {
        return ++lastId;
    }
}
