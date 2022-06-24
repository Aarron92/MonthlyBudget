package com.soob.monthlybudget.ui.transactions;

import android.app.Dialog;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.soob.monthlybudget.R;
import com.soob.monthlybudget.adapters.SpendingTransactionListAdapter;
import com.soob.monthlybudget.adapters.abstracts.DataListAdapter;
import com.soob.monthlybudget.databaselayer.entities.SpendingTransaction;
import com.soob.monthlybudget.databaselayer.persistancethread.transactions.SpendingTransactionRetrievalTransactionThread;
import com.soob.monthlybudget.databaselayer.persistancethread.transactions.SpendingTransactionVoidTransactionThread;
import com.soob.monthlybudget.enums.DatabaseUpdateType;
import com.soob.monthlybudget.ui.common.FragmentLayout;
import com.soob.monthlybudget.ui.common.GenericFragment;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/**
 * Fragment for the individual SpendingTransaction objects displayed on the UI. The accounts are populated with
 * data pulled from the database
 */
public class SpendingTransactionFragment extends GenericFragment<SpendingTransaction, SpendingTransactionListAdapter.SpendingTransactionViewHolder> implements FragmentLayout
{
    public SpendingTransactionFragment()
    {
        super();

        // called here because can't reference this until super call has completed
        this.setFragmentLayout(this);
    }

    @Override
    public int getContentView()
    {
        return R.layout.dialog_transaction;
    }

    @Override
    public int getTitleResource()
    {
        return R.id.dialog_transaction_title;
    }

    @Override
    public int getAddTitleText()
    {
        return R.string.add_transaction_modal_title;
    }

    @Override
    public int getViewTitleText()
    {
        return R.string.view_transaction_modal_title;
    }

    @Override
    public int getEditTitleText()
    {
        return R.string.edit_transaction_modal_title;
    }

    @Override
    public int getEditAndSaveButton()
    {
        return R.id.dialog_transaction_edit_and_save_button;
    }

    @Override
    public int getCancelButton()
    {
        return R.id.dialog_transaction_cancel_button;
    }

    @Override
    public int getDeleteButton()
    {
        return R.id.dialog_transaction_delete_button;
    }

    @Override
    public int getAddButtonText()
    {
        return R.string.add_transaction_button_text;
    }

    @Override
    public int getEditButtonText()
    {
        return R.string.edit_transaction_button_text;
    }

    @Override
    public int getSaveButtonText()
    {
        return R.string.save_transaction_button_text;
    }

    @Override
    public int getCancelButtonText()
    {
        return R.string.cancel_transaction_button_text;
    }

    @Override
    public int getAccountDropdown()
    {
        return R.id.dialog_outgoing_account_ref_dropdown_list;
    }

    @Override
    public Map<String, View> getEditableFields(Dialog dialog)
    {
        Map<String, View> editTextFields = new HashMap<>();

        // set the various field components
        EditText transactionAmount = dialog.findViewById(R.id.dialog_transaction_amount_editable_field);
        EditText transactionDate = dialog.findViewById(R.id.dialog_transaction_date_editable_field);
        EditText transactionComment = dialog.findViewById(R.id.dialog_transaction_comment_editable_field);
//        Spinn transactionAccountRef = dialog.findViewById(R.id.dialog_transaction_account_ref_editable_field);

        editTextFields.put("amount", transactionAmount);
        editTextFields.put("date", transactionDate);
        editTextFields.put("comment", transactionComment);
//        editTextFields.put("accountRef", transactionAccountRef);

        return editTextFields;
    }

    @Override
    protected DataListAdapter<SpendingTransaction, SpendingTransactionListAdapter.SpendingTransactionViewHolder> createDataListAdapter(List<SpendingTransaction> dataList)
    {
        return new SpendingTransactionListAdapter(this.getContext(), dataList, this,
                R.layout.layout_transactions_list, R.id.layout_transactions_list_card_view);
    }

    @Override
    protected List<SpendingTransaction> getAllDataFromDb()
    {
        SpendingTransactionRetrievalTransactionThread transaction =
                new SpendingTransactionRetrievalTransactionThread(this.getContext(), DatabaseUpdateType.GET_ALL);
        return transaction.execute();
    }

    @Override
    protected void prepopulateDialogDetails(SpendingTransaction existingEntry, Dialog dialog)
    {
        // set the various field components
        EditText transactionAmount = dialog.findViewById(R.id.dialog_transaction_amount_editable_field);
        EditText transactionDate = dialog.findViewById(R.id.dialog_transaction_date_editable_field);
        EditText transactionComment = dialog.findViewById(R.id.dialog_transaction_comment_editable_field);
        EditText transactionAccountRef = dialog.findViewById(R.id.dialog_transaction_account_ref_editable_field);

        // pre-populate the dialog details with the existing Transaction details but make the fields
        // non-editable until in edit mode
        String formattedAmount = String.format(Locale.ENGLISH, "%.2f", existingEntry.getAmount());
        transactionAmount.setText(formattedAmount);
        transactionDate.setText(existingEntry.getDate());
        transactionAccountRef.setText(existingEntry.getAccountRef());
        transactionComment.setText(existingEntry.getComment());

        // disable the fields for view mode
        transactionAmount.setEnabled(false);
        transactionDate.setEnabled(false);
        transactionAccountRef.setEnabled(false);
        transactionComment.setEnabled(false);
    }

