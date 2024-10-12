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
        if (event.getEntity() instanceof Raider) {
            if (event.getEntity().getKiller() != null) {
                Raider raider = (Raider) event.getEntity();
                if (raider.isPatrolLeader()) {
                    Player player = event.getEntity().getKiller();
                    int randomIntensity = new Random().nextInt(5) + 1;
                    if (player != null) {
                        player.addPotionEffect(new PotionEffect(PotionEffectType.BAD_OMEN, 20 * 60 * 3, randomIntensity));
                    }
                }
            }
        } else if (event.getEntity() instanceof EnderDragon) {
            for (Player player : plugin.getServer().getOnlinePlayers()) {
                if (event.getEntity().getLocation().getWorld() == player.getWorld()) {
                    if (event.getEntity().getLocation().distance(player.getLocation()) <= plugin.getConfig().getInt("EnderDragonSoundRange")) {
                        player.playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_DEATH, 1F, 1F);
                    }
                }
            }
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
                            if (drop.getType().name().endsWith("_HELMET")) {
                                armorStand.getEquipment().setHelmet(drop);
                            } else if (drop.getType().name().endsWith("_CHESTPLATE")) {
                                armorStand.getEquipment().setChestplate(drop);
                            } else if (drop.getType().name().endsWith("_LEGGINGS")) {
                                armorStand.getEquipment().setLeggings(drop);
                            } else if (drop.getType().name().endsWith("_BOOTS")) {
                                armorStand.getEquipment().setBoots(drop);
                            } else if (drop.getType().name().endsWith("_SWORD") || drop.getType().name().endsWith("_AXE") || drop.getType().name().endsWith("BOW") || drop.getType().name().endsWith("MACE") || drop.getType().name().endsWith("TRIDENT") || drop.getType().name().endsWith("_HOE ") || drop.getType().name().endsWith("_PICKAXE") || drop.getType().name().endsWith("_SHOVEL")) {
                                if (armorStand.getEquipment().getItemInMainHand().getType() == Material.AIR) {
                                    armorStand.getEquipment().setItemInMainHand(drop);
                                } else if (armorStand.getEquipment().getItemInOffHand().getType() == Material.AIR) {
                                    armorStand.getEquipment().setItemInOffHand(drop);
                                } else {
                                    location.getWorld().dropItemNaturally(location, drop);
                                }
                            } else {
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

}

