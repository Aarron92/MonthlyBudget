package com.soob.monthlybudget.adapters.abstracts;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.soob.monthlybudget.R;

/**
 * Inner class to hold the View. This class creates the UI elements in the list layout to be
 * populated
 */
public class DataListViewHolder extends RecyclerView.ViewHolder
{
    /**
     * The top level card view
     */
    CardView cardView;

    /**
     * Constructor
     *
     * @param view View in the ViewHolder
     */
    public DataListViewHolder(View view, int topLevelCardView)
    {
        super(view);

        // the card view containing the various other child views
        this.cardView = view.findViewById(topLevelCardView);
    }
}


