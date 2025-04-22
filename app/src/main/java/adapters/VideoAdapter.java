package adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.itubeapp.R;
import models.VideoItem;
import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {
    private List<VideoItem> videoList;

    public VideoAdapter(List<VideoItem> videoList) {
        this.videoList = videoList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.urlText.setText(videoList.get(position).url);
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView urlText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            urlText = itemView.findViewById(R.id.video_url);
        }
    }
}
