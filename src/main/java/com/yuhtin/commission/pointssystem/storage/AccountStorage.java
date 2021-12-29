package com.yuhtin.commission.pointssystem.storage;

import com.google.common.base.Stopwatch;
import com.yuhtin.commission.pointssystem.PointsSystem;
import com.yuhtin.commission.pointssystem.api.Account;
import com.yuhtin.commission.pointssystem.dao.repository.AccountRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.val;
import lombok.var;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Getter
@RequiredArgsConstructor
public final class AccountStorage {

    private final AccountRepository accountRepository;
    private final HashMap<String, Account> cache = new HashMap<>();

    public void init() {
        accountRepository.createTable();
        PointsSystem.getInstance().getLogger().info("DAO do plugin iniciado com sucesso.");
    }

    /**
     * Save account in repository
     *
     * @param account to save
     */
    public void saveOne(@NotNull Account account) {
        accountRepository.saveOne(account);
    }

    /**
     * Find a user in repository
     *
     * @param owner uuid or nick of user
     * @return {@link Account} found
     */
    @Nullable
    private Account selectOne(@NotNull String owner) {
        return accountRepository.selectOne(owner);
    }

    /**
     * Used to get created accounts by name
     *
     * @param name player name
     * @return {@link Account} found
     */
    @Nullable
    public Account findAccountByName(@NotNull String name) {
        var account = cache.get(name);
        if (account == null) account = accountRepository.selectOne(name);

        return account;
    }

    /**
     * Used to get accounts
     * If player is online and no have account, we will create a new for them
     * but, if is offline, will return null
     *
     * @param offlinePlayer player
     * @return {@link Account} found
     */
    @Nullable
    public Account findAccount(@NotNull OfflinePlayer offlinePlayer) {
        if (offlinePlayer.isOnline()) {
            val player = offlinePlayer.getPlayer();
            if (player != null) return findAccount(player);
        }

        if (offlinePlayer.getName() == null) return null;
        return findAccountByName(offlinePlayer.getName());
    }

    /**
     * Used to get accounts
     *
     * @param player player to search
     * @return {@link Account} found
     */
    @NotNull
    public Account findAccount(@NotNull Player player) {
        Account account = findAccountByName(player.getName());
        if (account == null) {
            account = new Account(player.getName());
            put(account);
        }

        return account;
    }

    /**
     * Put account directly in cache (will be sync to database automaticly)
     *
     * @param account of player
     */
    public void put(@NotNull Account account) {
        cache.put(account.getUsername(), account);
    }

    private static final ExecutorService executor = Executors.newFixedThreadPool(128);

    /**
     * Flush data from cache
     */
    public void flushData() {
        val loadTime = Stopwatch.createStarted();

        executor.execute(() -> {
            for (val entry : cache.entrySet()) {
                executor.execute(() -> accountRepository.saveOne(entry.getValue()));
            }
        });

        PointsSystem.getInstance().getLogger().info("Flushed data in " + loadTime);
    }

}
