package tcc.youajing.tcctools;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;



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
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (!player.hasPlayedBefore()) {
            // 随机选择一种床颜色
            Material randomBedColor = getRandomBedColor();

            // 给玩家添加随机颜色的床
            player.getInventory().addItem(new ItemStack(randomBedColor, 1));

            // 发送欢迎消息给所有在线玩家
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                onlinePlayer.sendMessage(ChatColor.BOLD.toString() + ChatColor.AQUA + "大萌新" + ChatColor.WHITE + player.getName() + ChatColor.AQUA + "驾到，通通闪开！");
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
                    Player player = event.getEntity().getKiller();
                    // 添加不祥征兆效果，随机赋予不祥征兆的强度（1到5），持续3分钟（60秒 * 3）
                    int randomIntensity = new Random().nextInt(5) + 1;
                    player.addPotionEffect(new PotionEffect(PotionEffectType.BAD_OMEN, 20 * 60 * 3 , randomIntensity));
                }
            }
        }

//        if (event.getEntityType() == EntityType.PILLAGER) {
//            // 获取掠夺者实体
//            Pillager pillager = (Pillager) event.getEntity();
//            // 检查这掠夺者是不是队长
//            if (pillager.isPatrolLeader()) {
//                // 获取杀手（玩家）
//                if (event.getEntity().getKiller() != null) {
//                    Player player = event.getEntity().getKiller();
//                    // 添加不祥征兆效果，随机赋予不祥征兆的强度（1到5），持续3分钟（60秒 * 3）
//                    int randomIntensity = new Random().nextInt(5) + 1;
//                    player.addPotionEffect(new PotionEffect(PotionEffectType.BAD_OMEN, 20 * 60 * 3 , randomIntensity));
//                }
//            }
//        }
    }
