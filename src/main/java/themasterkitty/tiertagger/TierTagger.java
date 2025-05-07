package themasterkitty.tiertagger;

import com.cjcrafter.foliascheduler.FoliaCompatibility;
import com.cjcrafter.foliascheduler.ServerImplementation;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import themasterkitty.tiertagger.papi.PlayerTierExpansion;
import themasterkitty.tiertagger.papi.RawPlayerTierExpansion;
import themasterkitty.tiertagger.papi.RawTierExpansion;
import themasterkitty.tiertagger.papi.TierExpansion;

import java.util.Objects;
import java.util.Timer;

public final class TierTagger extends JavaPlugin {
    public static TierTagger INSTANCE;
    public static Timer timer = new Timer();
    public static ServerImplementation scheduler;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        INSTANCE = this;
        scheduler = new FoliaCompatibility(this).getServerImplementation();

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
