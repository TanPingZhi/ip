import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Storage {
    String filepath;
    private ArrayList<Task> tasks = new ArrayList<>();

    Storage(String filepath) {
        this.filepath = filepath;
        parseFileToTasks(filepath);
    }

    public List<Task> load() {
        return tasks;
    }

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

    private void parseFileToTasks(String filepath) {
        try {
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
                if (data[1].equals("X")){
                    task.mark();
                }
                this.tasks.add(task);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (DukeException e) {
            try {
                (new File("data//tasks.txt")).createNewFile();
            } catch (IOException ex) {
                System.out.println("something went wrong creating taskfile");
            }
        }
    }

    @Override
    public String toString() {
        String out = "";
        for (Task t: tasks) {
            out += t.toFileString() + "\n";
        }
        return out;
    }
}