package tcc.youajing.tcctools;

import crypticlib.BukkitPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class TccTools extends BukkitPlugin {

    @Override
    public void enable() {
        //TODO
        // 初始化团队管理器和监听器
        Listener FCListener = new Listener(this);
        // 注册命令和监听器
        getServer().getPluginManager().registerEvents(FCListener, this);
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