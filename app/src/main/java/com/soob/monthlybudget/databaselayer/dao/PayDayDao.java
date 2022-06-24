package com.soob.monthlybudget.databaselayer.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.soob.monthlybudget.databaselayer.entities.PayDay;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Interface defining Data Access Object to interact with the Pay Day table
 *
 * Includes methods that offer abstract access to your app's database. At compile time, the Room
 * Persistence Library automatically generates implementations of the DAOs
 */
@Dao
public interface PayDayDao
{
    @Insert
    void insertPayDay(PayDay payDay);

    @Delete
    void deletePayDay(PayDay payDay);

    @Delete
    void deleteMultiplePayDays(PayDay... payDay);

    @Query("SELECT * FROM pay_day")
    List<PayDay> getAllPayDays();

    @Query("UPDATE pay_day SET amount = :newAmount WHERE id = :id")
    void updateAmount(UUID id, Double newAmount);

    @Query("UPDATE pay_day SET date = :newDate WHERE id = :id")
    void updateDate(UUID id, Date newDate);
}