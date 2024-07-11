package tcc.youajing.tcctools;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.inventory.ItemStack;



public class Listener implements org.bukkit.event.Listener {
    private final TccTools plugin;


    public Listener(TccTools plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        // 检查实体是否是苦力怕
        if (event.getEntity() instanceof Creeper) {
            //取消方块破坏但保留其他效果，并添加闪光粒子效果
            event.blockList().clear();
            World world = event.getLocation().getWorld();
            Location location = event.getLocation();
            world.spawnParticle(Particle.FLASH, location, 1);
            world.spawnParticle(Particle.FLASH, location, 1);
        } else if (event.getEntity() instanceof Fireball || event.getEntity() instanceof DragonFireball) {
            //检查实体是否是恶魂或者末影龙发射的火球
            Entity shooter = (Entity) ((Fireball) event.getEntity()).getShooter();
            if (shooter instanceof Ghast || shooter instanceof EnderDragon) {
                event.blockList().clear();
            }
        } else if (event.getEntity() instanceof EnderDragon) {
            //检查实体是不是末影龙
            event.blockList().clear();
        }
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

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event) {
        Player player = event.getPlayer();
        if (!player.hasPlayedBefore()) {
            player.getInventory().addItem(new ItemStack(Material.WHITE_BED, 1));
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                onlinePlayer.sendMessage( ChatColor.BOLD.toString() + ChatColor.AQUA + "大萌新" + ChatColor.WHITE + player.getName() + ChatColor.AQUA + "驾到，通通闪开！");
            }
        }
    }


}