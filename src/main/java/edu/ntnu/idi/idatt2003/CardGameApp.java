package edu.ntnu.idi.idatt2003;

import edu.ntnu.idi.idatt2003.models.DeckOfCards;
import edu.ntnu.idi.idatt2003.models.PlayingCard;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * JavaFX GUI for dealing and checking a hand of cards.
 */
public class CardGameApp extends Application {

    private final DeckOfCards deck = new DeckOfCards();
    private List<PlayingCard> currentHand = new ArrayList<>();

    private TextArea handArea;
    private Spinner<Integer> handSizeSpinner;
    private TextField sumOfFacesField;
    private TextField heartsField;
    private TextField flushField;
    private TextField queenOfSpadesField;

    @Override
    public void start(Stage stage) {
        handArea = new TextArea("Trykk 'Deal hand' for å trekke kort.");
        handArea.setEditable(false);
        handArea.setWrapText(true);
        handArea.setPrefRowCount(10);

        handSizeSpinner = new Spinner<>(1, 52, 5);
        handSizeSpinner.setEditable(true);

        Button dealHandButton = new Button("Deal hand");
        dealHandButton.setMaxWidth(Double.MAX_VALUE);
        dealHandButton.setOnAction(event -> dealHand());

        Button checkHandButton = new Button("Check hand");
        checkHandButton.setMaxWidth(Double.MAX_VALUE);
        checkHandButton.setOnAction(event -> checkHand());

        VBox controls = new VBox(12,
            new Label("Antall kort:"),
            handSizeSpinner,
            dealHandButton,
            checkHandButton
        );
        controls.setAlignment(Pos.TOP_CENTER);
        controls.setPadding(new Insets(4, 0, 0, 0));

        HBox topSection = new HBox(16, handArea, controls);
        HBox.setHgrow(handArea, Priority.ALWAYS);

        sumOfFacesField = createReadonlyField();
        heartsField = createReadonlyField();
        flushField = createReadonlyField();
        queenOfSpadesField = createReadonlyField();

        GridPane resultGrid = new GridPane();
        resultGrid.setHgap(10);
        resultGrid.setVgap(10);
        resultGrid.addRow(0, new Label("Sum of the faces:"), sumOfFacesField);
        resultGrid.addRow(1, new Label("Cards of hearts:"), heartsField);
        resultGrid.addRow(2, new Label("Flush:"), flushField);
        resultGrid.addRow(3, new Label("Queen of spades:"), queenOfSpadesField);

        VBox root = new VBox(16, topSection, resultGrid);
        root.setPadding(new Insets(16));

        Scene scene = new Scene(root, 760, 460);
        stage.setTitle("Deck Of Cards");
        stage.setScene(scene);
        stage.show();
    }

    private TextField createReadonlyField() {
        TextField field = new TextField();
        field.setEditable(false);
        return field;
    }

    private void dealHand() {
        int handSize = handSizeSpinner.getValue();
        currentHand = deck.dealHand(handSize);
        handArea.setText(toHandString(currentHand));
        clearAnalysisFields();
    }

    private void checkHand() {
        if (currentHand.isEmpty()) {
            handArea.setText("Ingen hånd å sjekke. Trykk 'Deal hand' først.");
            clearAnalysisFields();
            return;
        }

        int sum = currentHand.stream().mapToInt(PlayingCard::getFace).sum();
        List<String> hearts = currentHand.stream()
            .filter(card -> card.getSuit() == 'H')
            .map(PlayingCard::getAsString)
            .collect(Collectors.toList());
        boolean hasFlush = currentHand.stream().map(PlayingCard::getSuit).distinct().count() == 1;
        boolean hasQueenOfSpades = currentHand.stream()
            .anyMatch(card -> card.getSuit() == 'S' && card.getFace() == 12);

        sumOfFacesField.setText(String.valueOf(sum));
        heartsField.setText(hearts.isEmpty() ? "None" : String.join(" ", hearts));
        flushField.setText(hasFlush ? "Yes" : "No");
        queenOfSpadesField.setText(hasQueenOfSpades ? "Yes" : "No");
    }

    private void clearAnalysisFields() {
        sumOfFacesField.clear();
        heartsField.clear();
        flushField.clear();
        queenOfSpadesField.clear();
    }

    private String toHandString(List<PlayingCard> hand) {
        return hand.stream()
            .map(PlayingCard::getAsString)
            .collect(Collectors.joining(" "));
    }
}
