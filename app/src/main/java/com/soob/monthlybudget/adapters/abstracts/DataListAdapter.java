package com.soob.monthlybudget.adapters.abstracts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.soob.monthlybudget.inputlisteners.EntryClickListener;

import java.util.List;

/**
 * Adapter class for the RecyclerView used on the Accounts screen
 *
 * RecyclerView's require two things; the Adapter and the ViewHolder. The ViewHolder is what hold's
 * the RecyclerView on-screen and the Adapter binds the data to the actual view so that it can be
 * displayed. The ViewHolder is an inner class within this Adapter class
 *
 * T = type of data entry e.g. Account
 * V = the view holder subclass impl
 */
public abstract class DataListAdapter<T, V extends DataListViewHolder> extends RecyclerView.Adapter<V>
{
    /**
     * The Context of the Adapter. Context is essentially global information about an application's
     * current environment. This is need to inflate the layout using the LayoutInflater so that the
     * View can be passed to the ViewHolder class
     */
    protected final Context context;

    /**
     * List of objects that makes up the data set e.g. Account
     */
    protected final List<T> dataList;

    /**
     * Listener for the items that can be clicked on
     */
    protected final EntryClickListener<T> clickListener;

    /**
     * Resource identifier for the layout
     */
    protected final int layoutResource;

    /**
     * Resource identifier for the top level card view
     */
    protected final int cardViewResource;

    /**
     * Constructor
     *
     * @param context application context
     * @param dataList list of data entries displayed to the user
     * @param clickListener listener for clicks on entries
     * @param layoutResource the identifier of layout to bind the data adapter to
     * @param cardViewResource the top level card view containing fields to populated with data
     */
    public DataListAdapter(Context context, List<T> dataList, EntryClickListener<T> clickListener,
                           int layoutResource, int cardViewResource)
    {
        this.context = context;
        this.dataList = dataList;
        this.clickListener = clickListener;
        this.layoutResource = layoutResource;
        this.cardViewResource = cardViewResource;
    }

    /**
     * Inflate the UI with the context and the relevant layout so that data can be added, then
     * return an inflated View that can be used to create a new ViewHolder by the adapter impl class
     *
     * @return a View that can be used to create a ViewHolder with
     */
    protected View inflateUi()
    {
        // create a LayoutInflater - these are used to instantiate a layout XML file into its
        // corresponding View objects using the Context
        LayoutInflater layoutInflater = LayoutInflater.from(this.context);

        // create a View with the ID of the layout we want to inflate and the View group that will
        // behave as a parent to the view - in this case however, we don't want a parent so use null
        return layoutInflater.inflate(this.layoutResource, null);
    }

    /**
     * Binds data to the ViewHolder
     *
     * @param holder the ViewHolder with the UI elements to bind the data to
     * @param position the position of a given item within the adapter's data set
     */
    @Override
    public void onBindViewHolder(@NonNull V holder, int position)
    {
        // get the data for the Account from the data set list
        T dataEntry = this.dataList.get(position);

        // bind the click listener to the cards
        holder.cardView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                int index = holder.getAdapterPosition();
                clickListener.onClick(dataList.get(index), index);
            }
        });

        setTextFields(dataEntry, holder);
    }

    /**
     * Implementation to set the various text fields on the UI. Each screen is slightly different
     * so is implemented by the subclasses as needed
     *
     * @param entry data entry to read data from e.g. Account
     * @param holder the ViewHolder
     */
    protected abstract void setTextFields(T entry, V holder);

    /**
     * Return the size of the adapter's data set - i.e. the number of Accounts in the List
     *
     * @return the size of the data set
     */
    @Override
    public int getItemCount()
    {
        int size = 0;

        if(this.dataList != null)
        {
            size = this.dataList.size();
        }

        return size;
    }

    /**
     * Insert a new entry into the data list and refresh the list
     *
     * @param entry the entry to add to the data list
     */
    public void insertNewEntry(T entry)
    {
        this.dataList.add(entry);
        // TODO: CANT GET IT TO RECOGNISE ONLY THE INSERTED ON, SO JUST UPDATE ALL FOR NOW
        this.notifyDataSetChanged();
    }

    /**
     * Update an entry in the data list and refresh the list
     *
     * @param index the index of the entry being updated
     * @param entry the entry being updated
     */
    public void updateEntry(int index, T entry)
    {
        this.dataList.set(index, entry);
        this.notifyItemChanged(index);
    }

    /**
     * Delete an entry in the data list and refresh the list
     *
     * @param index the index of the entry being deleted
     */
    public void deleteEntry(int index)
    {
        this.dataList.remove(index);
        // TODO: CANT GET IT TO RECOGNISE ONLY THE INSERTED ON, SO JUST UPDATE ALL FOR NOW
        this.notifyDataSetChanged();
    }
}