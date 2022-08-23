package duke;

import java.time.LocalDate;

public class Deadline extends Task {

    protected String by;
    protected LocalDate localDate;

    public Deadline(String description, String by) {
        super(description);
        this.by = by;
        this.localDate = LocalDate.parse(by);
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }

    @Override
    public String toFileString() {
        return String.format("D | %s | %s | %s", getStatusIcon(), this.description, by);
    }
}