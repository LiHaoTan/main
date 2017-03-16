package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.task.ReadOnlyTask;

// TODO card design
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "Card.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label event;
    @FXML
    private Label id;
    @FXML
    private FlowPane tags;

    public PersonCard(ReadOnlyTask person, int displayedIndex) {
        super(FXML);
        event.setText(person.getName().value);
        id.setText(displayedIndex + ". ");
        initTags(person);
    }

    private void initTags(ReadOnlyTask person) {
        person.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
}
