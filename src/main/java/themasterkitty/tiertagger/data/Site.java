package themasterkitty.tiertagger.data;

import themasterkitty.tiertagger.data.fetcher.*;

public enum Site {
    McTiers(new McTiersFetcher()),
    SubTiers(new SubTiersFetcher()),
    OceTiers(new OceTiersFetcher()),
    McTiersIO(new McTiersIOFetcher());

    public final Fetcher fetcher;
    Site(Fetcher fetcher) {
        this.fetcher = fetcher;
    }
}
