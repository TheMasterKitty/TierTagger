package themasterkitty.tiertagger.data.fetcher;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import themasterkitty.tiertagger.data.Mode;
import themasterkitty.tiertagger.data.Ranking;
import themasterkitty.tiertagger.data.Region;
import themasterkitty.tiertagger.data.TierData;

import javax.annotation.Nullable;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.TimerTask;
import java.util.UUID;

public class OceTiersFetcher extends Fetcher {
    private static final HashMap<UUID, TierData> cache = new HashMap<>();

    static {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                cache.clear();
            }
        }, 0, 60 * 60 * 1000);
    }

    @Nullable
    public TierData fetchData(UUID id) {
        if (cache.containsKey(id)) return cache.get(id);

        try (HttpClient http = HttpClient.newHttpClient()) {
            HttpRequest request = HttpRequest.newBuilder(
                            URI.create("https://api.yeahjenni.xyz/ocetiers/uuid/" + id.toString()))
                    .header("accept", "application/json")
                    .build();

            HttpResponse<String> response = http.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) return null;

            JsonObject obj = JsonParser.parseString(response.body()).getAsJsonObject();

            HashMap<Mode, Ranking> ranks = new HashMap<>();
            JsonObject rankings = obj.get("gameModes").getAsJsonObject();
            for (String mode : rankings.keySet()) {
                JsonObject data = rankings.get(mode).getAsJsonObject();
                if (data.get("tier").isJsonNull()) continue;

                ranks.put(Mode.valueOf(mode), new Ranking(
                        Integer.parseUnsignedInt(data.get("tier").getAsString().substring(2, 3)),
                        data.get("isLT").getAsBoolean(),
                        null,
                        null,
                        false
                ));
            }
            cache.put(id, new TierData(ranks,
                    obj.get("leaderboardPosition").getAsInt(),
                    obj.get("score").getAsInt(),
                    Region.OC
            ));
        }
        catch (Exception ex) {
            return null;
        }

        return cache.get(id);
    }
}
