package com.github.usebing.pixelslobby.commands;

import com.github.usebing.pixelslobby.menu.GameMenu;
import org.mineacademy.fo.annotation.AutoRegister;
import org.mineacademy.fo.command.SimpleCommand;

@AutoRegister
public final class MenuCommand extends SimpleCommand {
    public MenuCommand() {
        super("menu");
    }

    @Override
    protected void onCommand() {
        if(isPlayer()){
            new GameMenu().displayTo(getPlayer());
        }
    }
}
