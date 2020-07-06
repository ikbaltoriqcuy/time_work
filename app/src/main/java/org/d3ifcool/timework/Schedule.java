package org.d3ifcool.timework;

import android.text.Editable;

import java.util.Date;

public class Schedule {
    private String idSchedule;
    private String nameSchedule;
    private String day;
    private String startTime;
    private String endTime;
    private int active;

    public Schedule(String idSchedule, String nameSchedule, String day, String startTime, String endTime, int active) {
        String splitStartText[] = startTime.split(":");
        String splitEndText[] = endTime.split(":");
        this.idSchedule = idSchedule;
        this.nameSchedule = nameSchedule;
        this.day = day;

        splitStartText[0] = splitStartText[0].length() ==1 ? "0"+splitStartText[0] : splitStartText[0];
        splitStartText[1] = splitStartText[1].length() ==1 ? "0"+splitStartText[1] : splitStartText[1];

        this.startTime = splitStartText[0] +":" + splitStartText[1];

        splitEndText[0] = splitEndText[0].length() ==1 ? "0"+splitEndText[0] : splitEndText[0];
        splitEndText[1] = splitEndText[1].length() ==1 ? "0"+splitEndText[1] : splitEndText[1];

        this.endTime = splitEndText[0]+":"+splitEndText[1];
        this.active = active;
    }

    public String getIdSchedule() {
        return idSchedule;
    }

    public String getNameSchedule() {
        return nameSchedule;
    }

    public String getDay() {
        return day;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public int getActive() {
        return active;
    }

    public void setIdSchedule(String idSchedule) {
        this.idSchedule = idSchedule;
    }

    public void setNameSchedule(String nameSchedule) {
        this.nameSchedule = nameSchedule;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setActive(int active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "idSchedule='" + idSchedule + '\'' +
                ", nameSchedule='" + nameSchedule + '\'' +
                ", day='" + day + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", active=" + active +
                '}';
    }
}
