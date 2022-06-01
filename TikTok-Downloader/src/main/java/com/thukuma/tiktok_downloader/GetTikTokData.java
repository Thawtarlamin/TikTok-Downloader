package com.thukuma.tiktok_downloader;

import android.content.Context;

import com.thukuma.tiktok_downloader.Model.TikTokModels;

public class GetTikTokData {
    private Context context;
    private String url;

    public GetTikTokData(Context context, String url,onComplete onComplete,onError onError) {
        this.context = context;
        this.url = url;
        new TikTokData(onComplete,onError).execute(url);
    }

    public interface onComplete{
        void onComplete(TikTokModels models);
    }
    public interface onError{
        void onError(String onError);
    }
}
