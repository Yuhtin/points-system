package com.yuhtin.commission.pointssystem.dao.repository.adapter;

import com.henryfabio.sqlprovider.executor.adapter.SQLResultAdapter;
import com.henryfabio.sqlprovider.executor.result.SimpleResultSet;
import com.yuhtin.commission.pointssystem.api.Account;

public final class AccountAdapter implements SQLResultAdapter<Account> {

    @Override
    public Account adaptResult(SimpleResultSet resultSet) {
        String accountOwner = resultSet.get("username");
        double points = resultSet.get("points");

        return new Account(accountOwner).setPoints(points);
    }

}
