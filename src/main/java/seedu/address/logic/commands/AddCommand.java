package seedu.address.logic.commands;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.Name;
import seedu.address.model.task.StartEndDateTime;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;

/**
 * Adds a task to the task list.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the task list. "
            + "Parameters: NAME  [t/TAG]...\n"
            + "Example: " + COMMAND_WORD
            + " exam t/CS2010";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task list";

    private final Task toAdd;

    /**
     * Creates an AddCommand using raw values.
     * @param endDateTimeArgs
     * @param startDateTimeArgs
     * @param deadlineDateTimeArgs
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String name, Optional<String> deadlineDateTimeArgs, Optional<String> startDateTimeArgs,
                      Optional<String> endDateTimeArgs, Set<String> tags) throws IllegalValueException {

        Optional<ZonedDateTime> deadlineDateTime = Optional.empty();
        Optional<ZonedDateTime> startDateTime = Optional.empty();
        Optional<ZonedDateTime> endDateTime = Optional.empty();

        if (deadlineDateTimeArgs.isPresent()) {
            deadlineDateTime = ParserUtil.parseDateTimeString(deadlineDateTimeArgs.get());
        }
        // deadline = deadlineArgs.flatMap(ParserUtil::parseDateTimeString); TODO use lambda

        boolean isStartDateTimePresent = startDateTimeArgs.isPresent();

        if (isStartDateTimePresent) {
            if (!endDateTimeArgs.isPresent()) {
                throw new IllegalValueException("End date must exist if there is a start date");
            }
            startDateTime = ParserUtil.parseDateTimeString(startDateTimeArgs.get());
            endDateTime = ParserUtil.parseDateTimeString(startDateTimeArgs.get());
        }

        //if (endDateTimeArgs.isPresent()) { TODO
            //if (!isStartDateTimePresent) {
            //    throw new IllegalValueException("End Date cannot exist without Start Date.");
            //}
            endDateTime = ParserUtil.parseDateTimeString(endDateTimeArgs.get());
        //}

        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }

        Optional<Deadline> deadline = Optional.empty();
        if (deadlineDateTimeArgs.isPresent()) {
            deadline = Optional.of(new Deadline(deadlineDateTime.get()));
        }

        Optional<StartEndDateTime> startEndDateTime = Optional.empty();

        if (startDateTimeArgs.isPresent() && endDateTimeArgs.isPresent()) {
            startEndDateTime = Optional.of(new StartEndDateTime(startDateTime.get(), endDateTime.get()));
        }


        this.toAdd = new Task(
                new Name(name),
                deadline,
                startEndDateTime,
                new UniqueTagList(tagSet)
        );
    }

    @Override
    public CommandResult execute() throws CommandException {
        assert model != null;
        try {
            model.addTask(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }

    }

}
