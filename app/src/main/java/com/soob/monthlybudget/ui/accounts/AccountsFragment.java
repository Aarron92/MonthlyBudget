package com.soob.monthlybudget.ui.accounts;

import android.app.Dialog;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.soob.monthlybudget.R;
import com.soob.monthlybudget.adapters.AccountsListAdapter;
import com.soob.monthlybudget.adapters.abstracts.DataListAdapter;
import com.soob.monthlybudget.databaselayer.entities.Account;
import com.soob.monthlybudget.databaselayer.persistancethread.accounts.AccountRetrievalTransactionThread;
import com.soob.monthlybudget.databaselayer.persistancethread.accounts.AccountVoidTransactionThread;
import com.soob.monthlybudget.enums.DatabaseUpdateType;
import com.soob.monthlybudget.ui.common.FragmentLayout;
import com.soob.monthlybudget.ui.common.GenericFragment;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/**
 * Fragment for the individual Account objects displayed on the UI. The accounts are populated with
 * data pulled from the database
 */
public class AccountsFragment extends GenericFragment<Account, AccountsListAdapter.AccountsViewHolder> implements FragmentLayout
{
    public AccountsFragment()
    {
        super();

        // called here because can't reference this until super call has completed
        this.setFragmentLayout(this);
    }

    @Override
    public int getContentView()
    {
        return R.layout.dialog_account;
    }

    @Override
    public int getTitleResource()
    {
        return R.id.dialog_account_title;
    }

    @Override
    public int getAddTitleText()
    {
        return R.string.add_account_modal_title;
    }

    @Override
    public int getViewTitleText()
    {
        return R.string.view_account_modal_title;
    }

    @Override
    public int getEditTitleText()
    {
        return R.string.edit_account_modal_title;
    }

    @Override
    public int getEditAndSaveButton()
    {
        return R.id.dialog_account_edit_and_save_button;
    }

    @Override
    public int getCancelButton()
    {
        return R.id.dialog_account_cancel_button;
    }

    @Override
    public int getDeleteButton()
    {
        return R.id.dialog_account_delete_button;
    }

    @Override
    public int getAddButtonText()
    {
        return R.string.add_account_button_text;
    }

    @Override
    public int getEditButtonText()
    {
        return R.string.edit_account_button_text;
    }

    @Override
    public int getSaveButtonText()
    {
        return R.string.save_account_button_text;
    }

    @Override
    public int getCancelButtonText()
    {
        return R.string.cancel_account_button_text;
    }

    @Override
    public int getAccountDropdown()
    {
        // TODO: DOESN'T HAVE ONE BUT NEED TO UPDATE THE DESIGN OF THE GENERIC FRAGMENT CLASS TO
        // REMOVE IT
        return R.id.dialog_outgoing_account_ref_dropdown_list;
    }

    @Override
    public Map<String, View> getEditableFields(Dialog dialog)
    {
        Map<String, View> editTextFields = new HashMap<>();

        // set the various field components
        EditText accountName = dialog.findViewById(R.id.dialog_account_name_editable_field);
        EditText accountAmount = dialog.findViewById(R.id.dialog_account_amount_editable_field);
        EditText accountComment = dialog.findViewById(R.id.dialog_account_comment_editable_field);

        editTextFields.put("name", accountName);
        editTextFields.put("amount", accountAmount);
        editTextFields.put("comment", accountComment);

        return editTextFields;
    }

    @Override
    protected DataListAdapter<Account, AccountsListAdapter.AccountsViewHolder> createDataListAdapter(List<Account> dataList)
    {
        return new AccountsListAdapter(this.getContext(), dataList, this,
                R.layout.layout_accounts_list, R.id.layout_accounts_list_card_view);
    }

    @Override
    protected List<Account> getAllDataFromDb()
    {
        AccountRetrievalTransactionThread transaction =
                new AccountRetrievalTransactionThread(this.getContext(), DatabaseUpdateType.GET_ALL);
        return transaction.execute();
    }

    @Override
    protected void prepopulateDialogDetails(Account existingEntry, Dialog dialog)
    {
        // set the various field components
        EditText accountName = dialog.findViewById(R.id.dialog_account_name_editable_field);
        EditText accountAmount = dialog.findViewById(R.id.dialog_account_amount_editable_field);
        EditText accountComment = dialog.findViewById(R.id.dialog_account_comment_editable_field);

        // pre-populate the dialog details with the existing Account details but make the fields
        // non-editable until in edit mode
        accountName.setText(existingEntry.getName());
        String formattedAmount = String.format(Locale.ENGLISH, "%.2f", existingEntry.getAmount());
        accountAmount.setText(formattedAmount);
        accountComment.setText(existingEntry.getComment());

        // disable the fields for view mode
        accountName.setEnabled(false);
        accountAmount.setEnabled(false);
        accountComment.setEnabled(false);
    }

