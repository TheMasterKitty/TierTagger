package themasterkitty.tiertagger;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import themasterkitty.tiertagger.data.Fetcher;
import themasterkitty.tiertagger.data.Site;
import themasterkitty.tiertagger.data.TierData;
import themasterkitty.tiertagger.utils.Formatter;
import themasterkitty.tiertagger.utils.PlayerUtil;

import javax.annotation.Nonnull;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class TiersCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {
        if (args.length != 1 && args.length != 2) return false;

        Site site;
        if (args.length == 2) site = Arrays.stream(Site.values()).filter(s -> s.name().equalsIgnoreCase(args[1])).findFirst().orElse(null);
        else site = Site.McTiers;
        if (site == null) return false;

        new BukkitRunnable() {
            @Override
            public void run() {
                Map.Entry<UUID, String> p = PlayerUtil.getUUID(args[0]);
                if (p == null) {
                    sender.sendMessage(Formatter.format(TierTagger.INSTANCE.getConfig().getString("player-no-exist")));
                    return;
                }

                TierData data = Fetcher.fetchData(p.getKey(), site);
                if (data == null || data.rankings().isEmpty()) {
                    sender.sendMessage(Formatter.format(TierTagger.INSTANCE.getConfig().getString("player-isnt-tested")).replaceAll("%site%", site.name()));
                }
                else {
                    String title = Formatter.format(String.join("\n", TierTagger.INSTANCE.getConfig().getStringList("format-title")))
                            .replaceAll("%player%", p.getValue())
                            .replaceAll("%points%", NumberFormat.getInstance().format(data.points()))
                            .replaceAll("%overall%", "#" + NumberFormat.getInstance().format(data.overall()))
                            .replaceAll("%region%", data.region().name());
                    sender.sendMessage(title + "\n" + Formatter.formatRankings(data.rankings()));
                }
            }
        }.runTaskAsynchronously(TierTagger.INSTANCE);

        return true;
    }

    @Override
    public List<String> onTabComplete(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {
        if (args.length == 1) {
            return Bukkit.getOnlinePlayers().stream().map(Player::getName).filter(n -> n.toLowerCase().startsWith(args[0].toLowerCase())).toList();
        }
        else if (args.length == 2) {
            return Arrays.stream(Site.values()).map(Enum::name).filter(s -> s.toLowerCase().startsWith(args[1].toLowerCase())).toList();
        }
        return List.of();
    }
}
