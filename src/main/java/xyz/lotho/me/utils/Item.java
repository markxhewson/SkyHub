package xyz.lotho.me.utils;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;

public class Item {

    public static ItemStack createItem(final Material material, final String displayName, final String... lore) {
        final ItemStack item = new ItemStack(material);
        final ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(Chat.colorize(displayName));
        meta.setLore(Arrays.asList(lore));

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack createItemShort(final Material material, int id, final String displayName, final String... lore) {
        final ItemStack item = new ItemStack(material);
        item.setDurability((short) id);

        final ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(Chat.colorize(displayName));
        meta.setLore(Arrays.asList(lore));

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack createColouredItem(final Material material, Color color, final String displayName, final String... lore) {
        final ItemStack item = new ItemStack(material);
        final LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();

        meta.setDisplayName(Chat.colorize(displayName));
        meta.setLore(Arrays.asList(lore));
        meta.setColor(color);

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack createPlayerHead(final String name, final String displayName, final String... lore) {
        final ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
        final SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();

        skullMeta.setOwner(name);

        skullMeta.setDisplayName(Chat.colorize(displayName));
        skullMeta.setLore(Arrays.asList(lore));

        skull.setItemMeta(skullMeta);

        return skull;
    }
}
