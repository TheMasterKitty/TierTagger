package themasterkitty.tiertagger;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import themasterkitty.tiertagger.data.Mode;
import themasterkitty.tiertagger.data.Site;
import themasterkitty.tiertagger.data.TierData;
import themasterkitty.tiertagger.utils.Formatter;
import themasterkitty.tiertagger.utils.PlayerUtil;

import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

public class RawPlayerTierExpansion extends PlaceholderExpansion {
    @Override
    public @NotNull String getIdentifier() {
        return "rawplayertier";
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

        Map.Entry<UUID, String> targetid = PlayerUtil.getUUID(params.split("_")[2]);
        if (targetid == null) return null;
        TierData data = site.fetcher.fetchData(targetid.getKey());
        if (data == null || !data.rankings().containsKey(mode)) return TierTagger.INSTANCE.getConfig().getString("untested-text");
        return peak ? data.rankings().get(mode).peakString() : data.rankings().get(mode).tierString();
    }

    @Override
    public @Nullable String onPlaceholderRequest(Player player, @NotNull String params) {
        return onRequest(player, params);
    }
}
