package fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import com.example.itubeapp.R;
import adapters.VideoAdapter;
import db.VideoDatabase;
import models.VideoItem;
import java.util.List;
import java.util.concurrent.Executors;

public class PlaylistFragment extends Fragment {
    private RecyclerView recyclerView;
    private VideoDatabase db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_playlist, container, false);

        recyclerView = view.findViewById(R.id.recycler_playlist);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        db = Room.databaseBuilder(requireContext(), VideoDatabase.class, "video_db").build();

        Executors.newSingleThreadExecutor().execute(() -> {
            List<VideoItem> list = db.videoDao().getAll();
            requireActivity().runOnUiThread(() -> recyclerView.setAdapter(new VideoAdapter(list)));
        });

        return view;
    }
}