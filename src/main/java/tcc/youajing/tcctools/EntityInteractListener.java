package tcc.youajing.tcctools;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Objects;

public class EntityInteractListener implements org.bukkit.event.Listener {
    private TccTools plugin;

    public EntityInteractListener(TccTools plugin)
    {
        this.plugin = plugin;
    }
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        // 耕地防玩家踩踏
        if (Objects.requireNonNull(event.getClickedBlock()).getType() == Material.FARMLAND && event.getAction() == Action.PHYSICAL)
        {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityInteract(EntityInteractEvent event) {
        if (event.getBlock().getType() == Material.FARMLAND) {
            event.setCancelled(true);
        }
    }
}
