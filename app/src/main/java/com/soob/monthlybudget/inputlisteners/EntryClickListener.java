package com.soob.monthlybudget.inputlisteners;

import com.soob.monthlybudget.databaselayer.entities.Account;

/**
 * Interface for listening for click events on the entries in various list views e.g. Accounts
 */
public interface EntryClickListener<T>
{
    /**
     * When the user clicks on an entry, detect the click and perform and action
     *
     * @param data the entry the user clicks on
     * @param index index of the data in the list
     */
    void onClick(final T data, final int index);
}
