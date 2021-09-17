package com.ss.android.ugc.bytex.example;

import android.os.AsyncTask;
import android.os.HandlerThread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author archko 2021/9/17:14:21
 */
public class ThreadTest {

    public static void test() {
    }

    public void testThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("testThread ");
            }
        }).start();
    }

    public void testTask() {
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... strings) {
                System.out.println("doInBackground");
                return "doInBackground";
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                System.out.println("onPostExecute");
            }
        }.execute("param");
    }

    public void handlerThread() {
        HandlerThread handlerThread = new HandlerThread("handlerThread");
        handlerThread.start();
    }

    public void execute() {
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("execute runnable");
            }
        });
    }
}
