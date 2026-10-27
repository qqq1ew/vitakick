package me.server.vitakick;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.regex.Pattern;

public final class VitaColdStartKick extends JavaPlugin implements Listener {

    private static final Pattern TARGET_NAME_PATTERN = Pattern.compile("^\\.?player.*", Pattern.CASE_INSENSITIVE);

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        getLogger().info("VitaColdStartKick успешно запущен!");
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String name = player.getName();

        if (TARGET_NAME_PATTERN.matcher(name).matches()) {
            // Задержка 70 тиков (3.5 секунды): мир полностью прогрузится, клиент войдет в стабильный цикл
            getServer().getScheduler().runTaskLater(this, () -> {
                if (player.isOnline()) {
                    // Используем классический kickPlayer без сложного JSON/Adventure, чтобы не ронять клиент Виты
                    player.kickPlayer("§cХолодный старт Виты. Перезайдите!");
                }
            }, 70L);
        }
    }
}
