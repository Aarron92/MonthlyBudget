package com.soob.monthlybudget.databaselayer.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;

import com.soob.monthlybudget.enums.AccountType;

import java.util.UUID;

/**
 * Entity class representing the Investments table in the database. This is a subclass of the
 * Account table as it is largely the same but has an additional field allowing it to persist data
 * about the original investment. Represents an investment
 */
@Entity(tableName = "investments")
public class InvestmentAccount extends Account
{
    /**
     * The original value of the investment
     */
    public Double originalInvestmentAmount;

    /**
     * Empty Constructor
     */
    public InvestmentAccount()
    {
    }

    /**
     * Constructor with values to set
     *
     * @param id UUID primary ID of the record
     * @param name name of the account in the record
     * @param amount amount of money in the account
     * @param comment optional comment field in the account
     * @param type optional field showing the type of account
     * @param originalInvestmentAmount optional field showing the originally invested amount in the account
     */
    public InvestmentAccount(@NonNull UUID id, @NonNull String name, @NonNull Double amount,
                             String comment, AccountType type, Double originalInvestmentAmount)
    {
       super(id, name, amount, comment, type);
       this.originalInvestmentAmount = originalInvestmentAmount;
    }

    public Double getOriginalInvestmentAmount()
    {
        return this.originalInvestmentAmount;
    }

    public void setOriginalInvestmentAmount(@NonNull Double originalInvestmentAmount)
    {
        this.originalInvestmentAmount = originalInvestmentAmount;
    }
}