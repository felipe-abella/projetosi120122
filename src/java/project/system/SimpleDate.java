package project.system;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
import project.exceptions.InvalidDateException;

public class SimpleDate implements Comparable<SimpleDate> {
    private int year, month, day;
    
    public SimpleDate() {
        day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        month = Calendar.getInstance().get(Calendar.MONTH);
        year = Calendar.getInstance().get(Calendar.YEAR);
    }
    
    public SimpleDate(int year, int month, int day) {
        setSimpleDate(year, month, day);
    }
    
    public SimpleDate(String dateString) {
        setSimpleDate(dateString);
    }
    
    public int getYear() {
        return year;
    }
    
    public int getMonth() {
        return month;
    }
    
    public int getDay() {
        return day;
    }
    
    public void setSimpleDate(int year, int month, int day) {
        if (!isDateValid(year, month, day))
            throw new InvalidDateException();
        this.year = year;
        this.month = month;
        this.day = day;
    }
    
    public void setSimpleDate(String dateString) {
        String[] parts = dateString.split("/");
        if (parts.length != 3)
            throw new InvalidDateException();
        
        int day, month, year;
        
        try {
            day = Integer.parseInt(parts[0]);
            month = Integer.parseInt(parts[1]);
            year = Integer.parseInt(parts[2]);
        } catch (NumberFormatException ex) {
            throw new InvalidDateException();
        }
        
        setSimpleDate(year, month, day);
    }
    
    public boolean isLeapYear(int year) {
        if (year%400 == 0)
            return true;
        if (year%4 == 0 && year%100 != 0)
            return true;
        return false;
    }
    
    public boolean isDateOld() {
        int nowDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        int nowMonth = Calendar.getInstance().get(Calendar.MONTH);
        int nowYear = Calendar.getInstance().get(Calendar.YEAR);
        
        if (year != nowYear)
            return year < nowYear;
        if (month != nowMonth)
            return month < nowMonth;
        return day < nowDay;
    }
    
    public boolean isDateValid(int year, int month, int day) {
        if (year < 1900)
            return false; // Unsupported!
        if (month < 1 || month > 12)
            return false;
        int monthSizes[] = new int[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        if (isLeapYear(year))
            monthSizes[1] = 29;
        if (day < 1 || day > monthSizes[month-1])
            return false;
        return true;
    }
    
    @Override
    public String toString() {
        return String.format("%02d/%02d/%d", day, month, year);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + this.year;
        hash = 53 * hash + this.month;
        hash = 53 * hash + this.day;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SimpleDate other = (SimpleDate) obj;
        if (this.year != other.year) {
            return false;
        }
        if (this.month != other.month) {
            return false;
        }
        if (this.day != other.day) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(SimpleDate other) {
        if (year != other.year)
            return year-other.year;
        if (month != other.month)
            return month-other.month;
        return day-other.day;
    }
}
