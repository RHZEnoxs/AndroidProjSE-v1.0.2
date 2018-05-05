package com.enoxs.se.model.object;

/**
 * Created by enoxs on 2018/5/3.
 */

public class AppInfo {
    /*String [] appConfig = {
            "app_name","app_version","app_date","app_author","app_remark"
    };*/
    private static String name = "";
    private static String version = "";
    private static String date = "";
    private static String author = "";
    private static String remark = "";

    public AppInfo(){

    }
    /**
     * Set App Information
     */
    public void setName(String name){
        this.name = name;
    }
    public void setVersion(String version){
        this.version = version;
    }
    public void setDate(String date){
        this.date = date;
    }
    public void setAuthor(String author){
        this.author = author;
    }
    public void setRemark(String remark){
        this.remark = remark;
    }

    /**
     * Get App Information
     */
    public String getName(){
        return this.name;
    }
    public String getVersion(){
        return this.version;
    }
    public String getDate(){
        return this.date;
    }
    public String getAuthor(){
        return this.author;
    }
    public String getRemark(){
        return this.remark;
    }
}
