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
            Player killer = event.getEntity().getKiller();

            if (killer != null) {
                Raider raider = (Raider) event.getEntity();
                if (raider.isPatrolLeader()) {
                    int randomIntensity = new Random().nextInt(5) + 1;
                    killer.addPotionEffect(new PotionEffect(PotionEffectType.BAD_OMEN, 20 * 60 * 3, randomIntensity));
                }
            }
        } else if (event.getEntity() instanceof EnderDragon) {
            event.getEntity().getWorld().getPlayers().stream()
                    .filter(player -> player.getWorld().equals(event.getEntity().getWorld()) &&
                            player.getLocation().distance(event.getEntity().getLocation()) <= plugin.getConfig().getInt("EnderDragonSoundRange"))
                    .forEach(player -> player.playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_DEATH, 1F, 1F));
        } else if (event.getEntity() instanceof ArmorStand armorStand) {
            if (armorStand.getLastDamageCause() instanceof EntityDamageByEntityEvent damageEvent) {
                Entity damager = damageEvent.getDamager();

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

