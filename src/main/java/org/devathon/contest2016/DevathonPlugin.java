package org.devathon.contest2016;

import org.bukkit.plugin.java.JavaPlugin;

public class DevathonPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        System.out.println("Enabled!");

        getServer().getPluginManager().registerEvents(new CommandHandler(), this);
    }

    @Override
    public void onDisable() {
        System.out.println("Disabled!");
    }
}

