package tcc.youajing.tcctools;

import crypticlib.BukkitPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.Listener;


public class TccTools extends BukkitPlugin {

    @Override
    public void enable() {
        //TODO
        // 初始化团队管理器和监听器
        saveDefaultConfig();
        Listener EntityExplodeListener = new EntityExplodeListener(this);
        Listener EntityDamageByEntityListener = new EntityDamageByEntityListener(this);
        EntityInteractListener EntityInteractListener = new EntityInteractListener(this);
        PlayerJoinListener PlayerJoinListener = new PlayerJoinListener(this);
        EntityDeathListener EntityDeathListener = new EntityDeathListener(this);
        PlayerChatListener PlayerChatListener = new PlayerChatListener(this);
        Listener WorldEventListener = new WorldEventListener(this);
        // 注册命令和监听器
        getServer().getPluginManager().registerEvents(EntityExplodeListener, this);
        getServer().getPluginManager().registerEvents(EntityDamageByEntityListener, this);
        getServer().getPluginManager().registerEvents(EntityInteractListener, this);
        getServer().getPluginManager().registerEvents(PlayerJoinListener, this);
        getServer().getPluginManager().registerEvents(EntityDeathListener, this);
        getServer().getPluginManager().registerEvents(PlayerChatListener, this);
        getServer().getPluginManager().registerEvents(WorldEventListener, this);

        // team,启动！
        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GREEN + "[TccTools]" + ChatColor.AQUA + "######################");
        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GREEN + "[TccTools]" + ChatColor.AQUA + "#                    #");
        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GREEN + "[TccTools]" + ChatColor.AQUA + "#TccTools已启动#");
        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GREEN + "[TccTools]" + ChatColor.AQUA + "#                    #");
        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GREEN + "[TccTools]" + ChatColor.AQUA + "######################");
    }

    @Override
    public void disable() {
        //TODO
        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GREEN + "[TccTools]" + ChatColor.RED + "######################");
        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GREEN + "[TccTools]" + ChatColor.RED + "#                    #");
        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GREEN + "[TccTools]" + ChatColor.RED + "#TccTools已关闭#");
        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GREEN + "[TccTools]" + ChatColor.RED + "#                    #");
        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GREEN + "[TccTools]" + ChatColor.RED + "######################");
    }

}