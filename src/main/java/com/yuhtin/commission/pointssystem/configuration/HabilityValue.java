package com.yuhtin.commission.pointssystem.configuration;

import com.henryfabio.minecraft.configinjector.common.annotations.ConfigField;
import com.henryfabio.minecraft.configinjector.common.annotations.ConfigFile;
import com.henryfabio.minecraft.configinjector.common.annotations.ConfigSection;
import com.henryfabio.minecraft.configinjector.common.annotations.TranslateColors;
import com.henryfabio.minecraft.configinjector.common.injector.ConfigurationInjectable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.bukkit.configuration.ConfigurationSection;

import java.util.function.Function;

@Getter
@TranslateColors
@Accessors(fluent = true)
@ConfigSection("config.habilities")
@ConfigFile("config.yml")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class HabilityValue implements ConfigurationInjectable {

    @Getter private static final HabilityValue instance = new HabilityValue();

    @ConfigField("zombieDamagePerLevel") private int zombieDamagePerLevel;
    @ConfigField("swordDamagePerLevel") private int swordDamagePerLevel;
    @ConfigField("resistancePerLevel") private int resistancePerLevel;
    @ConfigField("medkitReloadPerLevel") private int medkitReloadPerLevel;
    @ConfigField("maxLevels") private ConfigurationSection maxLevelsSection;

    public static <T> T get(Function<HabilityValue, T> function) {
        return function.apply(instance);
    }

}
