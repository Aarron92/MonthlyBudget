package com.soob.monthlybudget.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.soob.monthlybudget.R;
import com.soob.monthlybudget.adapters.abstracts.DataListAdapter;
import com.soob.monthlybudget.adapters.abstracts.DataListViewHolder;
import com.soob.monthlybudget.databaselayer.entities.Account;
import com.soob.monthlybudget.inputlisteners.EntryClickListener;
import com.soob.monthlybudget.ui.accounts.AccountsFragment;

import java.util.List;
import java.util.Locale;

/**
 * Adapter class for the RecyclerView used on the Accounts screen
 *
 * RecyclerView's require two things; the Adapter and the ViewHolder. The ViewHolder is what hold's
 * the RecyclerView on-screen and the Adapter binds the data to the actual view so that it can be
 * displayed. The ViewHolder is an inner class within this Adapter class
 */
public class AccountsListAdapter extends DataListAdapter<Account, AccountsListAdapter.AccountsViewHolder>
{
    /**
     * Constructor
     *
     * @param context the context to use
     * @param accountList the Account data set as a List
     * @param accountClickListener click listener for the Account entries
     */
    public AccountsListAdapter(Context context, List<Account> accountList, EntryClickListener<Account> accountClickListener,
                               int layoutResource, int cardViewResource)
    {
        super(context, accountList, accountClickListener, layoutResource, cardViewResource);
    }

    @Override
    protected void setTextFields(Account entry, AccountsViewHolder holder)
    {
        // bind the data to the UI elements insider the holder
        holder.nameTextView.setText(entry.getName());

        // TODO: CURRENCY SYMBOL WILL BE SET HERE AFTER READING FROM SETTINGS, USING £ FOR NOW
        String formattedAmount = "£" + String.format(Locale.ENGLISH, "%.2f", entry.getAmount());
        holder.amountTextView.setText(formattedAmount);

        holder.commentTextView.setText(entry.getComment());
    }

    @NonNull
    @Override
    public AccountsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        return new AccountsViewHolder(inflateUi(), super.cardViewResource);
    }

    /**
     * Inner class to hold the View. This class creates the UI elements in the list layout to be
     * populated
     */
    public static class AccountsViewHolder extends DataListViewHolder
    {
        /**
         * The TextView containing the Account's name
         */
        TextView nameTextView;

        /**
         * The TextView containing the Account's name
         */
        TextView amountTextView;

        /**
         * The TextView containing the Account's name
         */
        TextView commentTextView;

        /**
         * Constructor
         *
         * @param view View in the ViewHolder
         */
        public AccountsViewHolder(View view, int cardViewResource)
        {
            super(view, cardViewResource);

            // find the various inner View objects from the view parameter using their IDs
            this.nameTextView = view.findViewById(R.id.layout_accounts_list_account_name);
            this.amountTextView = view.findViewById(R.id.layout_accounts_list_account_amount);
            this.commentTextView = view.findViewById(R.id.layout_accounts_list_account_comment);
        }
    }
}