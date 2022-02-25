package xyz.lotho.me.interfaces.utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import xyz.lotho.me.utils.Item;

public class Menu {
    protected final Inventory inventory;
    protected final String inventoryName;
    protected final int size;

    protected ItemStack FILLER_ITEM = Item.createItemShort(Material.STAINED_GLASS_PANE, 7, " ");

    public Menu(String inventoryName, int size) {
        this.inventoryName = inventoryName;
        this.size = size;

        this.inventory = Bukkit.createInventory(null, this.size, this.inventoryName);
    }

    public String getName() {
        return this.inventoryName;
    }

    public int getSize() {
        return this.size;
    }

    public Inventory getInventory() {
        return this.inventory;
    }

    public void open(Player player) {
        player.openInventory(this.inventory);
    }

    public void setItems() {}

    public void fillRemainingSlots() {
        for (int i = 0; i < this.getSize(); i++) {
            if (this.inventory.getItem(i) == null) {
                this.inventory.setItem(i, this.FILLER_ITEM);
            }
        }
    }
}
