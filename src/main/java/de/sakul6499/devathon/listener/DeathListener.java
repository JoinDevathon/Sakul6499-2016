package de.sakul6499.devathon.listener;

import de.sakul6499.devathon.cart.CartManager;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.vehicle.VehicleDamageEvent;
import org.bukkit.event.vehicle.VehicleDestroyEvent;

/**
 * Created by lukas on 05.11.16.
 */
public class DeathListener implements Listener {

    // TODO: will be replaced by inventory

    @EventHandler
    public void EntityDeathEvent(VehicleDestroyEvent event) {
        // TODO: than just block

        if (event.getVehicle().getType().equals(EntityType.MINECART_CHEST) || event.getVehicle().getType().equals(EntityType.ARMOR_STAND)) {
            if (CartManager.GetInstance().kill(event.getVehicle().getLocation())) {
                event.setCancelled(true);
            }
        }
    }
}
