package blackjack.domain;

import blackjack.domain.card.Card;
import blackjack.domain.card.Score;
import blackjack.domain.card.Type;

public class Fixtures {

    public static final Card SPADE_ACE = new Card(Type.SPADE, Score.ACE);
    public static final Card HEART_ACE = new Card(Type.HEART, Score.ACE);
    public static final Card HEART_NINE = new Card(Type.HEART, Score.NINE);
    public static final Card HEART_SIX = new Card(Type.HEART, Score.SIX);
    public static final Card HEART_JACK = new Card(Type.HEART, Score.JACK);
    public static final Card SPADE_TWO = new Card(Type.SPADE, Score.TWO);
    public static final Card HEART_EIGHT = new Card(Type.HEART, Score.EIGHT);
    public static final Card SPADE_EIGHT = new Card(Type.SPADE, Score.EIGHT);
    public static final Card SPADE_KING = new Card(Type.SPADE, Score.KING);
    public static final Card SPADE_QUEEN = new Card(Type.SPADE, Score.KING);
    public static final Card CLOVER_FIVE = new Card(Type.SPADE, Score.FIVE);
}
