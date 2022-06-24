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
 * Job to retrieve specific fields of Accounts from the database in a background thread
 */
public class AccountFieldRetrievalTransactionThread extends DatabaseRetrievalTransactionThread<List<String>>
{
    private final DatabaseUpdateType updateType;

    public AccountFieldRetrievalTransactionThread(Context context, DatabaseUpdateType updateType)
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
    public List<String> doInBackground()
    {
        List<String> names = new ArrayList<>();

        switch (this.updateType)
        {
            case GET_ALL:
                names = DatabaseClient.getInstance(this.context).getBudgetDatabase()
                        .getAccountDao().getAllAccountNames();
                break;
            default:
                Toast toast = Toast.makeText(this.context,
                        String.format("Uh oh, something went wrong when trying to %s Account(s)", this.updateType.getKey()),
                        Toast.LENGTH_SHORT);
                toast.show();
                break;
        }

        return names;
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
