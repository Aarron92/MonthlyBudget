package com.soob.monthlybudget.databaselayer.persistancethread.outgoings;

import android.content.Context;
import android.widget.Toast;

import com.soob.monthlybudget.databaselayer.database.DatabaseClient;
import com.soob.monthlybudget.databaselayer.entities.Account;
import com.soob.monthlybudget.databaselayer.entities.Outgoing;
import com.soob.monthlybudget.databaselayer.persistancethread.DatabaseRetrievalTransactionThread;
import com.soob.monthlybudget.enums.DatabaseUpdateType;

import java.util.ArrayList;
import java.util.List;

/**
 * Job to retrieve all Outgoings from the database in a background thread
 */
public class OutgoingRetrievalTransactionThread extends DatabaseRetrievalTransactionThread<List<Outgoing>>
{
    private final DatabaseUpdateType updateType;

    public OutgoingRetrievalTransactionThread(Context context, DatabaseUpdateType updateType)
    {
        super(context);
        this.updateType = updateType;
    }

    /**
     * What to do before saving the outgoing entry - in this case nothing
     */
    @Override
    public void onPreExecute()
    {
        // do nothing
    }

    @Override
    public List<Outgoing> doInBackground()
    {
        List<Outgoing> outgoings = new ArrayList<>();

        switch (this.updateType)
        {
            // TODO: NOT IMPLEMENTED
//            case GET:
//                DatabaseClient.getInstance(this.context).getBudgetDatabase()
//                        .getAccountDao().getAccount();
//                break;
            case GET_ALL:
                outgoings = DatabaseClient.getInstance(this.context).getBudgetDatabase()
                        .getOutgoingsDao().getAllOutgoings();
                break;
            default:
                Toast toast = Toast.makeText(this.context,
                        String.format("Uh oh, something went wrong when trying to %s Outgoing(s)", this.updateType.getKey()),
                        Toast.LENGTH_SHORT);
                toast.show();
                break;
        }

        return outgoings;
    }

    /**
     * What to do after saving the outgoing entry - in this case, nothing is needed
     */
    @Override
    public void onPostExecute()
    {
        // do nothing
    }
}
