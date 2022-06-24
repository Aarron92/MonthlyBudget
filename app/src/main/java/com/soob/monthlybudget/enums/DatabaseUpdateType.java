package com.soob.monthlybudget.enums;

/**
 * Enum for types of database transaction
 */
public enum DatabaseUpdateType
{
    ADD("ADD"),
    GET("GET"),
    GET_ALL("GET_ALL"),
    UPDATE("UPDATE"),
    DELETE("DELETE");

    /**
     * Key - type of transactions
     */
    private final String key;

    DatabaseUpdateType(final String key)
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
