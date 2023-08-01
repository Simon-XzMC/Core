package me.simonxz.core.pickaxes;

public class PickaxeLevels {

    public int levelupCost(int level, int prestige) {

        int basexp = 1000 * (prestige + 1);
        double exponent = 1.25;

        return (int) Math.round(basexp * (exponent * level));
    }

}

