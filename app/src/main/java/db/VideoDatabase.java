package db;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import models.VideoItem;

@Database(entities = {VideoItem.class}, version = 1)
public abstract class VideoDatabase extends RoomDatabase {
    public abstract VideoDao videoDao();
}
