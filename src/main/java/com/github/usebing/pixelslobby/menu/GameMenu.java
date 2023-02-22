package com.github.usebing.pixelslobby.menu;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.mineacademy.fo.menu.Menu;
import org.mineacademy.fo.menu.button.Button;
import org.mineacademy.fo.menu.model.ItemCreator;
import org.mineacademy.fo.remain.CompMaterial;

public class GameMenu extends Menu {
    private final Button closeButton;

    private final Button comingSoonButton;
    public GameMenu(){
        this.setTitle("&8Game Menu");
        this.closeButton = new Button() {
            @Override
            public void onClickedInMenu(Player player, Menu menu, ClickType click) {
                player.closeInventory();
            }

            @Override
            public ItemStack getItem() {
                return ItemCreator.of(CompMaterial.BARRIER, "&cClose").make();
            }
        };

        this.comingSoonButton = new Button() {
            @Override
            public void onClickedInMenu(Player player, Menu menu, ClickType click) {
                player.closeInventory();
                player.sendMessage(ChatColor.RED + "This game is coming soon!");
            }

            @Override
            public ItemStack getItem() {
                return ItemCreator.of(CompMaterial.BEDROCK, "&cCOMING SOON").make();
            }
        };

        this.setSize(9 * 4);
    }

    @Override
    public ItemStack getItemAt(int slot) {
        if(slot == 31){
            return closeButton.getItem();
        }
        if(slot == 11 || slot == 13 || slot == 15){
            return comingSoonButton.getItem();
        }
        return NO_ITEM;
    }
}
