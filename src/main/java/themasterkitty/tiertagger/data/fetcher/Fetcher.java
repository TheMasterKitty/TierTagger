package themasterkitty.tiertagger.data.fetcher;

import themasterkitty.tiertagger.TierTagger;
import themasterkitty.tiertagger.data.TierData;

import java.util.TimerTask;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public abstract class Fetcher {
    public abstract TierData fetchData(UUID id);

    protected final ConcurrentHashMap<UUID, TierData> cache = new ConcurrentHashMap<>();

    public Fetcher() {
        TierTagger.timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                cache.clear();
            }
        }, 0, 60 * 60 * 1000);
    }
    public void removeCache(UUID id) {
        cache.remove(id);
    }
}
