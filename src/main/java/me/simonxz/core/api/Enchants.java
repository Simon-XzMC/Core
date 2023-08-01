package me.simonxz.core.api;

import me.simonxz.core.Main;

public class Enchants {
    private static Main plugin = (Main)Main.getPlugin(Main.class);

    private String enchant;

    private int max;

    private long price;

    private Double proc;

    private Double exponent;

    private int requirement;

    public Enchants(String enchant) {
        this.enchant = enchant;
        this.max = 0;
        this.price = 0L;
        this.proc = Double.valueOf(0.0D);
        this.exponent = Double.valueOf(0.0D);
        this.requirement = 0;
        plugin.getEnchantManager().addEnhcant(this);
    }

    public String getEnchant() {
        return this.enchant;
    }

    public int getMax() {
        return this.max;
    }

    public void setMax(int i) {
        this.max = i;
    }

    public long getPrice() {
        return this.price;
    }

    public void setPrice(long i) {
        this.price = i;
    }

    public Double getProc() {
        return this.proc;
    }

    public void setProc(Double d) {
        this.proc = d;
    }

    public double getExponent() {
        return this.exponent.doubleValue();
    }

    public void setExponent(double i) {
        this.exponent = Double.valueOf(i);
    }

    public int getRequirement() {
        return this.requirement;
    }

    public void setRequirement(int i) {
        this.requirement = i;
    }
}