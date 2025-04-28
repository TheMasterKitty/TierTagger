package themasterkitty.tiertagger.data;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.annotation.Nullable;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Fetcher {
    private static final HashMap<Map.Entry<UUID, Site>, TierData> cache = new HashMap<>();

    @Nullable
    public static TierData fetchData(UUID id, Site site) {
        Map.Entry<UUID, Site> key = Map.entry(id, site);

        if (cache.containsKey(key)) return cache.get(key);

        try (HttpClient http = HttpClient.newHttpClient()) {
            HttpRequest request = HttpRequest.newBuilder(
                            URI.create("https://" + site.domain + "/api/profile/" + id.toString().replaceAll("-", "")))
                    .header("accept", "application/json")
                    .build();

            HttpResponse<String> response = http.send(request, HttpResponse.BodyHandlers.ofString());

            JsonObject obj = JsonParser.parseString(response.body()).getAsJsonObject();

            HashMap<Mode, Ranking> ranks = new HashMap<>();
            JsonObject rankings = obj.get("rankings").getAsJsonObject();
            for (String mode : rankings.keySet()) {
                JsonObject data = rankings.get(mode).getAsJsonObject();
                ranks.put(Mode.valueOf(mode), new Ranking(
                        data.get("tier").getAsInt(),
                        data.get("pos").getAsInt() == 0,
                        data.has("peak_tier") ? data.get("peak_tier").getAsInt() : null,
                        data.has("peak_pos") ? data.get("peak_pos").getAsInt() == 0 : null,
                        data.get("retired").getAsBoolean(),
                        data.get("attained").getAsLong()
                ));
            }
            cache.put(key, new TierData(ranks,
                    obj.get("overall").getAsInt(),
                    obj.get("points").getAsInt(),
                    Region.valueOf(obj.get("region").getAsString())
            ));
        }
        catch (Exception ex) {
            return null;
        }

        return cache.get(key);
    }
}
