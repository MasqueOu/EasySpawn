package be.maksxou.spigotmc.listeners;

import be.maksxou.spigotmc.EasySpawn;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class EasySpawnListeners implements Listener {

    private final EasySpawn easySpawn;
    public EasySpawnListeners(EasySpawn easySpawn) {
        this.easySpawn = easySpawn;
    }

    @EventHandler(ignoreCancelled = true)
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        if (Boolean.parseBoolean(easySpawn.getMessage("teleport.on_first_join")) && player.hasPlayedBefore() || Boolean.parseBoolean(easySpawn.getMessage("teleport.on_join"))) {
            player.teleport(easySpawn.spawnLoc);
        }
    }
}
