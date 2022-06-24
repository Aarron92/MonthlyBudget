package com.soob.monthlybudget.ui.outgoings;

import android.app.Dialog;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.soob.monthlybudget.R;
import com.soob.monthlybudget.adapters.OutgoingListAdapter;
import com.soob.monthlybudget.adapters.abstracts.DataListAdapter;
import com.soob.monthlybudget.databaselayer.entities.Outgoing;
import com.soob.monthlybudget.databaselayer.persistancethread.outgoings.OutgoingRetrievalTransactionThread;
import com.soob.monthlybudget.databaselayer.persistancethread.outgoings.OutgoingVoidTransactionThread;
import com.soob.monthlybudget.enums.DatabaseUpdateType;
import com.soob.monthlybudget.ui.common.FragmentLayout;
import com.soob.monthlybudget.ui.common.GenericFragment;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/**
 * Fragment for the individual Outgoing objects displayed on the UI. The accounts are populated with
 * data pulled from the database
 */
public class OutgoingsFragment extends GenericFragment<Outgoing, OutgoingListAdapter.OutgoingsViewHolder> implements FragmentLayout
{
    public OutgoingsFragment()
    {
        super();

        // called here because can't reference this until super call has completed
        this.setFragmentLayout(this);
    }

    @Override
    public int getContentView()
    {
        return R.layout.dialog_outgoing;
    }

    @Override
    public int getTitleResource()
    {
        return R.id.dialog_outgoing_title;
    }

    @Override
    public int getAddTitleText()
    {
        return R.string.add_outgoing_modal_title;
    }

    @Override
    public int getViewTitleText()
    {
        return R.string.view_outgoing_modal_title;
    }

    @Override
    public int getEditTitleText()
    {
        return R.string.edit_outgoing_modal_title;
    }

    @Override
    public int getEditAndSaveButton()
    {
        return R.id.dialog_outgoing_edit_and_save_button;
    }

    @Override
    public int getCancelButton()
    {
        return R.id.dialog_outgoing_cancel_button;
    }

    @Override
    public int getDeleteButton()
    {
        return R.id.dialog_outgoing_delete_button;
    }

    @Override
    public int getAddButtonText()
    {
        return R.string.add_outgoing_button_text;
    }

    @Override
    public int getEditButtonText()
    {
        return R.string.edit_outgoing_button_text;
    }

    @Override
    public int getSaveButtonText()
    {
        return R.string.save_outgoing_button_text;
    }

    @Override
    public int getCancelButtonText()
    {
        return R.string.cancel_outgoing_button_text;
    }

    @Override
    public int getAccountDropdown()
    {
        return R.id.dialog_outgoing_account_ref_dropdown_list;
    }

    @Override
    public Map<String, View> getEditableFields(Dialog dialog)
    {
        // TODO: NEED TO ADD ACCOUNT REF

        Map<String, View> editableFields = new HashMap<>();

        // set the various field components
        EditText outgoingName = dialog.findViewById(R.id.dialog_outgoing_name_editable_field);
        EditText outgoingAmount = dialog.findViewById(R.id.dialog_outgoing_amount_editable_field);
        EditText outgoingDate = dialog.findViewById(R.id.dialog_outgoing_date_editable_field);
        Spinner outgoingAccountRef = dialog.findViewById(R.id.dialog_outgoing_account_ref_dropdown_list);
        EditText outgoingComment = dialog.findViewById(R.id.dialog_outgoing_comment_editable_field);

        editableFields.put("name", outgoingName);
        editableFields.put("amount", outgoingAmount);
        editableFields.put("date", outgoingDate);
        editableFields.put("accountRef", outgoingAccountRef);
        editableFields.put("comment", outgoingComment);

        return editableFields;
    }

    @Override
    protected DataListAdapter<Outgoing, OutgoingListAdapter.OutgoingsViewHolder> createDataListAdapter(List<Outgoing> dataList)
    {
        return new OutgoingListAdapter(this.getContext(), dataList, this,
                R.layout.layout_outgoings_list, R.id.layout_outgoings_list_card_view);
    }

