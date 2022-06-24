package com.soob.monthlybudget.databaselayer.entities;

import static androidx.room.ForeignKey.SET_NULL;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Date;
import java.util.UUID;

/**
 * Entity class representing the transactions table in the database. Represents a spending transaciton
 * that the user has add e.g. spent £3 on coffee
 *
 * Has an optional value that can link an outgoing payment to an account via its UUID primary key,
 * if the parent account is deleted then the foreign key reference will just be set to null so the
 * outgoing payment is not removed
 */
@Entity(tableName = "spending_transactions", foreignKeys = @ForeignKey(entity = Account.class, parentColumns = "name", childColumns = "accountRef", onDelete = SET_NULL))
public class SpendingTransaction
{
    /**
     * The primary ID of the record
     */
    @PrimaryKey
    @NonNull
    public UUID id;

    /**
     * The date of the transaction
     */
    @ColumnInfo(name = "date")
    @NonNull
    public String date;

    /**
     * The amount spent in the transaction - stored up to two decimal places
     */
    @ColumnInfo(name = "amount")
    @NonNull
    public Double amount;

    /**
     * An optional comment about the transaction
     */
    @ColumnInfo(name = "comment")
    public String comment;

    /**
     * An optional field linking an outgoing payment to an account
     */
    @ColumnInfo(name = "accountRef")
    public String accountRef;

    public SpendingTransaction()
    {
        this.id = UUID.randomUUID();
    }

    public SpendingTransaction(@NonNull UUID id, @NonNull String date, @NonNull Double amount, String comment, String accountRef)
    {
        this.id = id;
        this.date = date;
        this.amount = amount;
        this.comment = comment;
        this.accountRef = accountRef;
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
    public String getDate()
    {
        return this.date;
    }

    public void setDate(@NonNull String date)
    {
        this.date = date;
    }

    @NonNull
    public Double getAmount()
    {
        return this.amount;
    }

    public void setAmount(@NonNull Double amount)
    {
        this.amount = amount;
    }

    public String getComment()
    {
        return this.comment;
    }

    public void setComment(String comment)
    {
        this.comment = comment;
    }

    public String getAccountRef()
    {
        return this.accountRef;
    }

    public void setAccountRef(String accountRef)
    {
        this.accountRef = accountRef;
    }
}
