package com.soob.monthlybudget.databaselayer.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Date;
import java.util.UUID;

/**
 * Entity class representing the monthly_savings table in the database. This persists data covering
 * the user's currently monthly savings goal and also information about previous months
 *
 * Can only be one record per month, so the value of the month must be unique
 */
@Entity(tableName = "monthly_savings", indices = {@Index(value = {"monthSaved"},unique = true)})
public class MonthlySavings
{
    /**
     * The primary ID of the record
     */
    @PrimaryKey
    @NonNull
    public UUID id;

    /**
     * Amount saved in a previous month
     */
    @NonNull
    private Double previousSavedAmount;

    /**
     * Date the amount was saved - only really care about the month but store the whole date
     */
    @NonNull
    private Date date;

    /**
     * Daily limit that was used for that month
     */
    @NonNull
    private Double dailyLimitForTheMonth;

    /**
     * Average spent each day during this month
     */
    @NonNull
    private Double averageSpentPerDay;

    /**
     * Total spent for this month
     */
    @NonNull
    private Double totalSpent;

    /**
     * Total saved for this month
     */
    @NonNull
    private Double totalSaved;

    public MonthlySavings()
    {
    }

    public MonthlySavings(@NonNull UUID id, @NonNull Double previousSavedAmount,
                          @NonNull Date date, @NonNull Double dailyLimitForTheMonth,
                          @NonNull Double averageSpentPerDay, @NonNull Double totalSpent,
                          @NonNull Double totalSaved)
    {
        this.id = id;
        this.previousSavedAmount = previousSavedAmount;
        this.date = date;
        this.dailyLimitForTheMonth = dailyLimitForTheMonth;
        this.averageSpentPerDay = averageSpentPerDay;
        this.totalSpent = totalSpent;
        this.totalSaved = totalSaved;
    }

    @NonNull
    public UUID getId()
    {
        return this.id;
    }

    public void setId(@NonNull UUID id)
    {
        this.id = id;
    }

    @NonNull
    public Double getPreviousSavedAmount()
    {
        return previousSavedAmount;
    }

    public void setPreviousSavedAmount(@NonNull Double previousSavedAmount)
    {
        this.previousSavedAmount = previousSavedAmount;
    }

    @NonNull
    public Date getDate()
    {
        return this.date;
    }

    public void setDate(@NonNull Date date)
    {
        this.date = date;
    }

    @NonNull
    public Double getDailyLimitForTheMonth()
    {
        return this.dailyLimitForTheMonth;
    }

    public void setDailyLimitForTheMonth(@NonNull Double dailyLimitForTheMonth)
    {
        this.dailyLimitForTheMonth = dailyLimitForTheMonth;
    }

    @NonNull
    public Double getAverageSpentPerDay()
    {
        return this.averageSpentPerDay;
    }

    public void setAverageSpentPerDay(@NonNull Double averageSpentPerDay)
    {
        this.averageSpentPerDay = averageSpentPerDay;
    }

    @NonNull
    public Double getTotalSpent()
    {
        return this.totalSpent;
    }

    public void setTotalSpent(@NonNull Double totalSpent)
    {
        this.totalSpent = totalSpent;
    }

    @NonNull
    public Double getTotalSaved()
    {
        return this.totalSaved;
    }

    public void setTotalSaved(@NonNull Double totalSaved)
    {
        this.totalSaved = totalSaved;
    }
}