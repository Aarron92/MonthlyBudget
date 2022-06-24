package com.soob.monthlybudget.databaselayer.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.soob.monthlybudget.databaselayer.dao.AccountDao;
import com.soob.monthlybudget.databaselayer.dao.OutgoingsDao;
import com.soob.monthlybudget.databaselayer.dao.SpendingTransactionsDao;
import com.soob.monthlybudget.databaselayer.entities.Account;
import com.soob.monthlybudget.databaselayer.entities.Outgoing;
import com.soob.monthlybudget.databaselayer.entities.SpendingTransaction;

@Database(version = 3, entities = {Account.class, Outgoing.class, SpendingTransaction.class})
public abstract class BudgetDatabase extends RoomDatabase
{
    public abstract AccountDao getAccountDao();

    public abstract OutgoingsDao getOutgoingsDao();

    public abstract SpendingTransactionsDao getSpendingTransactionsDao();
}

