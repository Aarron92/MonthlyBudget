package com.soob.monthlybudget.databaselayer.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.soob.monthlybudget.databaselayer.entities.Account;
import com.soob.monthlybudget.databaselayer.entities.DailyLimit;
import com.soob.monthlybudget.databaselayer.entities.MonthlySavings;

import java.util.Date;
import java.util.List;

/**
 * Interface defining Data Access Object to interact with the Monthly Savings table
 *
 * Includes methods that offer abstract access to your app's database. At compile time, the Room
 * Persistence Library automatically generates implementations of the DAOs
 */
@Dao
public interface MonthlySavingsDao
{
    @Insert
    void insertMonthlySaving(MonthlySavings monthlySavings);

    @Delete
    void deleteMonthlySavings(MonthlySavings monthlySavings);

    @Delete
    void deleteMultipleMonthlySavings(MonthlySavings... monthlySavings);

    @Query("SELECT * FROM monthly_savings")
    List<MonthlySavings> getAllMonthlySavings();

    @Query("UPDATE monthly_savings SET previousSavedAmount = :newAmount WHERE date = :date")
    void updateAmountForMonth(Date date, Double newAmount);

    @Query("UPDATE monthly_savings SET dailyLimitForTheMonth = :newAmount WHERE date = :date")
    void updateDailyLimitForMonth(Date date, Double newAmount);

    @Query("UPDATE monthly_savings SET averageSpentPerDay = :newAmount WHERE date = :date")
    void updateAvgSpentForMonth(Date date, Double newAmount);

    @Query("UPDATE monthly_savings SET totalSpent = :newAmount WHERE date = :date")
    void updateTotalSpentForMonth(Date date, Double newAmount);

    @Query("UPDATE monthly_savings SET totalSaved = :newAmount WHERE date = :date")
    void updateTotalSavedForMonth(Date date, Double newAmount);
}