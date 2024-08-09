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
        Listener AllListener = new AllListener(this);
        Listener WorldEventListener = new WorldEventListener(this);
        // 注册命令和监听器
        getServer().getPluginManager().registerEvents(AllListener, this);
        getServer().getPluginManager().registerEvents(WorldEventListener, this);

        // team,启动！
        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GREEN + "[FCProtect]" + ChatColor.AQUA + "######################");
        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GREEN + "[FCProtect]" + ChatColor.AQUA + "#                    #");
        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GREEN + "[FCProtect]" + ChatColor.AQUA + "#TCC-ZFC保护已启动#");
        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GREEN + "[FCProtect]" + ChatColor.AQUA + "#                    #");
        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GREEN + "[FCProtect]" + ChatColor.AQUA + "######################");
    }

    @Override
    public void disable() {
        //TODO
        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GREEN + "[FCProtect]" + ChatColor.RED + "######################");
        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GREEN + "[FCProtect]" + ChatColor.RED + "#                    #");
        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GREEN + "[FCProtect]" + ChatColor.RED + "#TCC-ZFC保护已关闭#");
        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GREEN + "[FCProtect]" + ChatColor.RED + "#                    #");
        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GREEN + "[FCProtect]" + ChatColor.RED + "######################");
    }

}