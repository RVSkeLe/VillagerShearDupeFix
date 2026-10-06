package io.github.rvskele.villagersheardupefix;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.AbstractVillager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public final class VillagerShearDupeFix extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onInteract(PlayerInteractEntityEvent event) {
        if (!(event.getRightClicked() instanceof AbstractVillager villager)) {
            return;
        }

        ItemStack heldItem = event.getPlayer()
                .getInventory()
                .getItem(event.getHand());
        if (heldItem.getType() != Material.SHEARS) return;

        ItemStack villagerHeldItem = villager
                .getEquipment()
                .getItemInMainHand();
        if (villagerHeldItem.getType() == Material.AIR) return;

        event.setCancelled(true);

        Player player = event.getPlayer();
        Location location = villager.getLocation();
        getLogger().info("Blocked villager shear dupe attempt by "
                        + player.getName()
                        + " (" + player.getUniqueId() + ")"
                        + " at "
                        + location.getWorld().getName()
                        + " [" + location.getBlockX()
                        + ", " + location.getBlockY()
                        + ", " + location.getBlockZ() + "]"
        );
    }
}
