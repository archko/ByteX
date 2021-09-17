package com.ss.android.ugc.bytex.example;

import android.os.AsyncTask;

/**
 * @author wushuyong 2021/9/17:14:21
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
}
