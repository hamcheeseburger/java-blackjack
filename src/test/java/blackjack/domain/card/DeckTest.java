package blackjack.domain.card;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Stack;

import static org.assertj.core.api.Assertions.assertThat;


public class DeckTest {

    @Test
    @DisplayName("덱에서 카드를 가져올 수 있다.")
    void drawCard() {
        Deck deck = new Deck(() -> {
            List<Card> cards = List.of(new Card(Type.CLOVER, Score.KING));
            Stack<Card> bunchOfCards = new Stack<>();
            bunchOfCards.addAll(cards);
            return bunchOfCards;
        });
        Card card = deck.draw();

        assertThat(card).isEqualTo(new Card(Type.CLOVER, Score.KING));
    }

    @Test
    @DisplayName("초기에 전달받는 카드는 중복일 수 없다.")
    void notRedundantCards() {
        Deck deck = new Deck(new RandomCardGenerator());
        List<Card> cards = List.of(deck.draw(), deck.draw());

        assertThat(new HashSet<>(cards).size()).isEqualTo(2);
    }

    @Test
    @DisplayName("카드의 타입은 스페이드, 하트, 다이아몬드, 클로버이다.")
    void checkKindOfType() {
        Card card = new Deck(new RandomCardGenerator()).draw();

        assertThat(Type.getTypeValues().contains(card.getType()))
                .isEqualTo(true);
    }

    @Test
    @DisplayName("카드의 점수는 2~10, ACE, KING, JACK, QUEEN 이다.")
    void checkKindOfScore() {
        Card card = new Deck(new RandomCardGenerator()).draw();

        assertThat(Score.getScoreValues().contains(card.getScore()))
                .isEqualTo(true);
    }

    @Test
    @DisplayName("이미 뽑은 카드는 다시 뽑힐 수 없다.")
    void drawDifferentCard() {
        Deck deck = new Deck(() -> {
            List<Card> cards = List.of(new Card(Type.CLOVER, Score.KING), new Card(Type.DIAMOND, Score.SIX));
            Stack<Card> bunchOfCards = new Stack<>();
            bunchOfCards.addAll(cards);
            return bunchOfCards;
        });
        Card card1 = deck.draw();
        Card card2 = deck.draw();

        assertThat(card1).isNotEqualTo(card2);
    }
}