    @Override
    protected void enableEditableFields(Dialog dialog)
    {
        // set the various field components
        EditText accountName = dialog.findViewById(R.id.dialog_account_name_editable_field);
        EditText accountAmount = dialog.findViewById(R.id.dialog_account_amount_editable_field);
        EditText accountComment = dialog.findViewById(R.id.dialog_account_comment_editable_field);

        // disable the fields for view mode
        accountName.setEnabled(true);
        accountAmount.setEnabled(true);
        accountComment.setEnabled(true);
    }

    @Override
    protected boolean checkDetailsOfFields(Map<String, View> editableFields)
    {
        boolean canCloseDialog = false;

        // comment can be empty so only need to check the name and the amount
        EditText accountName = (EditText) editableFields.get("name");
        EditText accountAmount = (EditText) editableFields.get("amount");

        String name = Objects.requireNonNull(accountName).getText().toString();
        String amount = Objects.requireNonNull(accountAmount).getText().toString();

        // if the required fields are not populated then show an error
        if(name.isEmpty())
        {
            accountName.setError("The new account must have a name");
            accountName.requestFocus();
        }
        else if(amount.isEmpty())
        {
            accountAmount.setError("The new account must have an amount of money added");
            accountAmount.requestFocus();
        }
        else if(!amount.matches(MONEY_FORMAT_REGEX))
        {
            accountAmount.setError("The amount of money you are trying to add is in the wrong format");
            accountAmount.requestFocus();
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
        String accountName = Objects.requireNonNull((EditText)editableFields.get("name")).getText().toString();
        String accountAmount = Objects.requireNonNull((EditText)editableFields.get("amount")).getText().toString();
        String accountComment = Objects.requireNonNull((EditText)editableFields.get("comment")).getText().toString();

        Account account = new Account();

        // name and account are required so set them
        account.setName(accountName);
        account.setAmount(Double.parseDouble(accountAmount));

        // comment is not required so only set it if needed
        if(!accountComment.isEmpty())
        {
            account.setComment(accountComment);
        }
        // account.setType(); TODO: NOT YET ON THE UI

        AccountVoidTransactionThread transaction =
                new AccountVoidTransactionThread(this.getContext(), account, DatabaseUpdateType.ADD);
        transaction.execute();

        // update the data set with the newly added account - needs to be done on the main UI thread
        // rather than the thread that does the data insertion
        this.dataListAdapter.insertNewEntry(account);

        // scroll to the newly added account
        this.recyclerView.scrollToPosition(this.dataListAdapter.getItemCount() - 1);
    }

    @Override
    protected void updateExistingEntry(Account existingEntry, int index, Map<String, View> fieldsToUpdate)
    {
        Account updatedAccount = existingEntry;

        String accountName = Objects.requireNonNull((EditText)fieldsToUpdate.get("name")).getText().toString();
        String accountAmount = Objects.requireNonNull((EditText)fieldsToUpdate.get("amount")).getText().toString();
        String accountComment = Objects.requireNonNull((EditText)fieldsToUpdate.get("comment")).getText().toString();

        // name and account are required so set them
        updatedAccount.setName(accountName);
        updatedAccount.setAmount(Double.parseDouble(accountAmount));

        // comment is not required so only set it if needed
        if(!accountComment.isEmpty())
        {
            updatedAccount.setComment(accountComment);
        }
        // account.setType(); TODO: NOT YET ON THE UI

        AccountVoidTransactionThread transaction =
                new AccountVoidTransactionThread(this.getContext(), updatedAccount, DatabaseUpdateType.UPDATE);
        transaction.execute();

        // update the data set with the edited account - needs to be done on the main UI thread
        // rather than the thread that does the data insertion
        this.dataListAdapter.updateEntry(index, updatedAccount);
    }

    protected void deleteEntry(Account entry, int index)
    {
        AccountVoidTransactionThread transaction =
                new AccountVoidTransactionThread(this.getContext(), entry, DatabaseUpdateType.DELETE);
        transaction.execute();

        // update the data set to remove the delete account - needs to be done on the main UI thread
        // rather than the thread that does the data deletion
        this.dataListAdapter.deleteEntry(index);
    }
}