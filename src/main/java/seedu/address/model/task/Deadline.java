package seedu.address.model.task;

import java.time.ZonedDateTime;

public class Deadline {
    private ZonedDateTime dateTime;

    public Deadline(ZonedDateTime dateTime) {
        assert dateTime != null; // TODO

        this.dateTime = dateTime;
    }

    public ZonedDateTime getValue() {
        return dateTime;
    }
}
