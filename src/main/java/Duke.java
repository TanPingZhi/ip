import java.io.IOException;

//public class Duke {
//    private final static String WELCOME =
//            "Hello! I'm Duke\n" +
//                    "What can I do for you?\n" +
//            "use /at or /by";
//
//    public static void main(String[] args) throws IOException {
//        System.out.println(WELCOME);
//        CmdHandler cmdHandler = new CmdHandler();
//        cmdHandler.handle();
//    }
//}
public class Duke {

    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    public Duke(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.load());
        } catch (DukeException e) {
            ui.showLoadingError();
            tasks = new TaskList();
        }
    }

    public void run() {
//        ui.showWelcome();
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                System.out.println(fullCommand);
                ui.showLine(); // show the divider line ("_______")
                Command c = Parser.parse(fullCommand);
                c.execute(tasks, ui, storage);
                isExit = c.isExit();
            } catch (DukeException e) {
                ui.showError(e.getMessage());
            } finally {
                ui.showLine();
            }
        }
        try {
            storage.save(tasks);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        new Duke("data//tasks.txt").run();
    }
}