    @Override
    protected List<Outgoing> getAllDataFromDb()
    {
        OutgoingRetrievalTransactionThread transaction =
                new OutgoingRetrievalTransactionThread(this.getContext(), DatabaseUpdateType.GET_ALL);
        return transaction.execute();
    }

    @Override
    protected void prepopulateDialogDetails(Outgoing existingEntry, Dialog dialog)
    {
        // set the various field components
        EditText outgoingName = dialog.findViewById(R.id.dialog_outgoing_name_editable_field);
        EditText outgoingAmount = dialog.findViewById(R.id.dialog_outgoing_amount_editable_field);
        EditText outgoingDate = dialog.findViewById(R.id.dialog_outgoing_date_editable_field);
        EditText outgoingAccountRefText = dialog.findViewById(R.id.dialog_outgoing_account_ref_editable_field);
        Spinner outgoingAccountRefSpinner = dialog.findViewById(R.id.dialog_outgoing_account_ref_dropdown_list);
        EditText outgoingComment = dialog.findViewById(R.id.dialog_outgoing_comment_editable_field);

        // swap out the account reference dropdown list with a text field showing the current info
        outgoingAccountRefSpinner.setVisibility(View.GONE);
        outgoingAccountRefText.setVisibility(View.VISIBLE);

        // pre-populate the dialog details with the existing Outgoing details but make the fields
        // non-editable until in edit mode
        outgoingName.setText(existingEntry.getName());
        String formattedAmount = String.format(Locale.ENGLISH, "%.2f", existingEntry.getAmount());
        outgoingAmount.setText(formattedAmount);
        outgoingDate.setText(existingEntry.getDate());
        outgoingAccountRefText.setText(existingEntry.getAccountRef());
        outgoingComment.setText(existingEntry.getComment());

        // disable the fields for view mode
        outgoingName.setEnabled(false);
        outgoingAmount.setEnabled(false);
        outgoingDate.setEnabled(false);
        outgoingAccountRefText.setEnabled(false);
        outgoingComment.setEnabled(false);
    }

    @Override
    protected void enableEditableFields(Dialog dialog)
    {
        // get the various field components
        EditText outgoingName = dialog.findViewById(R.id.dialog_outgoing_name_editable_field);
        EditText outgoingAmount = dialog.findViewById(R.id.dialog_outgoing_amount_editable_field);
        EditText outgoingDate = dialog.findViewById(R.id.dialog_outgoing_date_editable_field);
        EditText outgoingAccountRefText = dialog.findViewById(R.id.dialog_outgoing_account_ref_editable_field);
        Spinner outgoingAccountRefSpinner = dialog.findViewById(R.id.dialog_outgoing_account_ref_dropdown_list);
        EditText outgoingComment = dialog.findViewById(R.id.dialog_outgoing_comment_editable_field);

        // swap out the account reference dropdown list with a text field so that it can be edited
        outgoingAccountRefSpinner.setVisibility(View.VISIBLE);
        outgoingAccountRefText.setVisibility(View.GONE);

        // set the current selection to to the right one in the account dropdown
        outgoingAccountRefSpinner.setSelection(0);

        // disable the fields for view mode
        outgoingName.setEnabled(true);
        outgoingAmount.setEnabled(true);
        outgoingDate.setEnabled(true);
        outgoingAccountRefSpinner.setEnabled(true);
        outgoingComment.setEnabled(true);
    }

    @Override
    protected boolean checkDetailsOfFields(Map<String, View> editableFields)
    {
        boolean canCloseDialog = false;

        // date, account ref and comment can be empty so only need to check the name and the amount
        EditText outgoingName = (EditText) editableFields.get("name");
        EditText outgoingAmount = (EditText) editableFields.get("amount");

        String name = Objects.requireNonNull(outgoingName).getText().toString();
        String amount = Objects.requireNonNull(outgoingAmount).getText().toString();

        // if the required fields are not populated then show an error
        if(name.isEmpty())
        {
            outgoingName.setError("The new outgoing entry must have a name");
            outgoingName.requestFocus();
        }
        else if(amount.isEmpty())
        {
            outgoingAmount.setError("The new outgoing entry must have an amount of money added");
            outgoingAmount.requestFocus();
        }
        else if(!amount.matches(MONEY_FORMAT_REGEX))
        {
            outgoingAmount.setError("The amount of money you are trying to add is in the wrong format");
            outgoingAmount.requestFocus();
        }
        else
        {
            canCloseDialog = true;
        }

        return canCloseDialog;
    }

