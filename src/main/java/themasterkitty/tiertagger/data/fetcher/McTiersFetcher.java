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
import java.util.UUID;

public class McTiersFetcher extends Fetcher {
    @Nullable
    public TierData fetchData(UUID id) {
        if (cache.containsKey(id)) return cache.get(id);

        try (HttpClient http = HttpClient.newHttpClient()) {
            HttpRequest request = HttpRequest.newBuilder(
                            URI.create("https://mctiers.com/api/profile/" + id.toString().replaceAll("-", "")))
                    .header("accept", "application/json")
                    .build();

            HttpResponse<String> response = http.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) return null;

            JsonObject obj = JsonParser.parseString(response.body()).getAsJsonObject();

            HashMap<Mode, Ranking> ranks = new HashMap<>();
            JsonObject rankings = obj.get("rankings").getAsJsonObject();
            for (String mode : rankings.keySet()) {
                JsonObject data = rankings.get(mode).getAsJsonObject();
                ranks.put(Mode.valueOf(mode), new Ranking(
                        data.get("tier").getAsInt(),
                        data.get("pos").getAsInt() == 0,
                        data.has("peak_tier") && !data.get("peak_tier").isJsonNull() ? data.get("peak_tier").getAsInt() : null,
                        data.has("peak_pos") && !data.get("peak_pos").isJsonNull() ? data.get("peak_pos").getAsInt() == 0 : null,
                        data.get("retired").getAsBoolean()
                ));
            }
            cache.put(id, new TierData(ranks,
                    obj.get("overall").getAsInt(),
                    obj.get("points").getAsInt(),
                    Region.valueOf(obj.get("region").getAsString())
            ));
        }
        catch (Exception ex) {
            return null;
        }

        return cache.get(id);
    }
}
