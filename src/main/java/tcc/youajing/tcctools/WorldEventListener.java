package tcc.youajing.tcctools;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;

/**
 * 世界事件监听器，用于处理Minecraft服务器中的世界事件
 */
public class WorldEventListener implements org.bukkit.event.Listener {
    private final TccTools plugin;

    /**
     * 构造函数，初始化世界事件监听器
     *
     * @param plugin TccTools插件的实例
     */
    public WorldEventListener(TccTools plugin) {
        this.plugin = plugin;
        // 添加一个PacketAdapter来监听指定的包类型
        ProtocolLibrary.getProtocolManager().addPacketListener(
                new PacketAdapter(plugin, ListenerPriority.HIGHEST, PacketType.Play.Server.WORLD_EVENT) {
                    /**
                     * 在世界事件包发送前触发
                     *
                     * @param event 事件对象，包含包的详细信息
                     */
                    @Override
                    public void onPacketSending(PacketEvent event) {
                        PacketContainer packetContainer = event.getPacket();
                        int eventId = packetContainer.getIntegers().read(0);
                        // 当事件ID为1023或1028时，将包中的布尔值改为false
                        if (eventId == 1023 || eventId == 1028) {
                            packetContainer.getBooleans().write(0, false);
                        }
                    }
                });
    }
}

