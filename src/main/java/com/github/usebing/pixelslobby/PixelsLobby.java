package com.github.usebing.pixelslobby;

import com.github.usebing.pixelslobby.menu.GameMenu;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.mineacademy.fo.menu.model.ItemCreator;
import org.mineacademy.fo.plugin.SimplePlugin;
import org.mineacademy.fo.remain.CompMaterial;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public final class PixelsLobby extends SimplePlugin {
    private static Permission perms = null;
    private static Chat chat = null;

    @Override
    protected void onPluginStart() {
        setupChat();
        setupPermissions();
        new MyTask().runTaskTimer(this, 0, 20);
    }

    public static class MyTask extends BukkitRunnable {
        private int countdown;
        public MyTask() {
            this.countdown = 5;
        }
        String color(String text){
            return ChatColor.translateAlternateColorCodes('&', text);
        }
        @Override
        public void run() {
            for(Player player : Bukkit.getOnlinePlayers()){
                Scoreboard scoreboard = Objects.requireNonNull(Bukkit.getScoreboardManager()).getNewScoreboard();
                Objective objective = scoreboard.registerNewObjective("scoreboard", "dummy");

                objective.setDisplayName(color("&9&lPixelsMC"));
                objective.setDisplaySlot(DisplaySlot.SIDEBAR);
                DateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");
                Date date = new Date();
                Score first= objective.getScore(color("&7" + dateFormat.format(date)));
                Score second = objective.getScore("");
                Score third = objective.getScore(color("&9Rank: &r" + getChat().getPlayerSuffix(player)));
                Score forth = objective.getScore(color(" "));
                Score fifth = objective.getScore(color("&9pixelsmc.bar"));
                first.setScore(4);
                second.setScore(3);
                third.setScore(2);
                forth.setScore(1);
                fifth.setScore(0);
                player.setScoreboard(scoreboard);
            }
            countdown--; // decrement the counter
        }
    }
    private boolean setupChat() {
        RegisteredServiceProvider<Chat> rsp = getServer().getServicesManager().getRegistration(Chat.class);
        chat = rsp.getProvider();
        return chat != null;
    }
    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();
        return perms != null;
    }
    public static Chat getChat() {
        return chat;
    }
    @EventHandler
    public void onWorldChange(PlayerChangedWorldEvent event){
        Player player = event.getPlayer();
        if(player.getWorld().getName().equalsIgnoreCase("lobby")){
            if(player.hasPermission("pixelslobby.fly")){
                player.setAllowFlight(true);
            }
        } else {
            if(!player.hasPermission("pixelslobby.fly.bypass") || !player.getGameMode().equals(GameMode.CREATIVE)){
                player.setAllowFlight(false);
            }
        }
    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        if(player.hasPermission("pixelslobby.fly")){
            player.setAllowFlight(true);
        }
        if(player.hasPermission("pixelslobby.joinmsg")){
            event.setJoinMessage(ChatColor.translateAlternateColorCodes('&', getChat().getPlayerPrefix(player) + player.getName() + " &6joined the server!"));
        } else {
            event.setJoinMessage(null);
        }
        player.getInventory().setItem(0, ItemCreator.of(CompMaterial.COMPASS, "&cGame Menu &7(Right click)").make());
    }
    @EventHandler
    public void onRightClick(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if(Objects.requireNonNull(event.getItem()).isSimilar(ItemCreator.of(CompMaterial.COMPASS, "&cGame Menu &7(Right click)").make())){
            if(event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
                new GameMenu().displayTo(player);
            }
        }
    }
    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event){
        if(Objects.requireNonNull(event.getItemDrop()).getItemStack().isSimilar(ItemCreator.of(CompMaterial.COMPASS, "&cGame Menu &7(Right click)").make())){
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void onItemMove(InventoryMoveItemEvent event){
        if(event.getItem().isSimilar(ItemCreator.of(CompMaterial.COMPASS, "&cGame Menu &7(Right click)").make())) {
            event.setCancelled(true);
        }
    }
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onClick(InventoryClickEvent event) {
        if(Objects.requireNonNull(event.getCurrentItem()).isSimilar(ItemCreator.of(CompMaterial.COMPASS, "&cGame Menu &7(Right click)").make())){
            event.setCancelled(true);
        }
    }
}
