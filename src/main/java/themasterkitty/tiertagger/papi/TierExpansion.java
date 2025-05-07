package themasterkitty.tiertagger.papi;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import themasterkitty.tiertagger.TierTagger;
import themasterkitty.tiertagger.data.Mode;
import themasterkitty.tiertagger.data.Site;
import themasterkitty.tiertagger.data.TierData;
import themasterkitty.tiertagger.utils.Formatter;

import java.util.Arrays;

public class TierExpansion extends PlaceholderExpansion {
    @Override
    public @NotNull String getIdentifier() {
        return "tier";
    }

    @Override
    public @NotNull String getAuthor() {
        return "TheMasterKitty";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.1.0";
    }

    @Override
    public @Nullable String onRequest(OfflinePlayer player, @NotNull String params) {
        Site site;
        try {
            site = Arrays.stream(Site.values()).filter(s -> s.name().equalsIgnoreCase(params.split("_")[0])).findFirst().orElseThrow();
        }
        catch (Exception ex) { return null; }
        Mode mode;
        try {
            mode = Mode.valueOf(params.split("_")[1]);
        }
        catch (Exception ex) { return null; }
        boolean peak = params.endsWith("_peak");

        TierData data = site.fetcher.fetchData(player.getUniqueId());
        if (data == null || !data.rankings().containsKey(mode)) return TierTagger.INSTANCE.getConfig().getString("untested-text");
        return Formatter.colorTier(peak ? data.rankings().get(mode).peakString() : data.rankings().get(mode).tierString());
    }

    @Override
    public @Nullable String onPlaceholderRequest(Player player, @NotNull String params) {
        return onRequest(player, params);
    }
}
