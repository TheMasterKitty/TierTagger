package themasterkitty.tiertagger.data;

import themasterkitty.tiertagger.data.fetcher.*;

public enum Site {
    McTiers(new McTiersFetcher()),
    SubTiers(new SubTiersFetcher()),
    OceTiers(new OceTiersFetcher()),
    PvPTiers(new PvPTiersFetcher());

    public final Fetcher fetcher;
    Site(Fetcher fetcher) {
        this.fetcher = fetcher;
    }
}
