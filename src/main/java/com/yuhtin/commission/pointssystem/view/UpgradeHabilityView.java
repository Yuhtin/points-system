package com.yuhtin.commission.pointssystem.view;

import com.google.common.base.Strings;
import com.henryfabio.minecraft.inventoryapi.editor.InventoryEditor;
import com.henryfabio.minecraft.inventoryapi.inventory.impl.simple.SimpleInventory;
import com.henryfabio.minecraft.inventoryapi.item.InventoryItem;
import com.henryfabio.minecraft.inventoryapi.viewer.Viewer;
import com.yuhtin.commission.pointssystem.PointsSystem;
import com.yuhtin.commission.pointssystem.api.Hability;
import com.yuhtin.commission.pointssystem.configuration.HabilityValue;
import com.yuhtin.commission.pointssystem.storage.AccountStorage;
import com.yuhtin.commission.pointssystem.util.ColorUtil;
import com.yuhtin.commission.pointssystem.util.ItemBuilder;
import com.yuhtin.commission.pointssystem.util.NumberUtil;
import lombok.val;
import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;

public class UpgradeHabilityView extends SimpleInventory {

    private final AccountStorage accountStorage;

    public UpgradeHabilityView() {
        super(
                "points.main",
                "Evolua suas habilidades",
                5 * 9
        );

        accountStorage = PointsSystem.getInstance().getAccountStorage();
    }

    @Override
    protected void configureInventory(Viewer viewer, InventoryEditor editor) {
        val player = viewer.getPlayer();
        val account = accountStorage.findAccount(player);

        editor.setItem(4, InventoryItem.of(new ItemBuilder(player.getName())
                .name("&aSua árvore de habilidades")
                .setLore("&fPontos: &7" + NumberUtil.format(account.getPoints()))
                .wrap()
        ));

        val maxLevelSection = HabilityValue.get(HabilityValue::maxLevelsSection);

        int[] slots = new int[]{20, 21, 23, 24};
        int index = 0;
        for (Hability hability : Hability.values()) {
            val maxLevel = maxLevelSection.getInt(hability.name());
            val currentLevel = account.getHabilities().getOrDefault(hability, 0);
            val progressBar = getProgressBar(currentLevel, maxLevel, 10, '|', ChatColor.GREEN, ChatColor.RED);

            editor.setItem(slots[index], InventoryItem.of(new ItemBuilder(hability.getMaterial())
                    .name("&e" + hability.getFancyName())
                    .setLore(
                            "&fNível: &7" + currentLevel,
                            "&fNível máximo: &7" + maxLevel,
                            "",
                            "&8[" + progressBar + "&8] &8(&a" + currentLevel + "&8/&c" + maxLevel + "&8)"
                    )
                    .wrap()
            ).defaultCallback(clickEvent -> {
                val clickEventPlayer = clickEvent.getPlayer();
                val clickEventAccount = accountStorage.findAccount(clickEventPlayer);

                if (clickEventAccount.getPoints() < 1) {
                    clickEventPlayer.sendMessage(ColorUtil.colored("&cVocê precisa ter pontos para evoluir uma habiliade."));
                    return;
                }

                if (currentLevel >= maxLevel) {
                    clickEventPlayer.sendMessage(ColorUtil.colored("&cVocê já atingiu o nível máximo desta habilidade."));
                    return;
                }

                account.getHabilities().remove(hability);
                account.getHabilities().put(hability, currentLevel + 1);
                account.setPoints(account.getPoints() - 1);

                clickEventPlayer.sendMessage(ColorUtil.colored("&aVocê evoluiu esta habilidade com sucesso."));
                clickEvent.updateInventory();
            }));

            index++;
        }
    }

    @Override
    protected void update(@NotNull Viewer viewer, @NotNull InventoryEditor editor) {
        configureInventory(viewer, editor);
    }

    public String getProgressBar(int current, int max, int totalBars, char symbol, ChatColor completedColor,
                                 ChatColor notCompletedColor) {
        float percent = (float) current / max;
        int progressBars = (int) (totalBars * percent);

        return Strings.repeat("" + completedColor + symbol, progressBars)
                + Strings.repeat("" + notCompletedColor + symbol, totalBars - progressBars);
    }
}
