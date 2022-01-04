package com.yuhtin.commission.pointssystem.commands;

import com.yuhtin.commission.pointssystem.view.UpgradeHabilityView;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HabilityCommand implements CommandExecutor {

    private final UpgradeHabilityView upgradeHabilityView = new UpgradeHabilityView().init();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            upgradeHabilityView.openInventory(((Player) sender));
            return true;
        }

        return false;
    }
}
