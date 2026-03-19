package edu.ntnu.idi.idatt2003.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a complete deck of 52 playing cards.
 */
public class DeckOfCards {

    private final char[] suit = {'S', 'H', 'D', 'C'};
    private final List<PlayingCard> cards;

    /**
     * Creates a complete deck of cards (52 cards).
     */
    public DeckOfCards() {
        this.cards = new ArrayList<>();

        for (char cardSuit : suit) {
            for (int face = 1; face <= 13; face++) {
                cards.add(new PlayingCard(cardSuit, face));
            }
        }
    }

    /**
     * Returns an unmodifiable view of all cards in the deck.
     *
     * @return all 52 cards in the deck
     */
    public List<PlayingCard> getCards() {
        return Collections.unmodifiableList(cards);
    }

    /**
     * Returns all cards in deck as a string, for example: "H4 H12 C3 D11 S1".
     *
     * @return all cards in deck as a space-separated string
     */
    public String getAsString() {
        return cards.stream()
            .map(PlayingCard::getAsString)
            .collect(Collectors.joining(" "));
    }
}