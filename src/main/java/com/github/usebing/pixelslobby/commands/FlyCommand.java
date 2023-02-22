package com.github.usebing.pixelslobby.commands;

import org.mineacademy.fo.annotation.AutoRegister;
import org.mineacademy.fo.command.SimpleCommand;

import java.util.concurrent.TimeUnit;

@AutoRegister
public final class FlyCommand extends SimpleCommand {
    public FlyCommand() {
        super("fly|flight");
        setDescription("Toggle your flight.");
        setPermission("pixelslobby.fly");
        setCooldown(3, TimeUnit.SECONDS);
        setTellPrefix("&8[&cFly&8]&7");
    }

    @Override
    protected void onCommand() {
        if(isPlayer()){
            if(getPlayer().getWorld().getName().equalsIgnoreCase("lobby") || getPlayer().hasPermission("pixelslobby.fly.bypass")){
                getPlayer().setAllowFlight(!getPlayer().getAllowFlight());
                if(getPlayer().getAllowFlight()){
                    tell("Enabled flight");
                } else {
                    tell("Disabled flight");
                }
            } else {
                tellError("This command can only be run in the lobby!");
            }
        } else {
            tellError("This command can only be run by a player!");
        }
    }
}
