package com.soob.monthlybudget.databaselayer.persistancethread;

import android.content.Context;
import android.widget.Toast;

/**
 * Abstract class to run a database transaction that inserts something into the database in
 * a background thread
 */
public abstract class DatabaseVoidTransactionThread
{
    public Context context;

    /**
     * Constructor
     */
    public DatabaseVoidTransactionThread(Context context)
    {
        this.context = context;
    }

    /**
     * Method that kicks off the transaction
     */
    public void execute()
    {
        startJob();
    }

    /**
     * Task to run in the background where there is no return object. Uses standard Thread and
     * Runnable implementation since there is nothing to return
     */
    private void startJob()
    {
        // do anything that is needed before the transaction runs - e.g display loading wheel
        onPreExecute();

        Thread thread = new Thread(() ->
        {
            // perform the background task
            doInBackground();

        });

        thread.start();

        // wait for the thread to finish after the insert so the recycler view adapter can be
        // updated properly
        try
        {
           thread.join();
        }
        catch (InterruptedException exc)
        {
            Toast toast = Toast.makeText(this.context,
                    "Uh oh, something went wrong waiting for the database insert thread",
                    Toast.LENGTH_SHORT);
            toast.show();
        }

        // do anything that is needed after the transaction has finished - e.g remove loading wheel
        onPostExecute();
    }

    /**
     * What to do before running the transaction - implemented as needed by calling fragment
     */
    public abstract void onPreExecute();

    /**
     * What to do during the transaction  when no returned data is expected - implemented
     * as needed by calling fragment
     */
    public abstract void doInBackground();

    /**
     * What to do after running the transaction  - implemented as needed by calling fragment
     */
    public abstract void onPostExecute();
}
