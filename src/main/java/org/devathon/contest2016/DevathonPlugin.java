package org.devathon.contest2016;

import org.bukkit.plugin.java.JavaPlugin;
import org.devathon.contest2016.listener.InventoryClose;

public class DevathonPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        System.out.println("Enabled!");

        getServer().getPluginManager().registerEvents(new CommandHandler(), this);
        getServer().getPluginManager().registerEvents(new InventoryClose(), this);
    }

    @Override
    public void onDisable() {
        System.out.println("Disabled!");
    }
}
