package themasterkitty.tiertagger.data;

import java.util.Map;

public record TierData(Map<Mode, Ranking> rankings, int overall, int points, Region region) {

}
