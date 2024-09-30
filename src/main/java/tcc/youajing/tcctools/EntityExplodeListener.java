package tcc.youajing.tcctools;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityExplodeEvent;

public class EntityExplodeListener implements org.bukkit.event.Listener {
    private TccTools plugin;

    public EntityExplodeListener(TccTools plugin)
    {
        this.plugin = plugin;
    }
    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof Creeper) {
            // 取消方块破坏但保留其他效果，并添加闪光粒子效果
            event.blockList().clear();
            World world = event.getLocation().getWorld();
            Location location = event.getLocation();
            world.spawnParticle(Particle.FLASH, location, 1);
        } else if (entity instanceof Fireball) {
            // 检查实体是否是恶魂或者末影龙发射的火球
            Entity shooter = (Entity) ((Fireball) entity).getShooter();
            if (shooter instanceof Ghast || shooter instanceof EnderDragon || shooter instanceof Wither) {
                event.blockList().clear();
            }
        } else if (entity instanceof EnderDragon) {
            // 检查实体是不是末影龙
            event.blockList().clear();
        } else if (entity instanceof Wither) {
            for (Player player : plugin.getServer().getOnlinePlayers()) {
                if (event.getLocation().getWorld() ==player.getWorld()) {
                    if (event.getEntity().getLocation().distance(player.getLocation())
                            <= plugin.getConfig().getInt("WitherSoundRange")) {
                        player.playSound(player.getLocation(), Sound.ENTITY_WITHER_SPAWN, 1F, 1F);
                    }
                }
            }
        }
    }
}
