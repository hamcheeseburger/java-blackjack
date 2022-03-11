package blackjack.domain;

import blackjack.domain.card.Deck;
import blackjack.domain.player.Dealer;
import blackjack.domain.player.Participant;
import blackjack.domain.player.Player;
import blackjack.domain.player.Players;

import java.util.List;
import java.util.stream.Collectors;

public class GameMachine {

    private final Deck deck;
    private final Players players;

    public GameMachine(final List<String> names) {
        validationNames(names);
        this.deck = new Deck();
        this.players = new Players(createParticipants(names), createDealer());
    }

    private void validationNames(final List<String> names) {
        if (names == null || names.isEmpty()) {
            throw new IllegalArgumentException("[ERROR] 참여자의 이름을 입력해주세요.");
        }
    }

    private List<Player> createParticipants(final List<String> names) {
        return names.stream()
                .map(name -> new Participant(deck.makeDistributeCard(), name))
                .collect(Collectors.toList());
    }

    private Dealer createDealer() {
        return new Dealer(deck.makeDistributeCard());
    }

    public boolean isDealerGetCard() {
        if (Player.changeToDealer(players.getDealer()).acceptableCard()) {
            players.addDealerCard(deck.draw());
            return true;
        }
        return false;
    }

    public void giveCard(Player player) {
        player.addCard(deck.draw());
    }

    public Players getPlayers() {
        return this.players;
    }
}
