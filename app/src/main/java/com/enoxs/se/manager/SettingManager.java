package com.enoxs.se.manager;

import android.content.Context;
import android.os.Environment;

import com.enoxs.se.R;
import com.enoxs.se.model.event.MessageCenterEvent;
import com.enoxs.se.model.object.AppInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

/**
 * Created by enoxs on 2018/4/26.
 */

public class SettingManager extends Thread{
    private static Logger log = LoggerFactory.getLogger(SettingManager.class);
    private static Context context;
    private static String sdDir = Environment.getExternalStorageDirectory().getPath() + "/SE/";
    private static String sdConfigPath = sdDir + "config.properties";
    private static Properties properties = new Properties();
    private static AppInfo appInfo = new AppInfo();
    private static MessageCenterEvent messageCenterEvent = new MessageCenterEvent();
    /**
     * config.properties
     * @param context
     */
    public SettingManager(Context context){
        super("SettingManager");
        this.context = context;
        this.start();
        log.info("init SettingManager.");
    }
    @Override
    public void run() {
        init();
        messageCenterEvent.setAppInfo(getConfigAppInfo());
        messageCenterEvent.sendMessageEvent();
        log.info("SettingManager finish.");
    }
    /**
     * 1. Check dir exists
     * 2. Check file exists
     * 3. Init config setting
     */
    private void init(){
        File sdDirPath = new File(sdDir);
        if (!sdDirPath.exists()) {
            log.info("file isn't exist");
            sdDirPath.mkdir();
        }
        File configFile = new File(sdConfigPath);
        if (!configFile.exists()) {
            log.info("copy config file");
            copyConfigFile();
        }else{
            log.info("init config setting");
            log.info("config path: " + sdConfigPath);
            initConfigSetting(configFile);
        }
        setConfigToAppInfo();
    }
    private boolean copyConfigFile(){
        boolean isInit = false;
        try {
            InputStream is = context.getResources().openRawResource(R.raw.config);
            FileOutputStream out = new FileOutputStream(sdConfigPath);
            byte[] buff = new byte[1024];
            int read = 0;
            try {
                while ((read = is.read(buff)) > 0) {
                    out.write(buff, 0, read);
                }
                properties.clear();
                InputStream ism = context.getResources().openRawResource(R.raw.config);
                properties.load(ism);
                ism.close();
                isInit = true;
            } finally {
                is.close();
                out.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("copyConfigFile Exception " + ex.getMessage());
        }
        return isInit;
    }
    private boolean initConfigSetting(File vipFile){
        boolean isInit = false;
        try {
            if(vipFile.canRead() && vipFile.canWrite()){
                //read file
                InputStream in = new FileInputStream(vipFile);
                //Write to (Properties)obj
                properties.clear();
                properties.load(in);
                in.close();
                isInit = true;
            }else{
                log.info("initConfigSetting check file permission");
            }
        }catch (Exception e) {
            e.getStackTrace();
            log.error("initConfigSetting Exception" + e.getMessage());
        }
        return isInit;
    }
    private void setConfigToAppInfo(){
        Enumeration enumeration = properties.propertyNames();
        String name,content;
        while (enumeration.hasMoreElements()) {
            name = (String) enumeration.nextElement();
            content = properties.get(name).toString();
            switch(name){
                case "app_name":
                    appInfo.setName(content);
                    break;
                case "app_version":
                    appInfo.setVersion(content);
                    break;
                case "app_date":
                    appInfo.setDate(content);
                    break;
                case "app_author":
                    appInfo.setAuthor(content);
                    break;
                case "app_remark":
                    appInfo.setRemark(content);
                    break;
                default:
                    log.info("name: {} , content: {} " , name , content);
                    break;
            }
        }
    }
    public AppInfo getConfigAppInfo(){
        return this.appInfo;
    }
}
