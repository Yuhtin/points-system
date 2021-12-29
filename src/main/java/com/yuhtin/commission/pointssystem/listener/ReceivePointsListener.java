package com.yuhtin.commission.pointssystem.listener;

import com.yuhtin.commission.pointssystem.configuration.FeatureValue;
import com.yuhtin.commission.pointssystem.configuration.MessagesValue;
import com.yuhtin.commission.pointssystem.storage.AccountStorage;
import lombok.AllArgsConstructor;
import lombok.val;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLevelChangeEvent;
import com.yuhtin.commission.pointssystem.util.ActionBarUtils;
import com.yuhtin.commission.pointssystem.util.NumberUtil;

@AllArgsConstructor
public class ReceivePointsListener implements Listener {

    private final AccountStorage accountStorage;

    @EventHandler
    public void onUpgradeLevel(PlayerLevelChangeEvent event) {
        val levelsUpgraded = event.getNewLevel() - event.getOldLevel();
        if (levelsUpgraded <= 0 ) return;

        val player = event.getPlayer();
        val account = accountStorage.findAccount(player);

        val points = FeatureValue.get(FeatureValue::pointsPerLevel) * levelsUpgraded;
        account.setPoints(account.getPoints() + points);

        val message = MessagesValue.get(MessagesValue::receivedPoints)
                .replace("%levels%", NumberUtil.format(levelsUpgraded))
                .replace("%points%", NumberUtil.format(points));

        ActionBarUtils.sendActionBar(player, message);
    }


}
