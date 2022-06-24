package com.soob.monthlybudget.ui.common;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.soob.monthlybudget.R;
import com.soob.monthlybudget.adapters.abstracts.DataListAdapter;
import com.soob.monthlybudget.adapters.abstracts.DataListViewHolder;
import com.soob.monthlybudget.databaselayer.entities.Account;
import com.soob.monthlybudget.databaselayer.persistancethread.accounts.AccountFieldRetrievalTransactionThread;
import com.soob.monthlybudget.databaselayer.persistancethread.accounts.AccountRetrievalTransactionThread;
import com.soob.monthlybudget.databaselayer.persistancethread.outgoings.OutgoingRetrievalTransactionThread;
import com.soob.monthlybudget.enums.DatabaseUpdateType;
import com.soob.monthlybudget.inputlisteners.EntryClickListener;

import java.util.List;
import java.util.Map;

/**
 * Generic fragment that other fragments can subclass to do most of the repeated code in here
 *
 * T = data type e.g. Account
 * V = view holder in the data adapter
 */
public abstract class GenericFragment<T, V extends DataListViewHolder> extends Fragment implements EntryClickListener<T>
{
    /**
     * Interface defining the type of fragment this is, with the various UI bits and pieces
     */
    protected FragmentLayout fragmentLayout;

    /**
     * Regex for checking the value of amount trying to be added
     */
    protected static final String MONEY_FORMAT_REGEX = "^(([1-9]\\d{0,2}(,\\d{3})*)|(([1-9]\\d*)?\\d))(\\.\\d\\d)?$";

    /**
     * RecyclerView containing the various data entries
     */
    protected RecyclerView recyclerView;

    /**
     * Adapter that populates the RecyclerView with data
     */
    protected DataListAdapter<T, V> dataListAdapter;

    public GenericFragment()
    {
    }

    public void setFragmentLayout(FragmentLayout fragmentLayout)
    {
        this.fragmentLayout = fragmentLayout;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment - a card view inside a recyclerview
        View view = inflater.inflate(this.fragmentLayout.getLayout(), container, false);

        // get the entries already stored in the database
        List<T> dataList = getAllDataFromDb();

        // create the RecyclerView and adapter
        this.dataListAdapter = createDataListAdapter(dataList);

        this.recyclerView = view.findViewById(this.fragmentLayout.getRecyclerView());
        this.recyclerView.setHasFixedSize(true);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        this.recyclerView.setAdapter(this.dataListAdapter);

        // set up the add button to respond to clicks
        Button addButton = view.findViewById(this.fragmentLayout.getAddButton());
        addButton.setText(this.fragmentLayout.getAddButtonText());
        addButton.setOnClickListener(v ->
        {
            // open the modal that allows the user to add a new entry
            openNewEntryDialog();
        });

        return view;
    }

