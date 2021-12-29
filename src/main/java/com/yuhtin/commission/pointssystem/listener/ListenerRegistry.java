package com.yuhtin.commission.pointssystem.listener;

import com.yuhtin.commission.pointssystem.PointsSystem;
import lombok.Data;
import lombok.val;
import org.bukkit.Bukkit;

import java.util.Arrays;

@Data(staticConstructor = "of")
public final class ListenerRegistry {

    private final PointsSystem plugin;

    public void register() {
        val logger = plugin.getLogger();
        try {
            val pluginManager = Bukkit.getPluginManager();
            val accountStorage = plugin.getAccountStorage();

            val listeners = Arrays.asList(
                    new ReceivePointsListener(accountStorage),
                    new ReceiveXPListener()
            );

            listeners.forEach(listener -> pluginManager.registerEvents(listener, plugin));
            logger.info("Foram registrados " + listeners.size() + " listeners com sucesso!");
        } catch (Throwable t) {
            t.printStackTrace();
            logger.severe("Não foi possível registrar os listeners.");
        }
    }
}
