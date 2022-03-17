package blackjack.domain.card;

import java.util.List;

public enum Score {

    ACE("A", 1),
    TWO("2", 2),
    THREE("3", 3),
    FOUR("4", 4),
    FIVE("5", 5),
    SIX("6", 6),
    SEVEN("7", 7),
    EIGHT("8", 8),
    NINE("9", 9),
    TEN("10", 10),
    JACK("J", 10),
    KING("K", 10),
    QUEEN("Q", 10),
    ;

    private final String symbol;
    private final int amount;

    Score(final String symbol, final int amount) {
        this.symbol = symbol;
        this.amount = amount;
    }

    public static List<Score> getScoreValues() {
        return List.of(values());
    }

    public String getSymbol() {
        return this.symbol;
    }

    public int getAmount() {
        return this.amount;
    }
}
