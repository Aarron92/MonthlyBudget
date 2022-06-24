package com.soob.monthlybudget.enums;

/**
 * Enum for account types
 */
public enum AccountType
{
    CURRENT("Current"),
    SAVINGS("Savings"),
    BUSINESS("Business"),
    JOINT("Joint"),
    CHILDREN("Children's"),
    STUDENT("Student"),
    FUND_AND_SHARE("Funds and Shares"),
    S_AND_S_ISA("Stocks and Shares ISA"),
    LISA("Lifetime ISA"),
    JUNIOR_ISA("Junior ISA"),
    CASH_ISA("Cash ISA"),
    INVESTMENT("Investment"),
    CREDIT_CARD("Credit Card"),
    OTHER("Other");

    /**
     * Key - name of the type of the account
     */
    private final String key;

    AccountType(final String key)
    {
        this.key = key;
    }

    /**
     * Get the key
     *
     * @return the Enum key
     */
    public String getKey()
    {
        return this.key;
    }
}
