package de.sakul6499.devathon.listener;

import de.sakul6499.devathon.cart.CartManager;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

/**
 * Created by lukas on 05.11.16.
 */
public class DamageListener implements Listener {

    // TODO: will be replaced by inventory

    @EventHandler
    public void EntityDeathEvent(EntityDamageEvent event) {
        // TODO: than just block

        if (event.getEntityType().equals(EntityType.ARMOR_STAND)) {
            if (CartManager.GetInstance().kill(event.getEntity().getLocation().add(0, 2, 0))) {
                event.setCancelled(true);
            }
        }
    }
}
