package com.soob.monthlybudget.databaselayer.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Date;
import java.util.UUID;

/**
 * Entity class representing the pay_day table in the database. Represents the day the user is paid
 * and how much they will be getting
 *
 * TODO: CURRENTLY ONLY WILL WORK WITH ONE PAY DAY BUT MIGHT NEED TO ADD MORE, IN THE MEANTIME, DATE NEEDS TO BE UNIQUE
 */
@Entity(tableName = "pay_day", indices = {@Index(value = {"date"},unique = true)})
public class PayDay
{
    /**
     * The primary ID of the record
     */
    @PrimaryKey
    @NonNull
    public UUID id;

    /**
     * How much is being paid
     */
    @ColumnInfo(name = "amount")
    @NonNull
    public String amount;

    /**
     * The date the payment is made
     */
    @ColumnInfo(name = "date")
    @NonNull
    public Date date;

    public PayDay()
    {}

    public PayDay(@NonNull UUID id, @NonNull String amount, @NonNull Date date)
    {
        this.id = id;
        this.amount = amount;
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
    public String getAmount()
    {
        return this.amount;
    }

    public void setAmount(@NonNull String amount)
    {
        this.amount = amount;
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
