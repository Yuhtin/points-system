package com.yuhtin.commission.pointssystem.listener;

import com.yuhtin.commission.pointssystem.api.Account;
import com.yuhtin.commission.pointssystem.api.Hability;
import com.yuhtin.commission.pointssystem.configuration.HabilityValue;
import com.yuhtin.commission.pointssystem.storage.AccountStorage;
import lombok.AllArgsConstructor;
import lombok.val;
import lombok.var;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;

@AllArgsConstructor
public class IncreaseZombieListener implements Listener {

    private final AccountStorage accountStorage;

    @EventHandler
    public void onHitZombie(EntityDamageByEntityEvent event) {
        var damage = event.getDamage();
        if (event.getDamager() instanceof Player) {
            val player = (Player) event.getDamager();
            val account = accountStorage.findAccount(player);
            val itemInHand = player.getItemInHand();
            if (itemInHand != null && itemInHand.getType() == Material.DIAMOND_SWORD) {
                val swordHability = account.getHabilities().getOrDefault(Hability.SWORD_DAMAGE, 0);
                val levelMultiplier = 1 + (swordHability * HabilityValue.get(HabilityValue::swordDamagePerLevel) / 100);

                damage *= levelMultiplier;
            }

            if (event.getEntity() instanceof Zombie) {
                val zombieDamageHability = account.getHabilities().getOrDefault(Hability.ZOMBIE_DAMAGE, 0);
                val levelMultiplier = 1 + (zombieDamageHability * HabilityValue.get(HabilityValue::zombieDamagePerLevel) / 100);

                damage *= levelMultiplier;
            }
        }

        if (event.getEntity() instanceof Player) {
            val entity = (Player) event.getEntity();
            val entityAccount = accountStorage.findAccount(entity);
            val resistance = entityAccount.getHabilities().getOrDefault(Hability.RESISTANCE, 0);
            val levelMultiplier = 1 - (resistance * HabilityValue.get(HabilityValue::resistancePerLevel) / 100);

            damage *= levelMultiplier;
        }

        event.setDamage(damage);
    }

}
