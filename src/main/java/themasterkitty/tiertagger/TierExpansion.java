package themasterkitty.tiertagger;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import themasterkitty.tiertagger.data.Fetcher;
import themasterkitty.tiertagger.data.Mode;
import themasterkitty.tiertagger.data.TierData;
import themasterkitty.tiertagger.utils.Formatter;

import java.util.Objects;

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
        return "1.0";
    }

    @Override
    public @Nullable String onRequest(OfflinePlayer player, @NotNull String params) {
        Mode mode;
        try {
            mode = Mode.valueOf(params.split("_")[0]);
        }
        catch (Exception ex) { return null; }
        boolean peak = params.split("_").length > 1 && Objects.equals(params.split("_")[0], "peak");

        TierData data = Fetcher.fetchData(player.getUniqueId(), mode.site);
        if (data == null || !data.rankings().containsKey(mode)) return TierTagger.INSTANCE.getConfig().getString("untested-text");
        return Formatter.colorTier(peak ? data.rankings().get(mode).peakString() : data.rankings().get(mode).tierString());
    }

    @Override
    public @Nullable String onPlaceholderRequest(Player player, @NotNull String params) {
        return onRequest(player, params);
    }
}
