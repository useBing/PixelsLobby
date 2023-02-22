package com.github.usebing.pixelslobby.listeners;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.mineacademy.fo.annotation.AutoRegister;

@AutoRegister
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class WeatherListeners implements Listener {
    @Getter
    private static final WeatherListeners instance = new WeatherListeners();

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent event){
        if(event.getWorld().getName().equalsIgnoreCase("lobby")){
            event.setCancelled(true);
        }
    }
}
