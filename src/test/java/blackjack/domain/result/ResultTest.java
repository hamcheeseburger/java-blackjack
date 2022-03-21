package blackjack.domain.result;

import blackjack.domain.betting.Money;
import blackjack.domain.player.Dealer;
import blackjack.domain.player.Participant;
import blackjack.domain.player.ParticipantAcceptStrategy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static blackjack.domain.Fixtures.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ResultTest {

    @Test
    @DisplayName("딜러와 참가자가 모두 블랙잭이라면 무승부로 판정한다.")
    void drawWhenBothBlackjack() {
        Participant zero = new Participant("zero", new ParticipantAcceptStrategy());
        zero.addCard(SPADE_ACE);
        zero.addCard(HEART_JACK);
        Dealer dealer = new Dealer();
        dealer.addCard(HEART_ACE);
        dealer.addCard(SPADE_KING);

        assertThat(Result.calculateResult(dealer, zero)).isEqualTo(Result.DRAW);
    }

    @Test
    @DisplayName("딜러와 참가자의 점수합이 같다면 무승부로 판단한다.")
    void drawWhenScoreEqual() {
        Participant zero = new Participant("zero", new ParticipantAcceptStrategy());
        zero.addCard(SPADE_KING);
        zero.addCard(HEART_SIX);
        Dealer dealer = new Dealer();
        dealer.addCard(SPADE_EIGHT);
        dealer.addCard(HEART_EIGHT);

        assertThat(Result.calculateResult(dealer, zero)).isEqualTo(Result.DRAW);
    }

    @Test
    @DisplayName("21점과 blackjack이 겨루면 blackjack을 가진 쪽이 승리한다.")
    void winBlackjackOwner() {
        Participant zero = new Participant("zero", new ParticipantAcceptStrategy());
        zero.addCard(SPADE_ACE);
        zero.addCard(SPADE_KING);
        Dealer dealer = new Dealer();
        dealer.addCard(HEART_EIGHT);
        dealer.addCard(SPADE_EIGHT);
        dealer.addCard(CLOVER_FIVE);

        assertThat(Result.calculateResult(dealer, zero)).isEqualTo(Result.BLACKJACK);
    }

    @Test
    @DisplayName("참가자가 Burst고 딜러는 Burst가 아니라면 딜러가 승리한다.")
    void dealerWinWhenNotBurstAndParticipantBurst() {
        Participant zero = new Participant("zero", new ParticipantAcceptStrategy());
        zero.addCard(SPADE_KING);
        zero.addCard(HEART_SIX);
        zero.addCard(HEART_JACK);
        Participant corinne = new Participant("corinne", new ParticipantAcceptStrategy());
        corinne.addCard(HEART_SIX);
        corinne.addCard(SPADE_QUEEN);
        corinne.addCard(SPADE_EIGHT);

        Dealer dealer = new Dealer();
        dealer.addCard(HEART_ACE);
        dealer.addCard(SPADE_KING);

        assertAll(
                () -> assertThat(Result.calculateResult(dealer, zero)).isEqualTo(Result.LOSE),
                () -> assertThat(Result.calculateResult(dealer, corinne)).isEqualTo(Result.LOSE)
        );
    }

    @Test
    @DisplayName("참가자가 Burst가 아니고 딜러는 Burst 라면 참가자가 승리한다.")
    void participantWinWhenNotBurstAndDealerBurst() {
        Participant zero = new Participant("zero", new ParticipantAcceptStrategy());
        zero.addCard(HEART_JACK);
        zero.addCard(SPADE_KING);

        Dealer dealer = new Dealer();
        dealer.addCard(SPADE_KING);
        dealer.addCard(HEART_SIX);
        dealer.addCard(SPADE_QUEEN);

        assertAll(
                () -> assertThat(Result.calculateResult(dealer, zero)).isEqualTo(Result.WIN)
        );
    }

    @Test
    @DisplayName("참가자와 딜러 모두 Burst 라면 딜러가 승리한다.")
    void dealerWinWhenBurstAndParticipantBurst() {
        Participant zero = new Participant("zero", new ParticipantAcceptStrategy());
        zero.addCard(SPADE_KING);
        zero.addCard(HEART_SIX);
        zero.addCard(HEART_JACK);

        Participant corinne = new Participant("corinne", new ParticipantAcceptStrategy());
        corinne.addCard(HEART_SIX);
        corinne.addCard(SPADE_KING);
        corinne.addCard(HEART_EIGHT);

        Dealer dealer = new Dealer();
        dealer.addCard(HEART_ACE);
        dealer.addCard(SPADE_KING);
        dealer.addCard(SPADE_ACE);

        assertAll(
                () -> assertThat(Result.calculateResult(dealer, zero)).isEqualTo(Result.LOSE),
                () -> assertThat(Result.calculateResult(dealer, corinne)).isEqualTo(Result.LOSE)
        );
    }

    @Test
    @DisplayName("참가자가 딜러보다 점수합이 높으면 참가자가 승리한다.")
    void participantWinWhenOverDealer() {
        Participant zero = new Participant("zero", new ParticipantAcceptStrategy());
        zero.addCard(SPADE_ACE);
        zero.addCard(HEART_SIX);

        Participant corinne = new Participant("corinne", new ParticipantAcceptStrategy());
        corinne.addCard(SPADE_KING);
        corinne.addCard(HEART_EIGHT);

        Dealer dealer = new Dealer();
        dealer.addCard(SPADE_TWO);
        dealer.addCard(SPADE_EIGHT);

        assertAll(
                () -> assertThat(Result.calculateResult(dealer, zero)).isEqualTo(Result.WIN),
                () -> assertThat(Result.calculateResult(dealer, corinne)).isEqualTo(Result.WIN)
        );
    }

    @Test
    @DisplayName("블랙잭이면 배팅 금액의 1.5배를 수익으로 얻는다.")
    void multipleProfitWhenBlackjack() {
        assertThat(Result.BLACKJACK.calculateProfit(new Money(1000))).isEqualTo(1500);
    }

    @Test
    @DisplayName("승리하면 배팅 금액 만큼 수익으로 얻는다.")
    void getProfitWhenWin() {
        assertThat(Result.WIN.calculateProfit(new Money(1000))).isEqualTo(1000);
    }

    @Test
    @DisplayName("패배하면 배팅 금액 만큼 잃는다.")
    void loseProfitWhenLose() {
        assertThat(Result.LOSE.calculateProfit(new Money(1000))).isEqualTo(-1000);
    }

    @Test
    @DisplayName("무승부이면 수익이 0이다.")
    void noProfitWhenDraw() {
        assertThat(Result.DRAW.calculateProfit(new Money(1000))).isEqualTo(0);
    }
}
