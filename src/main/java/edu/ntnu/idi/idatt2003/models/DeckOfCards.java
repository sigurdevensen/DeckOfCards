package edu.ntnu.idi.idatt2003.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Represents a complete deck of 52 playing cards.
 */
public class DeckOfCards {

    private final char[] suit = {'S', 'H', 'D', 'C'};
    private final List<PlayingCard> cards;
    private final Random random;

    /**
     * Creates a complete deck of cards (52 cards).
     */
    public DeckOfCards() {
        this.cards = new ArrayList<>();
        this.random = new Random();

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
     * Deals a hand with n random cards from the deck.
     *
     * @param n number of cards to deal, from 1 to 52
     * @return a collection containing n random cards
     * @throws IllegalArgumentException if n is outside [1, 52]
     */
    public List<PlayingCard> dealHand(int n) {
        if (n < 1 || n > cards.size()) {
            throw new IllegalArgumentException("Parameter n must be a number between 1 and 52");
        }

        List<PlayingCard> shuffledCards = new ArrayList<>(cards);
        Collections.shuffle(shuffledCards, random);
        return new ArrayList<>(shuffledCards.subList(0, n));
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