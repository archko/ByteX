package com.ss.android.ugc.bytex.example;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.*;

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
                System.out.println(Thread.currentThread() + " testThread ");
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
        Handler workHandler = new Handler(handlerThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                System.out.println(Thread.currentThread() + " handleMessage:" + msg.what);
            }
        };
        workHandler.sendEmptyMessage(1);
    }

    public void execute() {
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread() + " execute runnable");
            }
        });

        executorService = new ThreadPoolExecutor(8, 32, 3000, TimeUnit.SECONDS, new LinkedBlockingQueue(128));
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread() + " ThreadPoolExecutor runnable");
            }
        });
    }

    public void testTimer() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread() + " testTimer runnable");
            }
        }, 5000L);
    }
}
