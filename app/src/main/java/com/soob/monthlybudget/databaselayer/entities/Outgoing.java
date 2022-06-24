package com.soob.monthlybudget.databaselayer.entities;

import static androidx.room.ForeignKey.SET_NULL;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Date;
import java.util.UUID;

/**
 * Entity class representing the Outgoing table in the database. Represents known outgoings for the
 * month e.g. rent
 *
 * Has an optional value that can link an outgoing payment to an account via its UUID primary key,
 * if the parent account is deleted then the foreign key reference will just be set to null so the
 * outgoing payment is not removed
 *
 * Only one entry per outgoing, so name field has to be unique
 */
@Entity(tableName = "outgoings",
        foreignKeys = @ForeignKey(entity = Account.class, parentColumns = "name", childColumns = "accountRef", onDelete = SET_NULL),
        indices = {@Index(value = {"name"},unique = true)})
public class Outgoing
{
    /**
     * The primary ID of the record
     */
    @PrimaryKey
    @NonNull
    public UUID id;

    /**
     * The name of the outgoing e.g. 'Rent'
     */
    @ColumnInfo(name = "name")
    @NonNull
    public String name;

    /**
     * The amount of money that the outgoing costs - stored up to two decimal places
     */
    @ColumnInfo(name = "amount")
    @NonNull
    public Double amount;

    /**
     * The date of the outgoing payment
     *
     * TODO: String for now, will eventually be a date object
     */
    @ColumnInfo(name = "date")
    public String date;

    /**
     * An optional field linking an outgoing payment to an account
     *
     * Links to the name column of the Account table
     */
    @ColumnInfo(name = "accountRef")
    public String accountRef;

    /**
     * An optional comment on the outgoing cost e.g. 'Mortgage payment to Santander'
     */
    @ColumnInfo(name = "comment")
    public String comment;

    /**
     * Default constructor for creating a new Outgoing with a newly generated UUID
     */
    public Outgoing()
    {
        this.id = UUID.randomUUID();
    }

    /**
     * Constructor with values to set
     *
     * @param id UUID primary ID of the record
     * @param name name of the outgoing payment in the record
     * @param amount amount of money that is outgoing
     * @param accountRef optional field linking the outgoing payment to an account via it's primary key ID
     * @param comment optional comment field for the outgoing payment
     */
    public Outgoing(@NonNull UUID id, @NonNull String name, @NonNull Double amount,
                    String date, String accountRef, String comment)
    {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.date = date;
        this.accountRef = accountRef;
        this.comment = comment;
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

    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public String getAccountRef()
    {
        return this.accountRef;
    }

    public void setAccountRef(String accountRef)
    {
        this.accountRef = accountRef;
    }

    public String getComment()
    {
        return this.comment;
    }

    public void setComment(String comment)
    {
        this.comment = comment;
    }
}