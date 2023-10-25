package com.example.projectmobile;

import java.util.ArrayList;
import java.util.Date;

public class Trip {
    public static ArrayList<Trip> notes = new ArrayList<>();
    public static ArrayList<Trip> searchNotes = new ArrayList<>();
    public static String NOTE_EDIT_EXTRA =  "noteEdit";

    private int id;

    private String datetime;

    private String title;

    private String time;

    private String description;

    private String purpose;
    private String destination;

    private String groupRisky;

    private Date deleted;

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public static Trip getNoteForID(int passedNoteId) {

        for (Trip note : notes) {
            if (note.getId() == passedNoteId){
                return  note;
            }
        }
        return null;
    }

    public static ArrayList<Trip> nonDeleteNote(){
        ArrayList<Trip> nonDelete = new ArrayList<>();
        for (Trip note : notes) {
            if (note.getDeleted() == null){
                nonDelete.add(note);
            }
        }
        return nonDelete;

    }

    public static ArrayList<Trip> searchNotes(){
        ArrayList<Trip> nonDelete = new ArrayList<>();
        for (Trip note : searchNotes) {
            if (note.getDeleted() == null){
                nonDelete.add(note);
            }
        }
        return nonDelete;

    }

    public Trip() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDeleted(Date deleted) {
        this.deleted = deleted;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Trip(int id, String title, String destination, String description, String purpose , String groupRisky, String datetime, String time, Date deleted) {
        this.id = id;
        this.datetime = datetime;
        this.destination = destination;
        this.title = title;
        this.time = time;
        this.description = description;
        this.purpose = purpose;
        this.groupRisky = groupRisky;
        this.deleted = deleted;
    }

    public Trip(int id, String title, String description, String datetime, String time, String groupRisky, String purpose, String destination) {
        this.id = id;
        this.title = title;
        this.datetime = datetime;
        this.purpose =purpose;
        this.groupRisky = groupRisky;
        this.description = description;
        this.destination = destination;
        this.time = time;
        deleted = null;
    }

    public String getGroupRisky() {
        return groupRisky;
    }

    public void setGroupRisky(String groupRisky) {
        this.groupRisky = groupRisky;
    }

    public Date getDeleted() {

        return deleted;
    }
}
