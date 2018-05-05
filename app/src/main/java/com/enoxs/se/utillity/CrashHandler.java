package com.enoxs.se.utillity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import com.enoxs.se.activity.MainActivity;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by mayaminer on 2017/3/9.
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {
    private static final String TAG = "CrashHandler";
    private static final boolean DEBUG = true;

    //    private static final String PATH = Environment.getExternalStorageDirectory().getPath() + "/ryg_test/log/";
    private static final String PATH = Environment.getExternalStorageDirectory() + File.separator + "SE" + File.separator + "Log";

    private static final String FILE_NAME = "crash";

    //log文件的後綴名
    private static final String FILE_NAME_SUFFIX = ".trace";

    private static CrashHandler sInstance = new CrashHandler();

    //系統默認的異常處理（默認情況下，系統會終止當前的異常程序）
    private Thread.UncaughtExceptionHandler mDefaultCrashHandler;

    private Context mContext;

    //構造方法私有，防止外部構造多個實例，即採用單例模式
    private CrashHandler() {
    }

    public static CrashHandler getInstance() {
        return sInstance;
    }

    //這裡主要完成初始化工作
    public void init(Context context) {
        //獲取系統默認的異常處理器
        mDefaultCrashHandler = Thread.getDefaultUncaughtExceptionHandler();
        //將當前實例設為系統默認的異常處理器
        Thread.setDefaultUncaughtExceptionHandler(this);
        //獲取Context，方便內部使用
        mContext = context.getApplicationContext();
    }

    /**
     * 這個是最關鍵的函數，當程序中有未被捕獲的異常，系統將會自動調用#uncaughtException方法
     * thread為出現未捕獲異常的線程，ex為未捕獲的異常，有了這個ex，我們就可以得到異常信息。
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        try {
            //導出異常信息到SD卡中
            dumpExceptionToSDCard(ex);
            //這裡可以通過網絡上傳異常信息到服務器，便於開發人員分析日誌從而解決bug
            uploadExceptionToServer();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //打印出當前調用棧信息
        ex.printStackTrace();

        // 重新啟動 應用程式
        AlarmManager mgr = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(mContext, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("crash", true);
        PendingIntent restartIntent = PendingIntent.getActivity(mContext, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 1000, restartIntent); // 1秒鐘後重啟應用

        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
        System.gc();
        try{
            Thread.sleep(2000);
        }catch (InterruptedException e){
            Log.e(TAG, "error : ", e);
        }

    }

    private void dumpExceptionToSDCard(Throwable ex) throws IOException {
        //如果SD卡不存在或無法使用，則無法把異常信息寫入SD卡
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            if (DEBUG) {
                Log.w(TAG, "sdcard unmounted,skip dump exception");
                return;
            }
        }

        File dir = new File(PATH);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        long current = System.currentTimeMillis();
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(current));
        //以當前時間創建log文件
        File file = new File(PATH + File.separator + FILE_NAME + time + FILE_NAME_SUFFIX);

        try {
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));
            //導出發生異常的時間
            pw.println(time);

            //導出手機信息
            dumpPhoneInfo(pw);

            pw.println();
            //導出異常的調用棧信息
            ex.printStackTrace(pw);

            pw.close();
        } catch (Exception e) {
            Log.e(TAG, "dump crash info failed");
        }
    }

    private void dumpPhoneInfo(PrintWriter pw) throws PackageManager.NameNotFoundException {
        //應用的版本名稱和版本號
        PackageManager pm = mContext.getPackageManager();
        PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(), PackageManager.GET_ACTIVITIES);
        pw.print("App Version: ");
        pw.print(pi.versionName);
        pw.print('_');
        pw.println(pi.versionCode);

        //android版本號
        pw.print("OS Version: ");
        pw.print(Build.VERSION.RELEASE);
        pw.print("_");
        pw.println(Build.VERSION.SDK_INT);

        //手機製造商
        pw.print("Vendor: ");
        pw.println(Build.MANUFACTURER);

        //手機型號
        pw.print("Model: ");
        pw.println(Build.MODEL);

        //cpu架構
        pw.print("CPU ABI: ");
        pw.println(Build.CPU_ABI);
    }

    private void uploadExceptionToServer() {
        //TODO Upload Exception Message To Your Web Server
    }



}
