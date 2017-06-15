package com.sharkbait.damageindicators;

import org.apache.commons.lang.StringEscapeUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class Core extends JavaPlugin implements Listener {

    @Override
    public void onEnable(){

        getServer().getPluginManager().registerEvents(this, this);

        getConfig().options().copyDefaults(true);
        saveConfig();

        Bukkit.getConsoleSender().sendMessage("§aCool§eDamage§cIndicators §ahas been enabled!");
    }

    @Override
    public void onDisable(){
        Bukkit.getConsoleSender().sendMessage("§aCool§eDamage§cIndicators §chas been disabled!");
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event){
        ArmorStand armorStand = (ArmorStand) event.getEntity().getWorld().spawnEntity(event.getEntity().getLocation(), EntityType.ARMOR_STAND);

        armorStand.setVisible(false);
        armorStand.setSmall(true);
        armorStand.setBasePlate(false);
        armorStand.setCustomNameVisible(true);
        String translated = StringEscapeUtils.unescapeJava(getConfig().getString("indicator-text").replace("&", "§").replace("{damage}", String.valueOf(event.getDamage())));
        armorStand.setCustomName(translated);


        new BukkitRunnable(){
            @Override
            public void run(){
                armorStand.remove();
            }
        }.runTaskLater(this, getConfig().getLong("remove-indicator-delay") * 20);
    }
}
