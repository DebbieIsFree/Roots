package com.example.project2;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class PlaylistData {
    @SerializedName("name")
    private String name;
    @SerializedName("playlist")
    private ArrayList<String> playlist;

    String getName(){
        return name;
    }
    ArrayList<String> getPlaylist(){
        return playlist;
    }
}
