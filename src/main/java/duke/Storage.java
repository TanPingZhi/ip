package duke;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Storage is used to store and read Tasks.
 */
public class Storage {
    String filepath;
    private ArrayList<Task> tasks = new ArrayList<>();

    Storage(String filepath) {
        this.filepath = filepath;
    }

    /**
     * @return
     */
    public List<Task> load() {

        parseFileToTasks(filepath);
        return tasks;
    }

    /**
     * @param taskList TaskList object to be stored in the file
     * @throws IOException when the BufferedWritter is unable to initialise properly
     */
    public void save(TaskList taskList) throws IOException {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(filepath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        writer.write(taskList.toString());

        writer.close();
    }

    /**
     * Converts all lines of tasks stored in the text file to Task Object
     *
     * @param filepath location of file stored
     */
    private void parseFileToTasks(String filepath) {
        try {
            new File("data").mkdir();
            Scanner in = new Scanner(new FileReader(filepath));
            while (in.hasNextLine()) {
                String line = in.nextLine();
                String[] data = line.split(" \\| ");
                Task task = null;
                switch (data[0]) {
                case "T":
                    task = new Todo(data[2]);
                    break;
                case "D":
                    task = new Deadline(data[2], data[3]);
                    break;
                case "E":
                    task = new Event(data[2], data[3]);
                    break;
                }
                if (task == null) {
                    throw new DukeException("task is null");
                }
                if (data[1].equals("X")) {
                    task.mark();
                }
                this.tasks.add(task);
            }
        } catch (FileNotFoundException e) {
            try {
                (new File(filepath)).createNewFile();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        } catch (DukeException e) {
            try {
                (new File(filepath)).createNewFile();
            } catch (IOException ex) {
                System.out.println("something went wrong creating taskfile");
            }
        }
    }

    @Override
    public String toString() {
        String out = "";
        for (Task t : tasks) {
            out += t.toFileString() + "\n";
        }
        return out;
    }
}
