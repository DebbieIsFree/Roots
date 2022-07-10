package com.example.project2;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MusicData {
    @SerializedName("name")
    private String name;
    @SerializedName("singer")
    private String singer;
    @SerializedName("comment")
    private List<String> comment;

    String getName(){
        return name;
    }
    String getSinger(){
        return singer;
    }
    List<String> getComment() { return comment; }
}
