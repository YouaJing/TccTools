package tcc.youajing.tcctools;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;

public class WorldEventListener implements org.bukkit.event.Listener{
    private final TccTools plugin;
    public WorldEventListener(TccTools plugin) {
        this.plugin = plugin;
        ProtocolLibrary.getProtocolManager().addPacketListener(
                new PacketAdapter(plugin, ListenerPriority.HIGHEST, PacketType.Play.Server.WORLD_EVENT) {
                    @Override
                    public void onPacketSending(PacketEvent event) {
                        PacketContainer packetContainer = event.getPacket();
                        if (packetContainer.getIntegers().read(0) == 1023) {
                            packetContainer.getBooleans().write(0, false);
                        }
                    }
                });
    }
}
