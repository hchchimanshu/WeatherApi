package com.example.weather.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;



public class Pojo implements Serializable {

    String Message;
    String Status;
    @SerializedName("PostOffice")
    ArrayList<PostOfficePojo> PostOffice= null;


    public ArrayList<PostOfficePojo> getPostOffice() {
        return PostOffice;
    }

    public void setPostOffice(ArrayList<PostOfficePojo> postOffice) {
        PostOffice = postOffice;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
