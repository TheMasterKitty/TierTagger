package themasterkitty.tiertagger.utils;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import themasterkitty.tiertagger.TierTagger;
import themasterkitty.tiertagger.data.Site;

public class PlayerListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        TierTagger.scheduler.async().runNow(() -> {
            for (Site site : Site.values()) {
                site.fetcher.fetchData(e.getPlayer().getUniqueId());
            }
        });
    }
    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e) {
        for (Site site : Site.values()) {
            site.fetcher.removeCache(e.getPlayer().getUniqueId());
        }
    }
    @EventHandler
    public void onPlayerLeave(PlayerKickEvent e) {
        for (Site site : Site.values()) {
            site.fetcher.removeCache(e.getPlayer().getUniqueId());
        }
    }
}
