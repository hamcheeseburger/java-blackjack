package blackjack.domain.card;

import java.util.*;
import java.util.stream.IntStream;

public class Deck {

    protected static final int INIT_DISTRIBUTE_SIZE = 2;

    private final Stack<Card> bunchOfCards;

    public Deck(Stack<Card> bunchOfCards) {
        this.bunchOfCards = bunchOfCards;
    }

    public List<Card> makeDistributeCard() {
        List<Card> cards = new ArrayList<>();
        IntStream.range(0, INIT_DISTRIBUTE_SIZE)
                .forEach(i -> cards.add(draw()));
        return cards;
    }

    public Card draw() {
        return bunchOfCards.pop();
    }
}