    /**
     * Create the relevant data list adapter for the fragment e.g. the Accounts fragment will create
     * an AccountsListAdapter
     *
     * @param dataList the list of data entries to add to the adapter
     * @return the new data list adapter
     */
    protected abstract DataListAdapter<T, V> createDataListAdapter(List<T> dataList);

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
    }

    /**
     * Get all of the data records in the database via the Room library DAO
     *
     * @return a list of all persisted data entries
     */
    protected abstract List<T> getAllDataFromDb();

    @Override
    public void onClick(T entry, int index)
    {
        // pass in the entry and index of it in the adapter so we know which one is being updated
        // and can display the existing data in the dialog to the user
        openViewAndEditEntryDialog(entry, index);
    }

    /**
     * Open the dialog for adding a new entry
     */
    protected void openNewEntryDialog()
    {
        // create the dialog from the XML layout
        EditableStateDialog dialog = new EditableStateDialog(this.requireContext(), false);
        dialog.setContentView(this.fragmentLayout.getContentView());

        // set the title to show whether it is to add a new entry or edit and existing one
        TextView title = dialog.findViewById(this.fragmentLayout.getTitleResource());
        title.setText(this.fragmentLayout.getAddTitleText());

        // populate the account drop down TODO - NEEDS TO BE UPDATED
        Spinner accountDropDown = dialog.findViewById(this.fragmentLayout.getAccountDropdown());
        if(accountDropDown != null)
        {
            populateAccountDropDown(accountDropDown);
        }

        // if the save button is clicked, save the new entry
        Button saveButton = dialog.findViewById(this.fragmentLayout.getEditAndSaveButton());
        saveButton.setText(this.fragmentLayout.getSaveButtonText());
        saveButton.setOnClickListener(view ->
                {
                    // save the new entry, refresh the data, then dismiss the dialog
                    boolean canCloseDialog = checkDetailsAndSaveNewEntry(this.fragmentLayout.getEditableFields(dialog));

                    if(canCloseDialog)
                    {
                        dialog.dismiss();
                    }
                }
        );

        // if cancel button is clicked, close the dialog
        Button cancelButton = dialog.findViewById(this.fragmentLayout.getCancelButton());
        cancelButton.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    /**
     * Open the dialog for viewing and editing a data entry e.g. an Account or Outgoing record
     * The dialog is opened in the view state
     */
    private void openViewAndEditEntryDialog(T entry, int index)
    {
        // create the dialog from the XML layout
        EditableStateDialog dialog = new EditableStateDialog(this.requireContext(), false);
        dialog.setContentView(this.fragmentLayout.getContentView());

        // set the title to show whether it is to add a new entry or edit and existing one
        TextView title = dialog.findViewById(this.fragmentLayout.getTitleResource());
        title.setText(this.fragmentLayout.getViewTitleText());

        // populate the various dialog fields
        prepopulateDialogDetails(entry, dialog);

        // button for editing and saving is re-used. if in view mode, then the the view will be
        // changed to edit view, then if the save button is clicked, the changes will be saved
        Button editOrSaveButton = dialog.findViewById(this.fragmentLayout.getEditAndSaveButton());
        editOrSaveButton.setOnClickListener(view ->
                {
                    boolean canCloseDialog = false;

                    if(!dialog.isEditState())
                    {
                        dialog.setIsEditState(true);
                        title.setText(this.fragmentLayout.getEditTitleText());
                        enableEditableFields(dialog);
                        editOrSaveButton.setText(this.fragmentLayout.getSaveButtonText());
                    }
                    else if(dialog.isEditState())
                    {
                        // save the new entry, refresh the data, then dismiss the dialog
                        canCloseDialog = checkDetailsAndUpdateExistingEntry(entry,
                                index, this.fragmentLayout.getEditableFields(dialog));
                    }

                    if(canCloseDialog)
                    {
                        dialog.dismiss();
                    }
                }
        );

        // if cancel button is clicked, close the dialog
        Button cancelButton = dialog.findViewById(this.fragmentLayout.getCancelButton());
        cancelButton.setOnClickListener(v -> dialog.dismiss());

        // re-enable the delete button from the view too then if clicked, delete the current entry
        Button deleteButton = dialog.findViewById(this.fragmentLayout.getDeleteButton());
        deleteButton.setVisibility(View.VISIBLE);
        deleteButton.setOnClickListener(view ->
                {
                    // delete the entry, refresh the data, then dismiss the dialog
                    deleteEntry(entry, index);
                    dialog.dismiss();
                }
        );

        dialog.show();
    }

    protected abstract void prepopulateDialogDetails(T entry, Dialog dialog);

    protected abstract void enableEditableFields(Dialog dialog);

    /**
     * Check that each field is populated as required - if there are any problems, highlight them
     * to the user, otherwise save the new entry
     *
     * Returns a boolean that controls whether the dialog can be closed or not. If an error occurred,
     * then the dialog needs to stay open
     *
     * @param editableFields list of editable fields to set
     * @return whether to close the dialog or not
     */
    private boolean checkDetailsAndSaveNewEntry(Map<String, View> editableFields)
    {
        boolean canCloseDialog = false;

        boolean noErrors = checkDetailsOfFields(editableFields);

        if(noErrors)
        {
            // if successful, save and let the call know that the dialog can be closed
            saveNewEntry(editableFields);
            canCloseDialog = true;
        }

        return canCloseDialog;
    }

    protected abstract void saveNewEntry(Map<String, View> editableFields);

    /**
     * Check the details meet the expected format

     */
    private boolean checkDetailsAndUpdateExistingEntry(T existingEntry, int index, Map<String, View> fieldsToUpdate)
    {
        boolean canCloseDialog = false;

        boolean noErrors = checkDetailsOfFields(fieldsToUpdate);

        if(noErrors)
        {
            // if successful, save and let the call know that the dialog can be closed
            updateExistingEntry(existingEntry, index, fieldsToUpdate);
            canCloseDialog = true;
        }

        return canCloseDialog;
    }

    protected abstract boolean checkDetailsOfFields(Map<String, View> editableFields);

    protected abstract void updateExistingEntry(T existingEntry, int index, Map<String, View> fieldsToUpdate);

    protected abstract void deleteEntry(T entry, int index);

    protected void populateAccountDropDown(Spinner dropdown)
    {
        // get the list of Accounts to populate the dropdown with
        AccountFieldRetrievalTransactionThread transaction =
                new AccountFieldRetrievalTransactionThread(this.getContext(), DatabaseUpdateType.GET_ALL);
        List<String> allAccountNames = transaction.execute();

        // create an adapter to describe how the items are displayed
        // the layout for the dropdown item is defined by Android
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(this.getContext(), android.R.layout.simple_spinner_dropdown_item, allAccountNames);

        // set the spinners adapter
        dropdown.setAdapter(adapter);
    }
}