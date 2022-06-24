package com.soob.monthlybudget.databaselayer.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.soob.monthlybudget.enums.AccountType;

import java.util.UUID;

/**
 * Entity class representing the Accounts table in the database. Represents a single bank account
 * or another equivalent entry
 *
 * Names of entries must be unique
 */
@Entity(tableName = "accounts", indices = {@Index(value = {"name"},unique = true)})
public class Account
{
    /**
     * The primary ID of the record
     */
    @PrimaryKey
    @NonNull
    public UUID id;

    /**
     * The name of the account
     */
    @ColumnInfo(name = "name")
    @NonNull
    public String name;

    /**
     * The amount of money in the account - stored up to two decimal places
     */
    @ColumnInfo(name = "amount")
    @NonNull
    public Double amount;

    /**
     * An optional comment on the account e.g. 'My current account'
     */
    @ColumnInfo(name = "comment")
    public String comment;

    /**
     * An optional field detailing the type of account e.g. 'Savings Account'
     */
    @ColumnInfo(name = "type")
    public AccountType type;

    /**
     * Default constructor for creating a new Account with a newly generated UUID
     */
    public Account()
    {
        this.id = UUID.randomUUID();
    }

    /**
     * Constructor for creating a new Account with a known UUID to set the ID to - used for updating
     * a known Account
     *
     * @param uuid the value to set the UUID primary ID of the record to
     */
    public Account(@NonNull UUID uuid)
    {
        this.id = uuid;
    }

    /**
     * Constructor with values to set
     *
     * @param id UUID primary ID of the record
     * @param name name of the account in the record
     * @param amount amount of money in the account
     * @param comment optional comment field in the account
     * @param type optional field showing the type of account
     */
    public Account(@NonNull UUID id, @NonNull String name, @NonNull Double amount, String comment,
                   AccountType type)
    {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.comment = comment;
        this.type = type;
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
    public String getName()
    {
        return this.name;
    }

    public void setName(@NonNull String name)
    {
        this.name = name;
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

    public AccountType getType()
    {
        return this.type;
    }

    public void setType(AccountType type)
    {
        this.type = type;
    }
}