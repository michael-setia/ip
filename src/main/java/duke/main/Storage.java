package duke.main;

import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;
import duke.task.Todo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.Scanner;

public class Storage {

    // Method to convert task from hard disk
    public Task convertFromHardDisk(String s) {
        String[] data = s.split(" / ");
        String taskType = data[0];
        boolean isDone = data[1].equals("1");
        String description = data[2];
        Task task;
        if (taskType.equals("T")) {
            task = new Todo(description);
        } else if (taskType.equals("D")) {
            String date = data[3];
            task = new Deadline(description, date);
        } else {
            String date = data[3];
            task = new Event(description, date);
        }
        if (isDone) {
            task.markAsDone();
        }
        return task;
    }

    // Method to convert task to hard disk
    public String convertToHardDisk(Task t) {
        String[] info = t.getInfo();
        String taskType = info[0];
        String description = info[1];
        String isDone = t.isDone() ? "1" : "0";
        if (taskType.equals("T")) {
            return taskType + " / " + isDone + " / " + description;
        } else if (taskType.equals("D")) {
            return taskType + " / " + isDone + " / " + description + " / " + info[2];
        } else {
            return taskType + " / " + isDone + " / " + description + " / " + info[2];
        }
    }

    // Method to read all tasks from hard disk
    public ArrayList<Task> readFromHardDisk() {
        // Create paths for the file
        String projectRoot = System.getProperty("user.dir");
        // p1 is used for creating directory
        Path p1 = Paths.get(projectRoot, "data");
        Path p2 = Paths.get(projectRoot, "data", "task.txt");
        String path = p2.toString();
        File data = new File(path);

        // Make directory if it does not exist
        if (!Files.exists(p1)) {
            File temp = new File(p1.toString());
            boolean created = temp.mkdir();
            if (!created) {
                System.out.println("Cannot create directory for storage file! List will not" + "\n" +
                    "be saved until the directory and the file are created.");
            }
        }
        // ArrayList to store task
        ArrayList<Task> storage = new ArrayList<>();
        try {
            Scanner sc = new Scanner(data);
            while (sc.hasNextLine()) {
                // Convert line to task, then add to storage
                storage.add(convertFromHardDisk(sc.nextLine()));
            }
        } catch (FileNotFoundException e) {
            try {
                // Create file if it does not exist
                /* The case for file does not exist is handled separately so that the
                 * user does not need to delete the entire folder.
                 */
                boolean created = data.createNewFile();
                if (!created) {
                    System.out.println("Cannot create storage file! List will not be saved until the " +
                        "file is created");
                }
            } catch (IOException x) {
                x.printStackTrace();
            }
        }
        return  storage;
    }

    // Method to write all task to hard disk
    public void writeToHardDisk(TaskList storage) {
        String projectRoot = System.getProperty("user.dir");
        Path p2 = Paths.get(projectRoot, "data", "task.txt");
        try {
            FileWriter fw = new FileWriter(p2.toString());
            StringBuilder text = new StringBuilder();
            for (int i = 0; i < storage.size(); i++) {
                Task t = storage.get(i);
                text.append(convertToHardDisk(t)).append("\n");
            }
            fw.write(text.toString());
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}