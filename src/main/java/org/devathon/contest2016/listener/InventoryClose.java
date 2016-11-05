package org.devathon.contest2016.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

/**
 * Created by lukas on 05.11.16.
 */
public class InventoryClose implements Listener {

    @EventHandler
    public void inventoryCloseEvent(InventoryCloseEvent event) {
        System.out.println("Inventory Closed!");
        event.getPlayer().sendMessage("asd");
    }
}
