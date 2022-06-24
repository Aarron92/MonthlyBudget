package com.soob.monthlybudget.databaselayer.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.soob.monthlybudget.databaselayer.entities.Outgoing;
import com.soob.monthlybudget.databaselayer.entities.SpendingTransaction;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Interface defining Data Access Object to interact with the Spending Transaction table
 *
 * Includes methods that offer abstract access to your app's database. At compile time, the Room
 * Persistence Library automatically generates implementations of the DAOs
 */
@Dao
public interface SpendingTransactionsDao
{
    @Insert
    void insertSpendingTransaction(SpendingTransaction spendingTransaction);

    @Update
    void updateSpendingTransaction(SpendingTransaction spendingTransaction);

    @Delete
    void deleteSpendingTransaction(SpendingTransaction spendingTransaction);

    @Delete
    void deleteMultipleSpendingTransactions(SpendingTransaction... spendingTransactions);

    @Query("SELECT * FROM spending_transactions")
    List<SpendingTransaction> getAllSpendingTransactions();
}