package project.system;

import java.io.Serializable;
import java.util.Calendar;
import project.exceptions.InvalidDateException;

/**
 * Implements a simple "day/month/year" container.
 */
public class SimpleDate implements Comparable<SimpleDate>, Serializable {

    private int year, month, day;

    /**
     * Constructs a new SimpleDate using current day.
     */
    public SimpleDate() {
        day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        month = Calendar.getInstance().get(Calendar.MONTH);
        year = Calendar.getInstance().get(Calendar.YEAR);
    }

    /**
     * Constructs a new SimpleDate.
     *
     * @param year Year
     * @param month Month
     * @param day Day of the month
     * @throws InvalidDateException if these parameters doesn't make a valid
     * date.
     */
    public SimpleDate(int year, int month, int day) {
        setSimpleDate(year, month, day);
    }

    /**
     * Constructs a new SimpleDate from "DD/MM/YYYY"-formatted String.
     *
     * @param dateString the "DD/MM/YYYY"-formatted string
     * @throws InvalidDateException if dateString is not a valid date
     */
    public SimpleDate(String dateString) {
        setSimpleDate(dateString);
    }

    /**
     * Returns the year
     *
     * @return the year
     */
    public int getYear() {
        return year;
    }

    /**
     * Returns the month
     *
     * @return the month
     */
    public int getMonth() {
        return month;
    }

    /**
     * Returns the day
     *
     * @return the day
     */
    public int getDay() {
        return day;
    }

    /**
     * Change the SimpleDate's date.
     *
     * @param year Year
     * @param month Month
     * @param day Day of the month
     * @throws InvalidDateException if these parameters doesn't make a valid
     * date.
     */
    public void setSimpleDate(int year, int month, int day) {
        if (!isDateValid(year, month, day)) {
            throw new InvalidDateException();
        }
        this.year = year;
        this.month = month;
        this.day = day;
    }

    /**
     * Changes the date using a "DD/MM/YYYY"-formatted String.
     *
     * @param dateString the "DD/MM/YYYY"-formatted string
     * @throws InvalidDateException if dateString is not a valid date
     */
    public void setSimpleDate(String dateString) {
        String[] parts = dateString.split("/");
        if (parts.length != 3) {
            throw new InvalidDateException();
        }

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

    /**
     * Checks whether a given year is a leap year.
     *
     * @param year The year to check
     * @return whether it's a leap year
     */
    public static boolean isLeapYear(int year) {
        if (year % 400 == 0) {
            return true;
        }
        if (year % 4 == 0 && year % 100 != 0) {
            return true;
        }
        return false;
    }

    /**
     * Check if this date is before the current date.
     *
     * @return whether this date is old
     */
    public boolean isDateOld() {
        int nowDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        int nowMonth = Calendar.getInstance().get(Calendar.MONTH);
        int nowYear = Calendar.getInstance().get(Calendar.YEAR);

        if (year != nowYear) {
            return year < nowYear;
        }
        if (month != nowMonth) {
            return month < nowMonth;
        }
        return day < nowDay;
    }

    /**
     * Checks whether a given date is valid.
     *
     * This function assumes year >= 1900, otherwise it will return false.
     *
     * @param year Year
     * @param month Month
     * @param day Day of the month
     * @return whether it's valid
     */
    public static boolean isDateValid(int year, int month, int day) {
        if (month < 1 || month > 12) {
            return false;
        }
        int monthSizes[] = new int[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        if (isLeapYear(year)) {
            monthSizes[1] = 29;
        }
        if (day < 1 || day > monthSizes[month - 1]) {
            return false;
        }
        return true;
    }

    /**
     * Returns a "DD/MM/YYYY" representation of this date.
     *
     * @return the representation
     */
    @Override
    public String toString() {
        return String.format("%02d/%02d/%d", day, month, year);
    }

    /**
     * Returns the hash code of this date.
     *
     * @return the hash code
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + this.year;
        hash = 53 * hash + this.month;
        hash = 53 * hash + this.day;
        return hash;
    }

    /**
     * Returns whether this date equals another object.
     *
     * Two dates are equal when they have the same day, month and year.
     *
     * @param obj The other object
     * @return whether this SimpleDate equals the other object
     */
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

    /**
     * Compares this date to another.
     *
     * @param other The other date
     * @return less than 0, equal to 0 or greater than 0 if this date is before,
     * same time or after the other date
     */
    @Override
    public int compareTo(SimpleDate other) {
        if (year != other.year) {
            return year - other.year;
        }
        if (month != other.month) {
            return month - other.month;
        }
        return day - other.day;
    }
}
