package themasterkitty.tiertagger.utils;

import net.md_5.bungee.api.ChatColor;
import themasterkitty.tiertagger.TierTagger;
import themasterkitty.tiertagger.data.Mode;
import themasterkitty.tiertagger.data.Ranking;

import java.util.Comparator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Formatter {
    public static String format(String text) {
        Pattern hexPattern = Pattern.compile("&(#[a-fA-F0-9]{6})");
        Matcher matcher = hexPattern.matcher(text);
        StringBuilder builder = new StringBuilder();

        while (matcher.find()) {
            matcher.appendReplacement(builder, ChatColor.of(matcher.group(1)).toString());
        }

        matcher.appendTail(builder);
        return ChatColor.translateAlternateColorCodes('&', builder.toString()).replaceAll("\\n", "\n");
    }

    public static String colorTier(String tierString) {
        return addColor(tierString, tierString);
    }
    public static String addColor(String string, String key) {
        return format(TierTagger.INSTANCE.getConfig().getString("colors." + key) + string);
    }

    public static String formatRankings(Map<Mode, Ranking> rankings) {
        StringBuilder text = new StringBuilder();
        for (Map.Entry<Mode, Ranking> r : rankings.entrySet().stream().sorted(Comparator.comparingInt(e -> e.getValue().toInt())).toList()) {
            text.append(addColor(r.getKey().name, r.getKey().name())).append(": ").append(colorTier(r.getValue().tierString()));
            if (r.getValue().nullablePeakString() != null) {
                text.append(format(TierTagger.INSTANCE.getConfig().getString("peaked-format")).replaceAll("%tier%", colorTier(r.getValue().nullablePeakString())));
            }
            text.append("\n");
        }
        return text.toString().trim();
    }
}
