package com.soob.monthlybudget.databaselayer.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.soob.monthlybudget.databaselayer.entities.DailyLimit;

import java.util.Date;
import java.util.List;

/**
 * Interface defining Data Access Object to interact with the Daily Limit table
 *
 * Includes methods that offer abstract access to your app's database. At compile time, the Room
 * Persistence Library automatically generates implementations of the DAOs
 */
@Dao
public interface DailyLimitDao
{
    @Insert
    void insertDailyLimit(DailyLimit dailyLimit);

    @Delete
    void deleteDailyLimit(DailyLimit dailyLimit);

    @Delete
    void deleteMultipleDailyLimit(DailyLimit... dailyLimit);

    @Query("SELECT * FROM daily_limit")
    List<DailyLimit> getAllDailyLimits();

    @Query("SELECT * FROM daily_limit where date = :date")
    void getAmountForMonth(Date date);

    @Query("UPDATE daily_limit SET 'limit' = :newAmount WHERE date = :date")
    void updateAmountForMonth(Double newAmount, Date date);
}