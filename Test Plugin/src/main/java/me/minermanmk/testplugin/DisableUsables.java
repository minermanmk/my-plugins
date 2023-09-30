package me.minermanmk.testplugin;

import net.minecraft.world.level.block.Block;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class DisableUsables implements Listener {

    public void onFire(PlayerInteractEvent event) {
        Action eventAction;
        Player target;
        Material targetBlockType;
        ItemStack targetItemStack;

        if(!event.hasItem()){
            return;
        }
        eventAction = event.getAction();
        target = event.getPlayer();
        GameMode targetGamemode = target.getGameMode();

        targetBlockType = event.getClickedBlock().getType();

        if(targetGamemode == GameMode.ADVENTURE && (eventAction == Action.RIGHT_CLICK_AIR || eventAction == Action.RIGHT_CLICK_BLOCK) && targetBlockType != Material.OAK_DOOR){
            event.setCancelled(true);
        }
    }
}
