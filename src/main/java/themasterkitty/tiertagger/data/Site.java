package themasterkitty.tiertagger.data;

public enum Site {
    McTiers("mctiers.com"),
    SubTiers("subtiers.net");

    public final String domain;
    Site(String domain) {
        this.domain = domain;
    }
}
