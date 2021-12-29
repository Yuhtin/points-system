package com.yuhtin.commission.pointssystem.listener;

import com.yuhtin.commission.pointssystem.configuration.FeatureValue;
import lombok.val;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class ReceiveXPListener implements Listener {

    @EventHandler
    public void onKillZombie(EntityDeathEvent event) {
        if (event.getEntity().getType() != EntityType.ZOMBIE) return;

        val player = event.getEntity().getKiller();
        if (player == null) return;

        event.setDroppedExp(0);
        player.giveExp(FeatureValue.get(FeatureValue::xpPerZombie));
    }

}
