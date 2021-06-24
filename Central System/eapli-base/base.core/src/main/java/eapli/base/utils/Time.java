/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.utils;

import eapli.framework.util.Calendars;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author bruno
 */
public class Time {
    
    private int hours;
    private int minutes;

    public Time(int hours, int minutes) {
        this.hours = hours;
        setMinutes(minutes);
    }
    
    public Time(int hours){
        setHours(hours);
        setMinutes(0);
    }
    
    public static long hoursToMilli(int hours){
        return hours * 60 * 60 * 1000;
    }
    
    public static long minutesToMilli(int minutes){
        return minutes * 60 * 1000;
    }
    
    public static long timeToMilli(Time time){
        return hoursToMilli(time.hours) + minutesToMilli(time.minutes);
    }
    
    public static Time now(){
        int hours, minutes;
        Calendar c = Calendars.now();
        minutes = c.get(Calendar.MINUTE);
        hours = c.get(Calendar.HOUR_OF_DAY);
        return new Time(hours, minutes);
    }
    
    public int hours(){
        return hours;
    }
    
    public int minutes(){
        return minutes;
    }
    
    public void addHours(int hours){
        this.hours += hours;
    }
    
    public boolean isBefore(Time time){
        if (this.hours < time.hours()){
            return true;
        }
        if (this.hours == time.hours()){
            return this.minutes < time.minutes();
        }
        return false;
    }
    
    public boolean isAfter(Time time){
        if (this.hours > time.hours()){
            return true;
        }
        if (this.hours == time.hours()){
            return this.minutes > time.minutes();
        }
        return false;
    }
    
    private void setMinutes(int minutes){
        if (minutes < 0){
            throw new IllegalArgumentException("Minutes must be positive");
        }
        if (minutes > 59){
            setMinutes(minutes - 60);
            this.hours += 1;
        }else{
            this.minutes = minutes;
        }
    }
    
    private void setHours(int hours){
        if (hours < 0){
            throw new IllegalArgumentException("Hours must be positive");
        }
        this.hours = hours;
    }

	public static Time valueOf(Date date) {
        if (date == null){
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int hours = calendar.get(Calendar.MINUTE);
        int minutes = calendar.get(Calendar.HOUR_OF_DAY);
        Time time = new Time(hours, minutes);
		return time;
	}
}
