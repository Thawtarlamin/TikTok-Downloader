package com.thukuma.simpleapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.thukuma.tiktok_downloader.GetTikTokData;
import com.thukuma.tiktok_downloader.Model.TikTokModels;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new GetTikTokData(this, "https://www.tiktok.com/@lhakpasherpa5/video/7085729188493151515", new GetTikTokData.onComplete() {
            @Override
            public void onComplete(TikTokModels models) {
                Log.d("my-test", "No Water: "+models.getNo_Water());
                Log.d("my-test", "Water: "+models.getWater());
                Log.d("my-test", "Thumb: "+models.getAvatarThumb());
                Log.d("my-test", "Name: "+models.getNickname());
                Log.d("my-test", "Audio: "+models.getAudio_url());
                Log.d("my-test", "Duration: "+models.getDuration());
            }
        }, new GetTikTokData.onError() {
            @Override
            public void onError(String onError) {

            }
        });
    }
}