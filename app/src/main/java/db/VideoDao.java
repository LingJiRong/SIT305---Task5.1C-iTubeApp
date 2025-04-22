package db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import models.VideoItem;
import java.util.List;

@Dao
public interface VideoDao {
    @Insert
    void insert(VideoItem item);

    @Query("SELECT * FROM VideoItem")
    List<VideoItem> getAll();
}
