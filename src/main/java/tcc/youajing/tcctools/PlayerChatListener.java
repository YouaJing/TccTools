package tcc.youajing.tcctools;

import io.papermc.paper.event.player.AsyncChatEvent;
import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class PlayerChatListener implements org.bukkit.event.Listener{
    private TccTools plugin;

    public PlayerChatListener(TccTools plugin)
    {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();

        // 检查玩家输入的消息是否包含 [i] 或 [item]
        if (message.contains("[i]") || message.contains("[item]")) {
            Component displayItem = getComponent(player);

            // 初始化 MiniMessage 和 LegacyComponentSerializer
            MiniMessage miniMessage = MiniMessage.miniMessage();
            LegacyComponentSerializer legacySerializer = LegacyComponentSerializer.builder().hexColors().hexCharacter('#').character('&').build();

            // 将物品信息插入到消息中
            Component chatMessage = legacySerializer.deserialize(message);
            chatMessage = chatMessage.replaceText(TextReplacementConfig.builder().matchLiteral("[i]").replacement(displayItem).build());
            chatMessage = chatMessage.replaceText(TextReplacementConfig.builder().matchLiteral("[item]").replacement(displayItem).build());

            // 使用 PlaceholderAPI 处理占位符
            String formattedMessage = String.format("<b>%%teamplugin_color%%%%teamplugin_name4chat%%<reset><b>%%vault_prefix%%%s<reset><b>%%vault_suffix%% <#a1c4fd>>><reset> ", player.getName());
            formattedMessage = PlaceholderAPI.setPlaceholders(player, formattedMessage);
            Component finalMessage = miniMessage.deserialize(formattedMessage).append(chatMessage);

            // 取消原始消息并广播新消息
            event.setCancelled(true);
            Bukkit.broadcast(finalMessage);
        }
    }

    private static @NotNull Component getComponent(Player player) {
        ItemStack handItem = player.getInventory().getItemInMainHand();
        Component displayItem;

        if (handItem != null && handItem.getType() != Material.AIR) {
            displayItem = handItem.displayName()
                    .decorate(TextDecoration.BOLD);
            displayItem = displayItem.hoverEvent(handItem.asHoverEvent());
        } else {
            displayItem = Component.text(player.getName() + "的手")
                    .decorate(TextDecoration.BOLD)
                    .decorate(TextDecoration.UNDERLINED).hoverEvent(Component.text("手上什么也没有"));
        }
        return displayItem;
    }
}
