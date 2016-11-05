package de.sakul6499.devathon.listener;

import de.sakul6499.devathon.JSONLocation;
import de.sakul6499.devathon.cart.CartManager;
import de.sakul6499.devathon.cart.CartModel;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

/**
 * Created by lukas on 05.11.16.
 */
public class CreateListener implements Listener {

    private ItemStack check(ItemStack itemStack) {
        if (itemStack == null) return null;
        if (!itemStack.hasItemMeta()) return null;

        ItemMeta itemMeta = itemStack.getItemMeta();
        if (!itemMeta.hasLore()) return null;

        List<String> lore = itemMeta.getLore();
        if(lore.size() <= 0) return null;
        if (!itemMeta.getLore().get(0).equals(CartModel.ITEM_NAME)) return null;

        return itemStack;
    }

    @EventHandler
    public void PlayerInteractEvent(PlayerInteractEvent event) {
        if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;

        ItemStack itemStack;
        if ((itemStack = check(event.getPlayer().getInventory().getItemInMainHand())) != null) {
            CartManager.GetInstance().registerCart(new CartModel(itemStack.getItemMeta().getDisplayName(), new JSONLocation(event.getClickedBlock().getLocation().add(0, 1, 0))).spawn());
            event.getPlayer().getInventory().setItemInMainHand(new ItemStack(Material.AIR));
        } else if((itemStack = check(event.getPlayer().getInventory().getItemInOffHand())) != null) {
            CartManager.GetInstance().registerCart(new CartModel(itemStack.getItemMeta().getDisplayName(), new JSONLocation(event.getClickedBlock().getLocation().add(0, 1, 0))).spawn());
            event.getPlayer().getInventory().setItemInMainHand(new ItemStack(Material.AIR));
        }
    }
}
