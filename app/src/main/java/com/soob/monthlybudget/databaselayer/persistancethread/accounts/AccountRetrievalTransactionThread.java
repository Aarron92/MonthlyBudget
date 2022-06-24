package com.soob.monthlybudget.databaselayer.persistancethread.accounts;

import android.content.Context;
import android.widget.Toast;

import com.soob.monthlybudget.databaselayer.database.DatabaseClient;
import com.soob.monthlybudget.databaselayer.entities.Account;
import com.soob.monthlybudget.databaselayer.persistancethread.DatabaseRetrievalTransactionThread;
import com.soob.monthlybudget.enums.DatabaseUpdateType;

import java.util.ArrayList;
import java.util.List;

/**
 * Job to retrieve all Accounts from the database in a background thread
 */
public class AccountRetrievalTransactionThread extends DatabaseRetrievalTransactionThread<List<Account>>
{
    private final DatabaseUpdateType updateType;

    public AccountRetrievalTransactionThread(Context context, DatabaseUpdateType updateType)
    {
        super(context);
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

    @Override
    public List<Account> doInBackground()
    {
        List<Account> accounts = new ArrayList<>();

        switch (this.updateType)
        {
            // TODO: NOT IMPLEMENTED
//            case GET:
//                DatabaseClient.getInstance(this.context).getBudgetDatabase()
//                        .getAccountDao().getAccount();
//                break;
            case GET_ALL:
                accounts = DatabaseClient.getInstance(this.context).getBudgetDatabase()
                        .getAccountDao().getAllAccounts();
                break;
            default:
                Toast toast = Toast.makeText(this.context,
                        String.format("Uh oh, something went wrong when trying to %s Account(s)", this.updateType.getKey()),
                        Toast.LENGTH_SHORT);
                toast.show();
                break;
        }

        return accounts;
    }

    /**
     * What to do after saving the account - in this case, nothing is needed
     */
    @Override
    public void onPostExecute()
    {
        // do nothing
    }
}
