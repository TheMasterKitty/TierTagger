package themasterkitty.tiertagger.data;

import javax.annotation.Nullable;

public record Ranking(int tier, boolean pos, @Nullable Integer peak_tier, @Nullable Boolean peak_pos, boolean retired) {
    public String tierString() {
        return (pos ? "H" : "L") + "T" + tier;
    }
    public String peakString() {
        if (peak_tier == null || peak_pos == null) return tierString();
        return (peak_pos ? "H" : "L") + "T" + peak_tier;
    }
    @Nullable
    public String nullablePeakString() {
        if (peak_tier == null || peak_pos == null) return null;
        if (peak_tier == tier && peak_pos == pos) return null;
        return (peak_pos ? "H" : "L") + "T" + peak_tier;
    }

    public int toInt() {
        return tier * 2 + (pos ? 0 : 1);
    }
}
