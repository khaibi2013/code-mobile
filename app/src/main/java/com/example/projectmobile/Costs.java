package com.example.projectmobile;

import java.util.ArrayList;
import java.util.Date;

public class Costs {

    public static ArrayList<Costs> listCosts = new ArrayList<>();
    public static String COST_EDIT_EXTRA =  "costEdit";
    public static String COST_LIST_EXTRA =  "costEdit";

    public static String INPUT_COST_EXTRA =  "inputCost";

    private int id;

    private String typeCost;
    private String cost;

    private String comment;

    private String dateTime;


    private Integer noteId;


    private Date deleted;


    public String getTypeCost() {
        return typeCost;
    }

    public void setTypeCost(String typeCost) {
        this.typeCost = typeCost;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public Integer getNoteId() {
        return noteId;
    }

    public void setNoteId(Integer noteId) {
        this.noteId = noteId;
    }

    public static Costs getCostForID(int passedNoteId) {

        for (Costs cost : listCosts) {
            if (cost.getId() == passedNoteId){
                return  cost;
            }
        }
        return null;
    }

    public static ArrayList<Costs> nonDeleteCost(){
        ArrayList<Costs> nonDelete = new ArrayList<>();
        for (Costs cost : listCosts) {
            if (cost.getDeleted() == null){
                nonDelete.add(cost);
            }
        }

        return nonDelete;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getDeleted() {
        return deleted;
    }

    public void setDeleted(Date deleted) {
        this.deleted = deleted;
    }

    public Costs(int id,String typeCost ,String cost, String comment, String dateTime,Date deleted) {
        this.id = id;
        this.typeCost = typeCost;
        this.cost = cost;
        this.comment = comment;
        this.dateTime = dateTime;
//        this.noteId = noteId;
        this.deleted = deleted;
    }

    public Costs(int id, String typeCost ,String cost,String comment, String dateTime,Integer noteId) {
        this.id = id;
        this.dateTime = dateTime;
        this.typeCost = typeCost;
        this.cost = cost;
        this.comment = comment;
        this.noteId = noteId;
        deleted = null;
    }
}
