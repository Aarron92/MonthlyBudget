package com.soob.monthlybudget.databaselayer.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.soob.monthlybudget.enums.AccountType;
import com.soob.monthlybudget.databaselayer.entities.Account;

import java.util.List;
import java.util.UUID;

/**
 * Interface defining Data Access Object to interact with the Accounts table
 *
 * Includes methods that offer abstract access to your app's database. At compile time, the Room
 * Persistence Library automatically generates implementations of the DAOs
 *
 * TODO: MIGHT NEED TO ADD METHODS TO GET INDIVIDUAL FIELDS INSTEAD OF RETURNING THE WHOLE RECORD (APPLIES ACROSS DAOs)
 */
@Dao
public interface AccountDao
{
    @Insert
    void insertAccount(Account account);

    @Update
    void updateAccount(Account account);

    @Delete
    void deleteAccount(Account account);

    @Delete
    void deleteMultipleAccounts(Account... accounts);

    @Query("SELECT * FROM accounts")
    List<Account> getAllAccounts();

    @Query("SELECT name FROM accounts")
    List<String> getAllAccountNames();

    @Query("DELETE FROM accounts")
    void deleteAllAccounts();
}