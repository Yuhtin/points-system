package com.yuhtin.commission.pointssystem.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Material;

@Getter
@AllArgsConstructor
public enum Hability {

    MEDKIT_RELOAD("Recarga do medkit", Material.COOKED_BEEF),
    ZOMBIE_DAMAGE("Aumentar dano em zumbis", Material.ROTTEN_FLESH),
    SWORD_DAMAGE("Aumentar dano com espadas", Material.DIAMOND_SWORD),
    RESISTANCE("Resistência em pvp", Material.DIAMOND_CHESTPLATE);

    private final String fancyName;
    private final Material material;

}
