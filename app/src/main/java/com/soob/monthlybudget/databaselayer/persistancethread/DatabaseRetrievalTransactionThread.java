package com.soob.monthlybudget.databaselayer.persistancethread;

import android.content.Context;
import android.widget.Toast;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Abstract class to run a database transaction that retrieves something from the database in
 * a background thread
 *
 * T = type of entry e.g. Account
 */
public abstract class DatabaseRetrievalTransactionThread<T>
{
    public Context context;

    /**
     * Constructor
     */
    public DatabaseRetrievalTransactionThread(Context context)
    {
        this.context = context;
    }

    /**
     * Method that kicks off the transaction
     */
    public T execute()
    {
        return startJob();
    }

    /**
     * Task to run in the background where there is a return object. Uses an ExecutorService to
     * run the thread so that objects can be returned
     */
    private T startJob()
    {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        // do anything that is needed before the background task runs - e.g display loading wheel
        onPreExecute();

        // rather than using a Thread, use a Callable so that we can actually return something
        Callable<T> callable = () ->
        {
            // perform the background task
            T returnObj = doInBackground();

            return returnObj;
        };

        // submit the Callable to the ExecutorService so that it runs and we can get the result
        Future<T> future = executorService.submit(callable);
        T result = null;
        try
        {
            result = future.get();
        }
        catch (Exception exc)
        {
            Toast toast = Toast.makeText(this.context,
                    "Uh oh, something went wrong when trying to insert data into the database",
                    Toast.LENGTH_SHORT);
            toast.show();
        }
        finally
        {
            // remember to shut the service down to make sure tasks are run and then resources are freed
            executorService.shutdown();
        }

        return result;
    }

    /**
     * What to do before running the transaction - implemented as needed by calling fragment
     */
    public abstract void onPreExecute();

    /**
     * What to do during the transaction- implemented as needed by calling fragment
     *
     * @return object to be returned from the transaction
     */
    public abstract T doInBackground();

    /**
     * What to do after running the transaction  - implemented as needed by calling fragment
     */
    public abstract void onPostExecute();
}
