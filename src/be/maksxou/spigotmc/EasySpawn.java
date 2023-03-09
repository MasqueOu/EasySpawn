package be.maksxou.spigotmc;

import be.maksxou.spigotmc.commands.SpawnCommands;
import be.maksxou.spigotmc.listeners.EasySpawnListeners;
import be.maksxou.spigotmc.utils.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class EasySpawn extends JavaPlugin {

    private FileConfiguration fileConfiguration;

    public Location spawnLoc;

    @Override
    public void onEnable() {
        Bukkit.getLogger().info("--------------------------------------------");
        Bukkit.getLogger().info("EasySpawn by MasqueOù");
        Bukkit.getLogger().info("Hi ! EasySpawn is free and opensource.");
        Bukkit.getLogger().info("Thanks for using my plugin.");
        Bukkit.getLogger().info("--------------------------------------------");

        initStats();

        getConfig().options().copyDefaults(true);
        saveConfig();
        rlConfig();

        Objects.requireNonNull(getCommand("spawn")).setExecutor(new SpawnCommands(this));

        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new EasySpawnListeners(this), this);
    }

    private void initStats() {
        int pluginId = 17874;
        Metrics metrics = new Metrics(this, pluginId);

        metrics.addCustomChart(new Metrics.MultiLineChart("players_and_servers", () -> {
            Map<String, Integer> valueMap = new HashMap<>();
            valueMap.put("servers", 1);
            valueMap.put("players", Bukkit.getOnlinePlayers().size());
            return valueMap;
        }));
    }

    public void rlConfig() {
        reloadConfig();
        fileConfiguration = null;

        fileConfiguration = getConfig();
        this.spawnLoc = StringToLocation(getMessage("location.loc"));
    }

    public String getMessage(String string) {
        if(fileConfiguration.get(string) != null) {
            return Objects.requireNonNull(fileConfiguration.get(string)).toString().replace("&", "§");
        } else {
            return "Hello, i found a problem in the configuration file. Reset the configuration and contact me if the problem persists. Error code: " + string;
        }
    }

    @Override
    public void onDisable() {
        saveConfig();
    }

    public Location StringToLocation(String locationString) {
        System.out.println(locationString);
        Location loc;
        String[] parts = locationString.split(",");
        String worldName = parts[0];
        double x = Double.parseDouble(parts[1]);
        double y = Double.parseDouble(parts[2]);
        double z = Double.parseDouble(parts[3]);
        float yaw = Float.parseFloat(parts[4]);
        float pitch = Float.parseFloat(parts[5]);
        loc = new Location(Bukkit.getWorld(worldName), x, y, z, yaw, pitch);
        return loc;
    }

    public String TransformLocationToString(Location location) {
        String worldName = Objects.requireNonNull(location.getWorld()).getName();
        double x = location.getX();
        double y = location.getY();
        double z = location.getZ();
        float yaw = location.getYaw();
        float pitch = location.getPitch();
        return worldName + "," + x + "," + y + "," + z + "," + yaw + "," + pitch;
    }
}
