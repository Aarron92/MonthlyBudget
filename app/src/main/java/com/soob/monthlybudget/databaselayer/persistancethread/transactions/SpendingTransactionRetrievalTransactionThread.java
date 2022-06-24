package com.soob.monthlybudget.databaselayer.persistancethread.transactions;

import android.content.Context;
import android.widget.Toast;

import com.soob.monthlybudget.databaselayer.database.DatabaseClient;
import com.soob.monthlybudget.databaselayer.entities.SpendingTransaction;
import com.soob.monthlybudget.databaselayer.persistancethread.DatabaseRetrievalTransactionThread;
import com.soob.monthlybudget.enums.DatabaseUpdateType;

import java.util.ArrayList;
import java.util.List;

/**
 * Job to retrieve all Transactions from the database in a background thread
 */
public class SpendingTransactionRetrievalTransactionThread extends DatabaseRetrievalTransactionThread<List<SpendingTransaction>>
{
    private final DatabaseUpdateType updateType;

    public SpendingTransactionRetrievalTransactionThread(Context context, DatabaseUpdateType updateType)
    {
        super(context);
        this.updateType = updateType;
    }

    /**
     * What to do before saving the transaction entry - in this case nothing
     */
    @Override
    public void onPreExecute()
    {
        // do nothing
    }

    @Override
    public List<SpendingTransaction> doInBackground()
    {
        List<SpendingTransaction> transactions = new ArrayList<>();

        switch (this.updateType)
        {
            // TODO: NOT IMPLEMENTED
//            case GET:
//                DatabaseClient.getInstance(this.context).getBudgetDatabase()
//                        .getAccountDao().getAccount();
//                break;
            case GET_ALL:
                transactions = DatabaseClient.getInstance(this.context).getBudgetDatabase()
                        .getSpendingTransactionsDao().getAllSpendingTransactions();
                break;
            default:
                Toast toast = Toast.makeText(this.context,
                        String.format("Uh oh, something went wrong when trying to %s Transaction(s)", this.updateType.getKey()),
                        Toast.LENGTH_SHORT);
                toast.show();
                break;
        }

        return transactions;
    }

    /**
     * What to do after saving the transaction entry - in this case, nothing is needed
     */
    @Override
    public void onPostExecute()
    {
        // do nothing
    }
}
