package de.sakul6499.devathon;

import de.sakul6499.devathon.cart.CartManager;
import de.sakul6499.devathon.cart.CartModel;
import de.sakul6499.devathon.listener.ChatListener;
import de.sakul6499.devathon.listener.CreateListener;
import de.sakul6499.devathon.listener.DamageListener;
import de.sakul6499.devathon.listener.InventoryClose;
import de.sakul6499.devathon.script.ScriptHeap;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Devathon extends JavaPlugin {

    private final static File SCRIPTS_FOLDER;

    static {
        SCRIPTS_FOLDER = new File("scripts");
    }

    public static JavaPlugin PLUGIN_INSTANCE;

    @Override
    public void onLoad() {
        PLUGIN_INSTANCE = this;
    }

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new ChatListener(), this);
        getServer().getPluginManager().registerEvents(new InventoryClose(), this);
        getServer().getPluginManager().registerEvents(new DamageListener(), this);
        getServer().getPluginManager().registerEvents(new CreateListener(), this);

        try {
            File cartFile = new File(getDataFolder(), "carts.json");
            if (cartFile.exists()) {
                try (FileInputStream fileInputStream = new FileInputStream(cartFile)) {
                    byte[] bytes = new byte[fileInputStream.available()];
                    if (fileInputStream.read(bytes) > 0) {
                        JSONArray jsonArray = (JSONArray) new JSONParser().parse(new String(bytes));
                        for (Object object : jsonArray) {
                            CartManager.GetInstance().registerCart(CartModel.FromJSON((String) object));
                        }
                    }
                }
            }

            File[] files = SCRIPTS_FOLDER.listFiles((dir, name) -> name.endsWith(".jar"));
            if (files == null) {
                System.out.println("No Plugins found!");
            } else {
                for (File file : files) {
                    ScriptHeap.GetInstance().createPlugin(file);
                }

                ScriptHeap.GetInstance().startup();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Something gone wrong during startup!");
        }

        CartManager.GetInstance().spawnAll();
    }

    @Override
    public void onDisable() {
        CartManager.GetInstance().killAll();

        try {
            ScriptHeap.GetInstance().shutdown();

            File cartFile = new File(getDataFolder(), "carts.json");
            if (!cartFile.getParentFile().exists()) {
                cartFile.getParentFile().mkdirs();
            }
            if (!cartFile.exists()) {
                cartFile.createNewFile();
            }

            try (FileOutputStream fileOutputStream = new FileOutputStream(cartFile)) {
                fileOutputStream.write(CartManager.GetInstance().toString().getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Something gone wrong during shutdown!");
        }
    }
}
