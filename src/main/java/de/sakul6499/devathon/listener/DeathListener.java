package de.sakul6499.devathon.listener;

import de.sakul6499.devathon.cart.CartManager;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleDestroyEvent;

/**
 * Created by lukas on 05.11.16.
 */
public class DeathListener implements Listener {

    @EventHandler
    public void EntityDeathEvent(VehicleDestroyEvent event) {
        if (event.getVehicle().getType().equals(EntityType.MINECART_CHEST) || event.getVehicle().getType().equals(EntityType.ARMOR_STAND)) {
            if (CartManager.GetInstance().remove(event.getVehicle().getLocation())) {
                event.setCancelled(true);
            }
        }
    }
}
