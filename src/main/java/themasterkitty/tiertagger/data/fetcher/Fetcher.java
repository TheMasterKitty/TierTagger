package themasterkitty.tiertagger.data.fetcher;

import themasterkitty.tiertagger.data.TierData;

import java.util.Timer;
import java.util.UUID;

public abstract class Fetcher {
    protected static Timer timer = new Timer();
    public abstract TierData fetchData(UUID id);
}
