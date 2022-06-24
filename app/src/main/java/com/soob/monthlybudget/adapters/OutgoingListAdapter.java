package com.soob.monthlybudget.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.soob.monthlybudget.R;
import com.soob.monthlybudget.adapters.abstracts.DataListAdapter;
import com.soob.monthlybudget.adapters.abstracts.DataListViewHolder;
import com.soob.monthlybudget.databaselayer.entities.Outgoing;
import com.soob.monthlybudget.inputlisteners.EntryClickListener;

import java.util.List;
import java.util.Locale;

/**
 * Adapter class for the RecyclerView used on the Outgoing screen
 *
 * RecyclerView's require two things; the Adapter and the ViewHolder. The ViewHolder is what hold's
 * the RecyclerView on-screen and the Adapter binds the data to the actual view so that it can be
 * displayed. The ViewHolder is an inner class within this Adapter class
 */
public class OutgoingListAdapter extends DataListAdapter<Outgoing, OutgoingListAdapter.OutgoingsViewHolder>
{
    /**
     * Constructor
     *
     * @param context the context to use
     * @param outgoingList the Outgoing data set as a List
     * @param outgoingClickListener click listener for the outgoing entries
     */
    public OutgoingListAdapter(Context context, List<Outgoing> outgoingList,
                               EntryClickListener<Outgoing> outgoingClickListener,
                               int layoutResource, int cardViewResource)
    {
        super(context, outgoingList, outgoingClickListener, layoutResource, cardViewResource);
    }

    @Override
    protected void setTextFields(Outgoing entry, OutgoingsViewHolder holder)
    {
        // bind the data to the UI elements insider the holder
        holder.nameTextView.setText(entry.getName());

        // TODO: CURRENCY SYMBOL WILL BE SET HERE AFTER READING FROM SETTINGS, USING £ FOR NOW
        String formattedAmount = "£" + String.format(Locale.ENGLISH, "%.2f", entry.getAmount());
        holder.amountTextView.setText(formattedAmount);

        holder.dateTextView.setText(entry.getDate().toString());
        holder.commentTextView.setText(entry.getComment());
        holder.accountRefTextView.setText(entry.getAccountRef());
    }

    @NonNull
    @Override
    public OutgoingListAdapter.OutgoingsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        return new OutgoingListAdapter.OutgoingsViewHolder(inflateUi(), super.cardViewResource);
    }

    /**
     * Inner class to hold the View. This class creates the UI elements in the list layout to be
     * populated
     */
    public static class OutgoingsViewHolder extends DataListViewHolder
    {
        /**
         * The TextView containing the Outgoing entry's name
         */
        TextView nameTextView;

        /**
         * The TextView containing the Outgoing entry's amount
         */
        TextView amountTextView;

        /**
         * TODO: THIS WILL EVENTUALLY BE UPDATED TO A PROPER DATE FIELD
         * The TextView containing the Outgoing entry's date
         */
        TextView dateTextView;

        /**
         * The TextView containing the Outgoing entry's comment
         */
        TextView commentTextView;

        /**
         * The TextView containing the Outgoing entry's account reference
         */
        TextView accountRefTextView;

        /**
         * Constructor
         *
         * @param view View in the ViewHolder
         */
        public OutgoingsViewHolder(View view, int cardViewResource)
        {
            super(view, cardViewResource);

            // find the various inner View objects from the view parameter using their IDs
            this.nameTextView = view.findViewById(R.id.layout_outgoings_list_outgoing_name);
            this.amountTextView = view.findViewById(R.id.layout_outgoings_list_outgoing_amount);
            this.dateTextView = view.findViewById(R.id.layout_outgoings_list_outgoing_date);
            this.commentTextView = view.findViewById(R.id.layout_outgoings_list_outgoing_comment);
            this.accountRefTextView = view.findViewById(R.id.layout_outgoings_list_outgoing_account_ref);
        }
    }
}
