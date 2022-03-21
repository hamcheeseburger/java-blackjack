package blackjack.domain.player;

import blackjack.domain.card.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static blackjack.domain.Fixtures.*;
import static org.assertj.core.api.Assertions.assertThat;

class DealerTest {

    @Test
    @DisplayName("딜러는 시작시 카드를 2장 받는다.")
    void checkParticipantCardSize() {
        Dealer dealer = new Dealer();
        dealer.addCard(HEART_NINE);
        dealer.addCard(SPADE_TWO);
        assertThat(dealer.getCards().size()).isEqualTo(2);
    }

    @ParameterizedTest
    @MethodSource("cardListAndAcceptable")
    @DisplayName("딜러는 자신의 점수가 16이하인지 확인한다.")
    void addParticipantCard(List<Card> cards, boolean acceptable) {
        Dealer dealer = new Dealer();
        for (Card card : cards) {
            dealer.addCard(card);
        }
        assertThat(dealer.acceptableCard()).isEqualTo(acceptable);
    }

    private static Stream<Arguments> cardListAndAcceptable() {
        return Stream.of(
                Arguments.of(List.of(
                        SPADE_EIGHT,
                        HEART_EIGHT
                ), true),
                Arguments.of(List.of(
                        SPADE_EIGHT,
                        HEART_NINE
                ), false)
        );
    }
}
