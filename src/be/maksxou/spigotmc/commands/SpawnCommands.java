package be.maksxou.spigotmc.commands;

import be.maksxou.spigotmc.EasySpawn;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnCommands implements CommandExecutor {

    private final EasySpawn easySpawn;

    public SpawnCommands(EasySpawn easySpawn) {
        this.easySpawn = easySpawn;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(easySpawn.getMessage(easySpawn.getMessage("main.prefix") + " " + "message.sender_is_not_player"));
            return false;
        }
        Player player = (Player) sender;

        if (args.length == 0) {
            teleport(player);
            return false;
        } else if (args.length == 1) {
            if(args[0].equalsIgnoreCase("reload")) {
                reloadConfiguration(player);
                return false;
            } else if(args[0].equalsIgnoreCase("help")) {
                sendHelp(player);
                return false;
            } else if(args[0].equalsIgnoreCase("define")) {
                setSpawn(player);
                return false;
            } else {
                player.sendMessage(easySpawn.getMessage(easySpawn.getMessage("main.prefix") + " " + "message.command_doesnt_exist"));
                return false;
            }
        } else {
            player.sendMessage(easySpawn.getMessage(easySpawn.getMessage("main.prefix") + " " + "message.command_doesnt_exist"));
            return false;
        }

    }

    private void teleport(Player player){
        if (!player.hasPermission(easySpawn.getMessage("permission.spawn"))) {
            player.sendMessage(easySpawn.getMessage(easySpawn.getMessage("main.prefix") + " " + "message.not_permission"));
            return;
        }
        player.teleport(easySpawn.spawnLoc);
        if(Boolean.parseBoolean(easySpawn.getMessage(easySpawn.getMessage("teleport.message_on_teleport") + " " + "teleport.message_on_teleport"))){
            player.sendMessage(easySpawn.getMessage(easySpawn.getMessage("main.prefix") + " " + "message.teleport_message"));
        }
    }

    private void setSpawn(Player player){
        if (!player.hasPermission(easySpawn.getMessage("permission.define"))) {
            player.sendMessage(easySpawn.getMessage(easySpawn.getMessage("main.prefix") + " " + "message.not_permission"));
            return;
        }
        Location player_Location = player.getLocation();
        easySpawn.spawnLoc = player_Location;
        String result = easySpawn.TransformLocationToString(player_Location);
        easySpawn.getConfig().set("location.loc", result);
        easySpawn.saveConfig();
        player.sendMessage(easySpawn.getMessage("main.prefix") + " " + easySpawn.getMessage("message.success_creating_spawn"));
    }

    private void reloadConfiguration(Player player){
        if (!player.hasPermission(easySpawn.getMessage("permission.reload_plugin"))) {
            player.sendMessage(easySpawn.getMessage(easySpawn.getMessage("main.prefix") + " " + "message.not_permission"));
            return;
        }
        easySpawn.rlConfig();
        player.sendMessage(easySpawn.getMessage("main.prefix") + easySpawn.getMessage("message.success_reloading_config"));
    }

    private void sendHelp(Player player){
        if (!player.hasPermission(easySpawn.getMessage("permission.reload_plugin"))) {
            player.sendMessage(easySpawn.getMessage(easySpawn.getMessage("main.prefix") + " " + "message.not_permission"));
            return;
        }
            player.sendMessage(easySpawn.getMessage("main.prefix") + " " + ChatColor.GOLD + "/spawn | teleport command");
            player.sendMessage(easySpawn.getMessage("main.prefix") + " " + ChatColor.GOLD + "/spawn define | define the spawn point");
            player.sendMessage(easySpawn.getMessage("main.prefix") + " " + ChatColor.GOLD + "/spawn reload | reload the configuration");
            player.sendMessage(easySpawn.getMessage("main.prefix") + " " + ChatColor.GOLD + "/spawn help | you're here !");
    }

}
