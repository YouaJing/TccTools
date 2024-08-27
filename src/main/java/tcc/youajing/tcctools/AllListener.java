package tcc.youajing.tcctools;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;
import java.util.Random;

public class AllListener implements org.bukkit.event.Listener {
    private final TccTools plugin;
    private final MiniMessage miniMessage = MiniMessage.miniMessage();
    public AllListener(TccTools plugin) {
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

    @EventHandler
    public void onPlayerDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player damager && event.getEntity() instanceof Player damagedPlayer) {
            if (damager.getInventory().getItemInMainHand().getType() == Material.AIR) {
                damagedPlayer.getWorld().spawnParticle(Particle.ANGRY_VILLAGER, damagedPlayer.getLocation().add(0, 2, 0), 1);
            }
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
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (!player.hasPlayedBefore()) {
            // 随机选择一种床颜色
            Material randomBedColor = getRandomBedColor();

            // 给玩家添加随机颜色的床
            player.getInventory().addItem(new ItemStack(randomBedColor, 1));
            Component welcomeMessage = miniMessage.deserialize("<bold><rainbow>大萌新『<underlined>" + player.getName() + "</underlined>』驾到，通通闪开!!!</rainbow></bold>");

            // 发送欢迎消息给所有在线玩家
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                onlinePlayer.sendMessage(welcomeMessage);
            }
        }
    }

    private Material getRandomBedColor() {
        Material[] bedColors = {
                Material.WHITE_BED,
                Material.ORANGE_BED,
                Material.MAGENTA_BED,
                Material.LIGHT_BLUE_BED,
                Material.YELLOW_BED,
                Material.LIME_BED,
                Material.PINK_BED,
                Material.GRAY_BED,
                Material.LIGHT_GRAY_BED,
                Material.CYAN_BED,
                Material.PURPLE_BED,
                Material.BLUE_BED,
                Material.BROWN_BED,
                Material.GREEN_BED,
                Material.RED_BED,
                Material.BLACK_BED
        };

        Random random = new Random();
        return bedColors[random.nextInt(bedColors.length)];
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (event.getEntity() instanceof Raider) {
            // 获取杀手（玩家）
            if (event.getEntity().getKiller() != null) {
                Raider raider = (Raider) event.getEntity();
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
