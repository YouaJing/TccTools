package tcc.youajing.tcctools;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.lang.reflect.Method;
import java.util.Random;

import static org.bukkit.Bukkit.getServer;


public class Listener implements org.bukkit.event.Listener {
    private final TccTools plugin;


    public Listener(TccTools plugin) {
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
                Raider raider = (Raider) event.getEntity();
                if (raider.isPatrolLeader()) {
                    Player player = event.getEntity().getKiller();
                    // 添加不祥征兆效果，随机赋予不祥征兆的强度（1到5），持续3分钟（60秒 * 3）
                    int randomIntensity = new Random().nextInt(5) + 1;
                    player.addPotionEffect(new PotionEffect(PotionEffectType.BAD_OMEN, 20 * 60 * 3, randomIntensity));
                }
            }

        }
    }

//    @EventHandler
//    public void onWitherSpawn(CreatureSpawnEvent event) {
//        if (event.getEntityType() == EntityType.WITHER) {
//            try {
//                // 反射获取 CraftWorld 类
//                Class<?> craftWorldClass = Class.forName("org.bukkit.craftbukkit." + getServer().getVersion() + ".CraftWorld");
//                Object craftWorld = craftWorldClass.cast(event.getLocation().getWorld());
//
//                // 获取 handle 方法
//                Method getHandleMethod = craftWorldClass.getDeclaredMethod("getHandle");
//                Object worldServer = getHandleMethod.invoke(craftWorld);
//
//                // 获取 World 类
//                Class<?> worldClass = worldServer.getClass().getSuperclass();
//
//                // 获取 playSound 方法
//                Method playSoundMethod = worldClass.getDeclaredMethod("a", double.class, double.class, double.class, String.class, float.class, float.class, boolean.class);
//                playSoundMethod.setAccessible(true);
//
//                // 取消声音播放
//                playSoundMethod.invoke(worldServer, event.getLocation().getX(), event.getLocation().getY(), event.getLocation().getZ(), "", 0.0f, 0.0f, false);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
}
