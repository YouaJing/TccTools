package tcc.youajing.tcctools;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class PlayerJoinListener implements org.bukkit.event.Listener{
    private TccTools plugin;
    private final MiniMessage miniMessage = MiniMessage.miniMessage();

    public PlayerJoinListener(TccTools plugin)
    {
        this.plugin = plugin;
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
}