    @Override
    protected void saveNewEntry(Map<String, View> editableFields)
    {
        String outgoingName = Objects.requireNonNull((EditText)editableFields.get("name")).getText().toString();
        String outgoingAmount = Objects.requireNonNull((EditText)editableFields.get("amount")).getText().toString();
        String outgoingDate = Objects.requireNonNull((EditText)editableFields.get("date")).getText().toString();
        String outgoingAccountRef = Objects.requireNonNull((Spinner)editableFields.get("accountRef")).getSelectedItem().toString();
        String outgoingComment = Objects.requireNonNull((EditText)editableFields.get("comment")).getText().toString();

        Outgoing outgoing = new Outgoing();

        // name and account are required so set them
        outgoing.setName(outgoingName);
        outgoing.setAmount(Double.parseDouble(outgoingAmount));
        outgoing.setAmount(Double.parseDouble(outgoingAmount));

        // comment is not required so only set it if needed
        // date, account ref and comment are not required so only set if needed
        if(!outgoingDate.isEmpty())
        {
            outgoing.setDate(outgoingDate);
        }
        if(!outgoingAccountRef.isEmpty())
        {
            outgoing.setAccountRef(outgoingAccountRef);
        }
        if(!outgoingComment.isEmpty())
        {
            outgoing.setComment(outgoingComment);
        }
        OutgoingVoidTransactionThread transaction =
                new OutgoingVoidTransactionThread(this.getContext(), outgoing, DatabaseUpdateType.ADD);
        transaction.execute();

        // update the data set with the newly added account - needs to be done on the main UI thread
        // rather than the thread that does the data insertion
        this.dataListAdapter.insertNewEntry(outgoing);

        // scroll to the newly added outgoing
        this.recyclerView.scrollToPosition(this.dataListAdapter.getItemCount() - 1);
    }

    @Override
    protected void updateExistingEntry(Outgoing existingEntry, int index, Map<String, View> fieldsToUpdate)
    {
        Outgoing updatedOutgoing = existingEntry;

        String outgoingName = Objects.requireNonNull((EditText)fieldsToUpdate.get("name")).getText().toString();
        String outgoingAmount = Objects.requireNonNull((EditText)fieldsToUpdate.get("amount")).getText().toString();
        String outgoingDate = Objects.requireNonNull((EditText)fieldsToUpdate.get("date")).getText().toString();
        String outgoingAccountRef = Objects.requireNonNull((Spinner)fieldsToUpdate.get("accountRef")).getSelectedItem().toString();
        String outgoingComment = Objects.requireNonNull((EditText)fieldsToUpdate.get("comment")).getText().toString();

        // name and amount are required so set them
        updatedOutgoing.setName(outgoingName);
        updatedOutgoing.setAmount(Double.parseDouble(outgoingAmount));

        // date, account ref and comment are not required so only set if needed
        if(!outgoingDate.isEmpty())
        {
            updatedOutgoing.setDate(outgoingDate);
        }
        if(!outgoingAccountRef.isEmpty())
        {
            updatedOutgoing.setAccountRef(outgoingAccountRef);
        }
        if(!outgoingComment.isEmpty())
        {
            updatedOutgoing.setComment(outgoingComment);
        }

        OutgoingVoidTransactionThread transaction =
                new OutgoingVoidTransactionThread(this.getContext(), updatedOutgoing, DatabaseUpdateType.UPDATE);
        transaction.execute();

        // update the data set with the edited outgoing - needs to be done on the main UI thread
        // rather than the thread that does the data insertion
        this.dataListAdapter.updateEntry(index, updatedOutgoing);
    }

    protected void deleteEntry(Outgoing entry, int index)
    {
        OutgoingVoidTransactionThread transaction =
                new OutgoingVoidTransactionThread(this.getContext(), entry, DatabaseUpdateType.DELETE);
        transaction.execute();

        // update the data set to remove the delete outgoing - needs to be done on the main UI thread
        // rather than the thread that does the data deletion
        this.dataListAdapter.deleteEntry(index);
    }
}