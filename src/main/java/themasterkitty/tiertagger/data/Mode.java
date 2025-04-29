package themasterkitty.tiertagger.data;

public enum Mode {
    vanilla("Vanilla"),
    sword("Sword"),
    uhc("UHC"),
    pot("Pot"),
    nethop("Netherite OP"),
    smp("SMP"),
    axe("Axe"),
    axeoce("Axe"),
    creeper("Creeper"),
    bow("Bow"),
    manhunt("Manhunt"),
    og_vanilla("OG Vanilla"),
    bed("Bed"),
    minecart("Minecart"),
    mace("Mace"),
    speed("Speed"),
    iron_pot("Iron Pot"),
    dia_smp("Dia SMP"),
    dia_crystal("Diamond Crystal"),
    elytra("Elytra"),
    trident("Trident"),
    debuff("Debuff"),
    cart("Cart"),
    crystal("Crystal"),
    diamondPot("Diamond Pot"),
    diamondSmp("Diamond SMP"),
    netheritePot("Netherite Pot"),
    neth_pot("Netherite Pot");

    public final String name;
    Mode(String name) {
        this.name = name;
    }
}
