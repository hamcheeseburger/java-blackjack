package blackjack.controller;

import blackjack.domain.card.Deck;
import blackjack.domain.card.RandomCardGenerator;
import blackjack.domain.player.*;
import blackjack.domain.result.Judge;
import blackjack.view.InputView;
import blackjack.view.OutputView;

import java.util.List;
import java.util.stream.Collectors;

public class Blackjack {

    private static final int INIT_CARD_SIZE = 2;

    public void play() {
        final Deck deck = new Deck(new RandomCardGenerator());
        final Players players = initPlayers(deck);

        OutputView.printPlayersInitCardInfo(players);
        decideGetMoreCard(players, deck);
        announcePlayersFinishInfo(players);
        OutputView.printGameResult(Judge.calculateGameResult(players));
    }

    private Players initPlayers(final Deck deck) {
        final List<Player> participants = createParticipants(InputView.responseNames());
        final Player dealer = createDealer();
        makeParticipantsInitCards(participants, deck);
        makePlayerInitCards(dealer, deck);
        return new Players(participants, dealer);
    }

    private List<Player> createParticipants(final List<String> names) {
        return names.stream()
                .map(name -> new Participant(name, new ParticipantAcceptStrategy()))
                .collect(Collectors.toList());
    }

    private Dealer createDealer() {
        return new Dealer();
    }


    private void makeParticipantsInitCards(final List<Player> participants, final Deck deck) {
        for (Player participant : participants) {
            makePlayerInitCards(participant, deck);
        }
    }

    private void makePlayerInitCards(final Player player, final Deck deck) {
        for (int i = 0; i < INIT_CARD_SIZE; i++) {
            player.addCard(deck.draw());
        }
    }

    private void decideGetMoreCard(final Players players, final Deck deck) {
        decideParticipantsMoreCard(players, deck);
        decideDealerMoreCard(players.getDealer(), deck);
    }

    private void decideParticipantsMoreCard(final Players players, final Deck deck) {
        while (!players.allParticipantsDecided()) {
            Player player = players.getParticipant();
            boolean cardAccept = players.isParticipantAcceptCard(deck);
            printParticipantCardInfo(cardAccept, player);
        }
    }

    private void printParticipantCardInfo(final boolean cardAccept, final Player participant) {
        if(cardAccept) {
            OutputView.printPlayerCardInfo(participant);
        }
    }

    private void decideDealerMoreCard(final Player dealer, final Deck deck) {
        if (dealer.acceptableCard()) {
            dealer.addCard(deck.draw());
            OutputView.printDealerAcceptCard();
            return;
        }
        OutputView.printDealerDenyCard();
    }

    private void announcePlayersFinishInfo(final Players players) {
        OutputView.printPlayerFinalInfo(players.getDealer());
        OutputView.printFinishParticipantInfo(players.getParticipants());
    }
}
