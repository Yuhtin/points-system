package com.yuhtin.commission.pointssystem;

import com.google.common.base.Stopwatch;
import com.henryfabio.minecraft.inventoryapi.manager.InventoryManager;
import com.henryfabio.sqlprovider.executor.SQLExecutor;
import com.yuhtin.commission.pointssystem.commands.HabilityCommand;
import com.yuhtin.commission.pointssystem.dao.SQLProvider;
import com.yuhtin.commission.pointssystem.dao.repository.AccountRepository;
import com.yuhtin.commission.pointssystem.listener.ListenerRegistry;
import com.yuhtin.commission.pointssystem.storage.AccountStorage;
import lombok.Getter;
import lombok.val;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.logging.Level;

@Getter
public final class PointsSystem extends JavaPlugin {

    private AccountRepository accountRepository;
    private AccountStorage accountStorage;


    @Override
    public void onLoad() {
        val configFile = new File(getDataFolder(), "config.yml");
        if (!configFile.exists()) saveResource("config.yml", false);
    }

    @Override
    public void onEnable() {
        getLogger().info("Iniciando carregamento do plugin.");
        val loadTime = Stopwatch.createStarted();

        val sqlConnector = SQLProvider.of(this).setup(null);
        val sqlExecutor = new SQLExecutor(sqlConnector);

        accountRepository = new AccountRepository(sqlExecutor);
        accountStorage = new AccountStorage(accountRepository);

        accountStorage.init();

        InventoryManager.enable(this);
        ListenerRegistry.of(this).register();

        getCommand("habilidades").setExecutor(new HabilityCommand());

        loadTime.stop();
        getLogger().log(Level.INFO, "Plugin inicializado com sucesso. ({0})", loadTime);
    }

    public static PointsSystem getInstance() {
        return PointsSystem.getPlugin(PointsSystem.class);
    }

}
