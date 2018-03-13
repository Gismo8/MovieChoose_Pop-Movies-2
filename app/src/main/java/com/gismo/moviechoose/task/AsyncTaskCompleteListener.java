package com.gismo.moviechoose.task;

/**
 * Created by gismo on 2018. 03. 13..
 */

public interface AsyncTaskCompleteListener<T>
{
    /**
     * Invoked when the AsyncTask has completed its execution.
     * @param result The resulting object from the AsyncTask.
     */
    public void onTaskComplete(T result);
}
