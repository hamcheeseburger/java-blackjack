package blackjack.domain.card;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class CardsTest {

    @Test
    @DisplayName("카드를 추가할 떄 null을 전달하면 예외를 발생한다.")
    void thrownExceptionWhenGivenNull() {
        Cards cards = new Cards();
        assertThatThrownBy(() -> cards.addCard(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 올바른 카드를 입력해주세요.");
    }

    @Test
    @DisplayName("카드를 추가하면 관리하는 카드 개수가 늘어난다.")
    void addCard() {
        Cards cards = new Cards();
        cards.addCard(new Card(Type.SPADE, Score.ACE));
        cards.addCard(new Card(Type.DIAMOND, Score.TWO));
        cards.addCard(new Card(Type.CLOVER, Score.THREE));

        assertThat(cards.getCards().size()).isEqualTo(3);
    }

    @Test
    @DisplayName("cards의 점수 합을 구한다.")
    void sumCardScore() {
        Cards cards = new Cards();
        cards.addCard(new Card(Type.SPADE, Score.ACE));
        cards.addCard(new Card(Type.DIAMOND, Score.TWO));
        cards.addCard(new Card(Type.CLOVER, Score.THREE));

        assertThat(cards.calculateFinalScore()).isEqualTo(16);
    }


    @ParameterizedTest
    @MethodSource("cardOverMaxScore")
    @DisplayName("카드의 점수가 21을 넘을 수 있다.")
    void calculateOverMaxScore(List<Card> receivedCards, int score) {
        Cards cards = new Cards();
        for (Card card : receivedCards) {
            cards.addCard(card);
        }

        Assertions.assertThat(cards.calculateFinalScore()).isEqualTo(score);
    }

    private static Stream<Arguments> cardOverMaxScore() {
        return Stream.of(
                Arguments.of(List.of(
                        new Card(Type.SPADE, Score.EIGHT),
                        new Card(Type.HEART, Score.EIGHT),
                        new Card(Type.SPADE, Score.EIGHT)
                ), 24),
                Arguments.of(List.of(
                        new Card(Type.SPADE, Score.EIGHT),
                        new Card(Type.HEART, Score.NINE),
                        new Card(Type.SPADE, Score.EIGHT)
                ), 25)
        );
    }

    @ParameterizedTest
    @MethodSource("containsAce")
    @DisplayName("Ace가 포함되면 1또는 11로 계산될 수 있다.")
    void calculateContainsAce(List<Card> receivedCards, int score) {
        Cards cards = new Cards();
        for (Card card : receivedCards) {
            cards.addCard(card);
        }

        Assertions.assertThat(cards.calculateFinalScore()).isEqualTo(score);
    }

    private static Stream<Arguments> containsAce() {
        return Stream.of(
                Arguments.of(List.of(
                        new Card(Type.SPADE, Score.ACE),
                        new Card(Type.HEART, Score.ACE),
                        new Card(Type.HEART, Score.NINE)
                ), 21),
                Arguments.of(List.of(
                        new Card(Type.SPADE, Score.ACE),
                        new Card(Type.HEART, Score.JACK),
                        new Card(Type.HEART, Score.ACE)
                ), 12),
                Arguments.of(List.of(
                        new Card(Type.SPADE, Score.TWO),
                        new Card(Type.HEART, Score.EIGHT),
                        new Card(Type.HEART, Score.ACE)
                ), 21)

        );
    }
}