    @Override
    protected void enableEditableFields(Dialog dialog)
    {
//        // get the various field components
//        EditText outgoingName = dialog.findViewById(R.id.dialog_outgoing_name_editable_field);
//        EditText outgoingAmount = dialog.findViewById(R.id.dialog_outgoing_amount_editable_field);
//        EditText outgoingDate = dialog.findViewById(R.id.dialog_outgoing_date_editable_field);
//        EditText outgoingAccountRefText = dialog.findViewById(R.id.dialog_outgoing_account_ref_editable_field);
//        Spinner outgoingAccountRefSpinner = dialog.findViewById(R.id.dialog_outgoing_account_ref_dropdown_list);
//        EditText outgoingComment = dialog.findViewById(R.id.dialog_outgoing_comment_editable_field);
//
//        // swap out the account reference dropdown list with a text field so that it can be edited
//        outgoingAccountRefSpinner.setVisibility(View.VISIBLE);
//        outgoingAccountRefText.setVisibility(View.GONE);
//
//        // set the current selection to to the right one in the account dropdown
//        outgoingAccountRefSpinner.setSelection(0);
//
//        // disable the fields for view mode
//        outgoingName.setEnabled(true);
//        outgoingAmount.setEnabled(true);
//        outgoingDate.setEnabled(true);
//        outgoingAccountRefSpinner.setEnabled(true);
//        outgoingComment.setEnabled(true);
    }

    @Override
    protected boolean checkDetailsOfFields(Map<String, View> editableFields)
    {
        boolean canCloseDialog = false;

        // only field that is required is the amount, so only need to check that one is populated
        EditText transactionAmount = (EditText) editableFields.get("amount");

        String amount = Objects.requireNonNull(transactionAmount).getText().toString();

        // if the required fields are not populated then show an error
        if(amount.isEmpty())
        {
            transactionAmount.setError("The new transaction entry must have an amount of money added");
            transactionAmount.requestFocus();
        }
        else if(!amount.matches(MONEY_FORMAT_REGEX))
        {
            transactionAmount.setError("The amount of money you are trying to add is in the wrong format");
            transactionAmount.requestFocus();
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
        String transactionAmount = Objects.requireNonNull((EditText)editableFields.get("amount")).getText().toString();
        String transactionDate = Objects.requireNonNull((EditText)editableFields.get("date")).getText().toString();
        String transactionComment = Objects.requireNonNull((EditText)editableFields.get("comment")).getText().toString();
        String transactionAccountRef = Objects.requireNonNull((Spinner)editableFields.get("accountRef")).getSelectedItem().toString();

        SpendingTransaction transaction = new SpendingTransaction();

        // amount is require so set
        transaction.setAmount(Double.parseDouble(transactionAmount));

        // other fields are not required so only set if provided
        if(!transactionDate.isEmpty())
        {
            transaction.setDate(transactionDate);
        }
        if(!transactionAccountRef.isEmpty())
        {
            transaction.setAccountRef(transactionAccountRef);
        }
        if(!transactionComment.isEmpty())
        {
            transaction.setComment(transactionComment);
        }

        SpendingTransactionVoidTransactionThread transactionThread =
                new SpendingTransactionVoidTransactionThread(this.getContext(), transaction, DatabaseUpdateType.ADD);
        transactionThread.execute();

        // update the data set with the newly added account - needs to be done on the main UI thread
        // rather than the thread that does the data insertion
        this.dataListAdapter.insertNewEntry(transaction);

        // scroll to the newly added transaction
        this.recyclerView.scrollToPosition(this.dataListAdapter.getItemCount() - 1);
    }

    @Override
    protected void updateExistingEntry(SpendingTransaction existingEntry, int index, Map<String, View> fieldsToUpdate)
    {
        SpendingTransaction updatedTransaction = existingEntry;

        String transactionAmount = Objects.requireNonNull((EditText)fieldsToUpdate.get("amount")).getText().toString();
        String transactionDate = Objects.requireNonNull((EditText)fieldsToUpdate.get("date")).getText().toString();
//        String transactionAccountRef = Objects.requireNonNull((EditText)fieldsToUpdate.get("accountRef")).getText().toString();
        String transactionComment = Objects.requireNonNull((EditText)fieldsToUpdate.get("comment")).getText().toString();

        // name and amount are required so set them
        updatedTransaction.setAmount(Double.parseDouble(transactionAmount));

        // date, account ref and comment are not required so only set if needed
        if(!transactionDate.isEmpty())
        {
            updatedTransaction.setDate(transactionDate);
        }
//        if(!transactionAccountRef.isEmpty())
//        {
//            updatedTransaction.setAccountRef(transactionAccountRef);
//        }
        if(!transactionComment.isEmpty())
        {
            updatedTransaction.setComment(transactionComment);
        }

        SpendingTransactionVoidTransactionThread transaction =
                new SpendingTransactionVoidTransactionThread(this.getContext(), updatedTransaction, DatabaseUpdateType.UPDATE);
        transaction.execute();

        // update the data set with the edited transaction - needs to be done on the main UI thread
        // rather than the thread that does the data insertion
        this.dataListAdapter.updateEntry(index, updatedTransaction);
    }

    protected void deleteEntry(SpendingTransaction entry, int index)
    {
        SpendingTransactionVoidTransactionThread transaction =
                new SpendingTransactionVoidTransactionThread(this.getContext(), entry, DatabaseUpdateType.DELETE);
        transaction.execute();

        // update the data set to remove the delete transaction - needs to be done on the main UI thread
        // rather than the thread that does the data deletion
        this.dataListAdapter.deleteEntry(index);
    }
}