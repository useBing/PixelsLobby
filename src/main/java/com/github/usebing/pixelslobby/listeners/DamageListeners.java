package com.github.usebing.pixelslobby.listeners;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.mineacademy.fo.annotation.AutoRegister;

@AutoRegister
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DamageListeners implements Listener {
    @Getter
    private static final DamageListeners instance = new DamageListeners();
    @EventHandler
    public void onDamage(EntityDamageEvent event){
        if(event.getEntity().getWorld().getName().equalsIgnoreCase("lobby")){
            event.setCancelled(true);
        }
    }
}
