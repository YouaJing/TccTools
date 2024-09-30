package tcc.youajing.tcctools;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

/**
 * 实现了一个监听器，用于处理玩家之间的伤害事件
 * 主要功能是：当玩家未使用武器对其他玩家造成伤害时，生成一个愤怒村民的粒子效果
 */
public class EntityDamageByEntityListener implements org.bukkit.event.Listener {
    private TccTools plugin;

    /**
     * 构造函数，初始化监听器实例
     *
     * @param plugin TccTools插件的实例
     */
    public EntityDamageByEntityListener (TccTools plugin)
    {
        this.plugin = plugin;
    }

    /**
     * 处理玩家对玩家伤害事件
     * 当事件的伤害者和受害者都是玩家时，检查伤害者是否手持空气（即未使用武器）
     * 如果是空手，将在受害玩家上方生成一个愤怒村民的粒子效果
     *
     * @param event 实体伤害事件
     */
    @EventHandler
    public void onPlayerDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player damager && event.getEntity() instanceof Player damagedPlayer) {
            if (damager.getInventory().getItemInMainHand().getType() == Material.AIR) {
                damagedPlayer.getWorld().spawnParticle(Particle.ANGRY_VILLAGER, damagedPlayer.getLocation().add(0, 2, 0), 1);
            }
        }
    }
}
