package themasterkitty.tiertagger.utils;

import org.bukkit.Bukkit;
import themasterkitty.tiertagger.TierTagger;

import javax.annotation.Nullable;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.*;

public class PlayerUtil {
    private static UUID toUUID(String str) {
        try {
            return UUID.fromString(str);
        }
        catch (Exception ignored) {
            return UUID.fromString(String.format(
                    "%s-%s-%s-%s-%s",
                    str.substring(0, 8),
                    str.substring(8, 12),
                    str.substring(12, 16),
                    str.substring(16, 20),
                    str.substring(20)
            ));
        }
    }
    private static final HashMap<String, Map.Entry<UUID, String>> uuidcache = new HashMap<>();

    static {
        TierTagger.timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                uuidcache.clear();
            }
        }, 0, 6 * 60 * 60 * 1000);
    }

    @Nullable
    public static Map.Entry<UUID, String> getUUID(String ign) {
        if (Bukkit.getPlayerExact(ign) != null) return Map.entry(Objects.requireNonNull(Bukkit.getPlayerExact(ign)).getUniqueId(), Objects.requireNonNull(Bukkit.getPlayerExact(ign)).getName());
        if (uuidcache.containsKey(ign)) return uuidcache.get(ign);

        StringBuilder response = new StringBuilder();
        try {
            URL url = new URI("https://api.mojang.com/users/profiles/minecraft/" + ign).toURL();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                for (String line; (line = reader.readLine()) != null;) {
                    response.append(line);
                }
            }
        } catch (Exception ignored) {
            uuidcache.put(ign, null);
            return null;
        }

        String result = response.toString().replace(" ", "");
        if (result.contains("errorMessage")) {
            uuidcache.put(ign, null);
            return null;
        }
        Map.Entry<UUID, String> entry = Map.entry(toUUID(result.split("id\":\"")[1].split("\"")[0]), result.split("name\":\"")[1].split("\"")[0]);
        uuidcache.put(ign, entry);
        return entry;
    }
}
