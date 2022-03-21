package blackjack.domain.player;

import blackjack.domain.card.Card;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static blackjack.domain.Fixtures.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class PlayerTest {

    @ParameterizedTest
    @MethodSource("bunchOfCards")
    @DisplayName("가지고 있는 카드합이 블랙잭인지 확인한다.")
    void checkBlackjack(List<Card> cards, boolean result) {
        Player player = new Participant("corinne", name -> true);
        for (Card card : cards) {
            player.addCard(card);
        }

        assertThat(player.isBlackjack()).isEqualTo(result);
    }

    private static Stream<Arguments> bunchOfCards() {
        return Stream.of(
                Arguments.of(List.of(SPADE_ACE, SPADE_KING), true),
                Arguments.of(List.of(SPADE_ACE, HEART_JACK), true),
                Arguments.of(List.of(SPADE_ACE, SPADE_QUEEN), true),
                Arguments.of(List.of(SPADE_KING, HEART_NINE, SPADE_TWO), false),
                Arguments.of(List.of(SPADE_KING, HEART_NINE), false)
        );
    }
}
