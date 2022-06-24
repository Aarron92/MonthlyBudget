package com.soob.monthlybudget.databaselayer.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Date;
import java.util.UUID;

/**
 * Entity class representing the daily_limit table in the database. Represents the daily spending
 * limit for the current month
 *
 * Can only be one record per month, so date field must be unique
 */
@Entity(tableName = "daily_limit", indices = {@Index(value = {"date"},unique = true)})
public class DailyLimit
{
    /**
     * The primary ID of the record
     */
    @PrimaryKey
    @NonNull
    public UUID id;

    /**
     * The daily allowance for this month
     */
    @ColumnInfo(name = "limit")
    @NonNull
    public String limit;

    /**
     * The date of the month when this limit was in use - only month is really relevant here
     */
    @ColumnInfo(name = "date")
    @NonNull
    public Date date;

    public DailyLimit()
    {}

    public DailyLimit(@NonNull UUID id, @NonNull String limit, @NonNull Date date)
    {
        this.id = id;
        this.limit = limit;
        this.date = date;
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
    public String getLimit()
    {
        return this.limit;
    }

    public void setLimit(@NonNull String limit)
    {
        this.limit = limit;
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
}
