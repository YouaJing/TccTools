package tcc.youajing.tcctools;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;
import java.util.Random;

public class EntityDeathListener implements org.bukkit.event.Listener {
    private TccTools plugin;

    public EntityDeathListener(TccTools plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        // 当实体死亡时，根据实体类型执行不同的操作
        if (event.getEntity() instanceof Raider) {
            // 获取杀手（玩家）
            if (event.getEntity().getKiller() != null) {
                Raider raider = (Raider) event.getEntity();
                // 如果是巡逻队长，则给杀手玩家添加不祥征兆效果
                if (raider.isPatrolLeader()) {
                    Player player = event.getEntity().getKiller();
                    // 添加不祥征兆效果，随机赋予不祥征兆的强度（1到5），持续3分钟（60秒 * 3）
                    int randomIntensity = new Random().nextInt(5) + 1;
                    if (player != null) {
                        player.addPotionEffect(new PotionEffect(PotionEffectType.BAD_OMEN, 20 * 60 * 3, randomIntensity));
                    }
                }
            }
        } else if (event.getEntity() instanceof EnderDragon) {
            // 当末影龙死亡时，播放死亡声音给附近的玩家
            for (Player player : plugin.getServer().getOnlinePlayers()) {
                if (event.getEntity().getLocation().getWorld() == player.getWorld()) {
                    if (event.getEntity().getLocation().distance(player.getLocation()) <= plugin.getConfig().getInt("EnderDragonSoundRange")) {
                        player.playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_DEATH, 1F, 1F);
                    }
                }
            }
        } else if (event.getEntity() instanceof ArmorStand armorStand) {
            // 当盔甲架受到伤害时，检查伤害来源
            if (armorStand.getLastDamageCause() instanceof EntityDamageByEntityEvent damageEvent) {
                Entity damager = damageEvent.getDamager();
                // 如果伤害来源是玩家，并且玩家手持剑，则取消事件并掉落物品
                if (damager instanceof Player player) {
                    ItemStack itemInHand = player.getInventory().getItemInMainHand();
                    if (itemInHand.getType().toString().endsWith("_SWORD")) {
                        List<ItemStack> drops = event.getDrops();
                        event.setCancelled(true);
                        Location location = event.getEntity().getLocation();
                        for (ItemStack drop : drops) {
                            if (!drop.getType().equals(Material.ARMOR_STAND)) {
                                location.getWorld().dropItemNaturally(location, drop);
                            }
                        }
                    }
                }
            }
        }
    }
}

