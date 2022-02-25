package xyz.lotho.me.handlers;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.PlayerInventory;
import xyz.lotho.me.SkyHub;

public class handleInventoryMovement implements Listener {

    SkyHub instance;

    public handleInventoryMovement(SkyHub instance) {
        this.instance = instance;
    }

    @EventHandler
    public void onInventoryMove(InventoryClickEvent event) {
        Inventory inventory = event.getInventory();

        if (inventory.getType() == InventoryType.CRAFTING || event.getSlotType() == InventoryType.SlotType.ARMOR) event.setCancelled(true);
    }
}
