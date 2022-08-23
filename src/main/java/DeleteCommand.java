public class DeleteCommand extends Command {
    int indexToDelete;
    DeleteCommand(int number) {
        this.indexToDelete = number - 1;
    }
    @Override
    void execute(TaskList tasks, Ui ui, Storage storage) {
        tasks.remove(indexToDelete);
    }

    @Override
    boolean isExit() {
        return false;
    }
}