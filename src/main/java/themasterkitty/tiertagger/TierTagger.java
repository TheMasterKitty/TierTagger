package themasterkitty.tiertagger;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class TierTagger extends JavaPlugin {
    public static TierTagger INSTANCE;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        INSTANCE = this;
        Objects.requireNonNull(getCommand("tiers")).setExecutor(new TiersCommand());
        Objects.requireNonNull(getCommand("tiers")).setTabCompleter(new TiersCommand());
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new TierExpansion().register();
            new RawTierExpansion().register();
            new PlayerTierExpansion().register();
            new RawPlayerTierExpansion().register();
        }
    }
}
