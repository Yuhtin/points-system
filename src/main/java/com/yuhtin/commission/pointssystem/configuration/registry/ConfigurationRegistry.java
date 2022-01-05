package com.yuhtin.commission.pointssystem.configuration.registry;

import com.henryfabio.minecraft.configinjector.bukkit.injector.BukkitConfigurationInjector;
import com.yuhtin.commission.pointssystem.configuration.FeatureValue;
import com.yuhtin.commission.pointssystem.configuration.HabilityValue;
import com.yuhtin.commission.pointssystem.configuration.MessagesValue;
import lombok.Data;
import org.bukkit.plugin.Plugin;

@Data(staticConstructor = "of")
public final class ConfigurationRegistry {

    private final Plugin plugin;

    public void register() {
        BukkitConfigurationInjector configurationInjector = new BukkitConfigurationInjector(plugin);
        configurationInjector.injectConfiguration(FeatureValue.instance(), MessagesValue.instance(), HabilityValue.instance());

        getPlugin().getLogger().info("Configurações registradas e injetadas com sucesso.");
    }

}
