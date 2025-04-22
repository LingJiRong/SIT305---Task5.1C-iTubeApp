package models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class VideoItem {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String url;

    public VideoItem(String url) {
        this.url = url;
    }
}

