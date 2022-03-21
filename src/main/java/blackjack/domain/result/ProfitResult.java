package blackjack.domain.result;

import blackjack.domain.betting.Money;
import blackjack.domain.player.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ProfitResult {

    private final Map<Player, Integer> participantProfits;

    private ProfitResult(final Map<Player, Integer> result) {
        this.participantProfits = result;
    }

    public static ProfitResult of(final Map<Player, Money> bettings, final Player dealer) {
        Map<Player, Integer> profits = calculateParticipantsProfit(bettings, dealer);
        return new ProfitResult(profits);
    }

    private static Map<Player, Integer> calculateParticipantsProfit(Map<Player, Money> bettings, Player dealer) {
        return bettings.keySet().stream()
                .collect(Collectors.toMap(
                        Function.identity(),
                        participant -> calculateParticipantProfit(bettings, dealer, participant))
                );
    }

    private static int calculateParticipantProfit(Map<Player, Money> bettings, Player dealer, Player participant) {
        Result participantResult = Result.calculateResult(dealer, participant);
        return participantResult.calculateProfit(bettings.get(participant));
    }

    private static int calculateDealerProfit(Collection<Integer> profits) {
        return profits.stream().mapToInt(Result::calculateOppositeProfit).sum();
    }

    public List<Player> players() {
        return new ArrayList<>(participantProfits.keySet());
    }

    public int profit(final Player player) {
        return participantProfits.get(player);
    }

    public int dealerProfit() {
        return calculateDealerProfit(participantProfits.values());
    }
}
