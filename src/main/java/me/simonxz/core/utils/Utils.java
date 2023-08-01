package me.simonxz.core.utils;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.text.NumberFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    public static String color(String message) {
        return ChatColor.translateAlternateColorCodes('&', translateHexColorCodes(message));
    }


    public static String translateHexColorCodes(String message) {
        Pattern hexPattern = Pattern.compile("&#([A-Fa-f0-9]{6})");
        Matcher matcher = hexPattern.matcher(message);
        StringBuffer buffer = new StringBuffer(message.length() + 32);
        while (matcher.find()) {
            String group = matcher.group(1);
            matcher.appendReplacement(buffer, "§x§" + group.charAt(0) + '§' + group.charAt(1) + '§' + group.charAt(2) + '§' + group.charAt(3) + '§'
            + group.charAt(4) + '§' + group.charAt(5));
        }
        return matcher.appendTail(buffer).toString();
    }

    public static ItemStack makeItem(Material material, String displayName, String... lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(displayName);
        itemMeta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ENCHANTS });
        itemMeta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES });
        itemMeta.setLore(Arrays.asList(lore));
        item.setItemMeta(itemMeta);
        return item;
    }

    public static double randomDouble(double ranMin, double ranMax) {
        Random r = new Random();
        double randomValue = ranMin + (ranMax - ranMin) * r.nextDouble();
        if (randomValue > 0.0D)
            return randomValue;
        return 0.0D;
    }

    public static String insertCommas(long value) {
        return NumberFormat.getIntegerInstance().format(value);
    }

    public static String format(long value) {
        if (value == Long.MIN_VALUE) return format(Long.MIN_VALUE + 1);
        if (value < 0) return "-" + format(-value);
        if (value < 1000) return Long.toString(value);

        NavigableMap<Long, String> suffixes = new TreeMap<>();
        suffixes.put(1_000L, "k");
        suffixes.put(1_000_000L, "M");
        suffixes.put(1_000_000_000L, "B");
        suffixes.put(1_000_000_000_000L, "T");
        suffixes.put(1_000_000_000_000_000L, "Q");
        suffixes.put(1_000_000_000_000_000_000L, "QT");

        Map.Entry<Long, String> e = suffixes.floorEntry(value);
        Long divideBy = e.getKey();
        String suffix = e.getValue();

        long truncated = value / (divideBy / 10);
        boolean hasDecimal = truncated < 100 && (truncated / 10d) != (truncated / 10);
        return hasDecimal ? (truncated / 10d) + suffix : (truncated / 10) + suffix;
    }

    private static final TreeMap<Integer, String> roman = new TreeMap<>();

    static {
        roman.put(Integer.valueOf(1000), "M");
        roman.put(Integer.valueOf(900), "CM");
        roman.put(Integer.valueOf(500), "D");
        roman.put(Integer.valueOf(400), "CD");
        roman.put(Integer.valueOf(100), "C");
        roman.put(Integer.valueOf(90), "XC");
        roman.put(Integer.valueOf(50), "L");
        roman.put(Integer.valueOf(40), "XL");
        roman.put(Integer.valueOf(10), "X");
        roman.put(Integer.valueOf(9), "IX");
        roman.put(Integer.valueOf(5), "V");
        roman.put(Integer.valueOf(4), "IV");
        roman.put(Integer.valueOf(1), "I");
    }

    public static final String toRoman(int number) {
        if (number <= 0)
            return "Zero";
        int l = ((Integer)roman.floorKey(Integer.valueOf(number))).intValue();
        if (number == l)
            return roman.get(Integer.valueOf(number));
        return String.valueOf(roman.get(Integer.valueOf(l))) + toRoman(number - l);
    }

    public static int convertInteger(String str) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static final String capitalize(String str) {

        if (str == null || str.length() == 0)
            return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1);

    }
}
