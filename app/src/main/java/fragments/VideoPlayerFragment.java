package fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.room.Room;
import com.example.itubeapp.R;
import db.VideoDatabase;
import models.VideoItem;
import java.util.concurrent.Executors;

public class VideoPlayerFragment extends Fragment {
    private WebView webView;
    private EditText inputLink;
    private Button playButton, saveButton, playlistButton;
    private VideoDatabase db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video_player, container, false);

        webView = view.findViewById(R.id.web_view);
        inputLink = view.findViewById(R.id.input_link);
        playButton = view.findViewById(R.id.btn_play);
        saveButton = view.findViewById(R.id.btn_save);
        playlistButton = view.findViewById(R.id.btn_playlist);

        db = Room.databaseBuilder(requireContext(), VideoDatabase.class, "video_db").build();

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setMediaPlaybackRequiresUserGesture(false);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        webView.setWebChromeClient(new WebChromeClient());

        playButton.setOnClickListener(v -> {
            String url = inputLink.getText().toString().trim();
            if (!TextUtils.isEmpty(url)) {
                String videoId = extractVideoId(url);
                if (videoId != null) {
                    webView.clearCache(true);
                    webView.clearHistory();

                    String html = "<html><body style='margin:0;padding:0;'>" +
                            "<iframe width='100%' height='100%' " +
                            "src='https://www.youtube.com/embed/" + videoId + "' " +
                            "frameborder='0' allowfullscreen></iframe></body></html>";

                    webView.loadDataWithBaseURL("https://www.youtube.com", html, "text/html", "UTF-8", null);

                    // Optional: Open in external YouTube app or browser
                    String videoUrl = "https://www.youtube.com/watch?v=" + videoId;
                    Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl));
                    startActivity(webIntent);

                    // Optional: Open directly in YouTube app
                    Intent ytIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + videoId));
                    ytIntent.setPackage("com.google.android.youtube");
                    if (ytIntent.resolveActivity(requireActivity().getPackageManager()) != null) {
                        startActivity(ytIntent);
                    }

                } else {
                    Toast.makeText(getContext(), "Invalid YouTube link", Toast.LENGTH_SHORT).show();
                }
            }
        });

        saveButton.setOnClickListener(v -> {
            String url = inputLink.getText().toString().trim();
            if (!TextUtils.isEmpty(url)) {
                Executors.newSingleThreadExecutor().execute(() -> db.videoDao().insert(new VideoItem(url)));
                Toast.makeText(getContext(), "Video saved to playlist", Toast.LENGTH_SHORT).show();
            }
        });

        playlistButton.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new PlaylistFragment())
                    .addToBackStack(null)
                    .commit();
        });

        return view;
    }

    private String extractVideoId(String url) {
        Uri uri = Uri.parse(url);
        if (uri.getHost() != null && uri.getHost().contains("youtu.be")) {
            return uri.getLastPathSegment();
        } else if (uri.getQueryParameter("v") != null) {
            return uri.getQueryParameter("v");
        }
        return null;
    }
}

