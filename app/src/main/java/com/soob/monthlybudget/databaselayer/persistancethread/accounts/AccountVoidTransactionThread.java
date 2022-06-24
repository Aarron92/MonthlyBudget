package com.soob.monthlybudget.databaselayer.persistancethread.accounts;

import android.content.Context;
import android.widget.Toast;

import com.soob.monthlybudget.databaselayer.database.DatabaseClient;
import com.soob.monthlybudget.databaselayer.entities.Account;
import com.soob.monthlybudget.databaselayer.persistancethread.DatabaseVoidTransactionThread;
import com.soob.monthlybudget.enums.DatabaseUpdateType;

/**
 * Thread job to perform an update on the database where there is no returned object expected
 */
public class AccountVoidTransactionThread extends DatabaseVoidTransactionThread
{
    private final Account account;

    private final DatabaseUpdateType updateType;

    public AccountVoidTransactionThread(Context context, Account account, DatabaseUpdateType updateType)
    {
        super(context);
        this.account = account;
        this.updateType = updateType;
    }

    /**
     * What to do before saving the account - in this case nothing
     */
    @Override
    public void onPreExecute()
    {
        // do nothing
    }

    /**
     * The database transaction to carry out - the update done depends on the type specified
     */
    @Override
    public void doInBackground()
    {
        switch (this.updateType)
        {
            case ADD:
                DatabaseClient.getInstance(this.context).getBudgetDatabase()
                        .getAccountDao().insertAccount(this.account);
                break;
            case UPDATE:
                DatabaseClient.getInstance(this.context).getBudgetDatabase()
                        .getAccountDao().updateAccount(this.account);
                break;
            case DELETE:
                DatabaseClient.getInstance(this.context).getBudgetDatabase()
                        .getAccountDao().deleteAccount(this.account);
                break;
            default:
                Toast toast = Toast.makeText(this.context,
                        String.format("Uh oh, something went wrong when trying to %s Account(s)", this.updateType.getKey()),
                        Toast.LENGTH_SHORT);
                toast.show();
                break;
        }
    }

    /**
     * What to do after saving the account - in this case, nothing is needed as the data set of the
     * RecyclerView needs to be updated from the main UI thread
     */
    @Override
    public void onPostExecute()
    {
        // do nothing
    }
}
