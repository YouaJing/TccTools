package tcc.youajing.tcctools;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class EntityInteractListener implements org.bukkit.event.Listener {
    private TccTools plugin;

    public EntityInteractListener(TccTools plugin)
    {
        this.plugin = plugin;
    }
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        // 检查玩家的动作
        if (event.getAction() == Action.PHYSICAL) {
            Block block = event.getClickedBlock();
            // 检查交互的方块是否是耕地
            if (block != null && block.getType() == Material.FARMLAND) {
                // 取消事件，防止耕地被踩踏
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onEntityInteract(EntityInteractEvent event) {
        //获取实体交互的方块
        Block block = event.getBlock();
        //检查交互的方块是否是耕地
        if (block.getType() == Material.FARMLAND) {
            // 取消事件，防止耕地被踩踏
            event.setCancelled(true);
        }
    }
}
