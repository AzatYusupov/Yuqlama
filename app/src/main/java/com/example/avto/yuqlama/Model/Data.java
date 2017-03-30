package com.example.avto.yuqlama.Model;

import java.util.Date;

/**
 * Created by Azat on 11.03.2017.
 */

public class Data {
    int year;
    String month, week;
    int day;
    int hours;
    int minutes;
    public Data(Date date) {
        this.year = date.getYear();
        this.month = toUzbeMonth(date.getMonth());
        this.day = date.getDate();
        this.hours = date.getHours();
        this.minutes = date.getMinutes();
        this.week = toUzbeWeekDay(date.getDay());
    }
    public int getYear() {
        return this.year;
    }
    public String getMonth() {
        return this.month;
    }
    public String getWeek() {
        return this.week;
    }
    public int getDay() {
        return this.day;
    }
    public int getHours() {
        return this.hours;
    }
    public String getMinutes() {
        return toStringMinute(this.minutes);
    }
    public int getPair() {
        if (isBigger("8:30") && isLess("9:50"))
            return 1;
        if (isBigger("10:00") && isLess("11:20"))
            return 2;
        if (isBigger("11:30") && isLess("12:50"))
            return 3;
        if (isBigger("13:30") && isLess("14:50"))
            return 4;
        if (isBigger("15:00") && isLess("16:20"))
            return 5;
        if (isBigger("16:30") && isLess("17:50"))
            return 6;
        return -1;
    }
    private String toStringMinute(int m) {
        if (m < 10)
            return "0"+m;
        return m+"";
    }
    private boolean isBigger(String time) {
        String[]a = time.split(":");
        int h = Integer.parseInt(a[0]);
        int m = Integer.parseInt(a[1]);
        if (this.hours > h || this.hours == h && this.minutes >= m)
            return true;
        return false;
    }
    private boolean isLess(String time) {
        String[]a = time.split(":");
        int h = Integer.parseInt(a[0]);
        int m = Integer.parseInt(a[1]);
        if (this.hours < h || this.hours == h && this.minutes <= m)
            return true;
        return false;
    }
    private static String toUzbeMonth(int n) {
        String[]months = {"Yan", "Fev", "Mar", "Apr", "May", "Iyun", "Iyul", "Avg", "Sen", "Okt", "Noy", "Dek"};
        return months[n];
    }
    private static String toUzbeWeekDay(int n) {
        String[]weeks = {"Yak", "Dush", "Sesh", "Chor", "Pay", "Juma", "Shan"};
        return weeks[n];
    }
}
