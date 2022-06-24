package com.soob.monthlybudget.databaselayer.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.soob.monthlybudget.enums.AccountType;
import com.soob.monthlybudget.databaselayer.entities.InvestmentAccount;

import java.util.List;
import java.util.UUID;

/**
 * Interface defining Data Access Object to interact with the Investment Account table
 *
 * Extends the AccountDao interface to access methods there too
 *
 * Includes methods that offer abstract access to your app's database. At compile time, the Room
 * Persistence Library automatically generates implementations of the DAOs
 */
@Dao
public interface InvestmentAccountDao extends AccountDao
{
    // TODO: LOOK AT HOW THIS WORKS WITH INHERITANCE, MIGHT NOT NEED TO DECLARE ALL THESE

    @Insert
    void insertInvestmentAccount(InvestmentAccount investmentAccount);

    @Delete
    void deleteInvestmentAccount(InvestmentAccount investmentAccount);

    @Delete
    void deleteMultipleInvestmentAccounts(InvestmentAccount... investmentAccount);

    @Query("SELECT * FROM investments")
    List<InvestmentAccount> getAllInvestmentAccounts();

    @Query("UPDATE investments SET name = :newName WHERE id = :id")
    void updateName(UUID id, String newName);

    @Query("UPDATE investments SET amount = :newAmount WHERE name = :name")
    void updateAmount(String name, Double newAmount);

    @Query("UPDATE investments SET comment = :newComment WHERE name = :name")
    void updateComment(String name, String newComment);

    @Query("UPDATE investments SET type = :newType WHERE name = :name")
    void updateType(String name, AccountType newType);

    @Query("UPDATE investments SET originalInvestmentAmount = :newOriginalInvestment WHERE name = :name")
    void updateOriginalInvestmentAmount(String name, Double newOriginalInvestment);
}