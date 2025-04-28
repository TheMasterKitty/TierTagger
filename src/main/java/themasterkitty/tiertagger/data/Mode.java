package themasterkitty.tiertagger.data;

public enum Mode {
    vanilla("Vanilla", Site.McTiers),
    sword("Sword", Site.McTiers),
    uhc("UHC", Site.McTiers),
    pot("Pot", Site.McTiers),
    nethop("Neth OP", Site.McTiers),
    smp("SMP", Site.McTiers),
    axe("Axe", Site.McTiers),
    creeper("Creeper", Site.SubTiers),
    bow("Bow", Site.SubTiers),
    manhunt("Manhunt", Site.SubTiers),
    og_vanilla("OG Vanilla", Site.SubTiers),
    bed("Bed", Site.SubTiers),
    minecart("Minecart", Site.SubTiers),
    mace("Mace", Site.SubTiers),
    speed("Speed", Site.SubTiers),
    iron_pot("Iron Pot", Site.SubTiers),
    dia_smp("Dia SMP", Site.SubTiers),
    dia_crystal("Diamond Crystal", Site.SubTiers),
    elytra("Elytra", Site.SubTiers),
    trident("Trident", Site.SubTiers),
    debuff("Debuff", Site.SubTiers);

    public final String name;
    public final Site site;
    Mode(String name, Site site) {
        this.name = name;
        this.site = site;
    }
}
