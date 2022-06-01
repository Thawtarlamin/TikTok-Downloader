package com.thukuma.tiktok_downloader;

import android.os.AsyncTask;
import android.os.Build;

import com.thukuma.tiktok_downloader.Model.TikTokModels;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class TikTokData extends AsyncTask<String, String, String> {
    private GetTikTokData.onComplete complete;
    private GetTikTokData.onError onError;

    public TikTokData(GetTikTokData.onComplete complete, GetTikTokData.onError onError) {
        this.complete = complete;
        this.onError = onError;
    }

    @Override
    protected String doInBackground(String... strings) {
        HttpURLConnection connection;
        BufferedReader reader;
        try {

            URL url = new URL(strings[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            InputStream stream = connection.getInputStream();

            reader = new BufferedReader(new InputStreamReader(stream));

            StringBuilder buffer = new StringBuilder();
            String Line;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

            }
            while ((Line = reader.readLine()) != null) {
                if (Line.contains("videoData")) {
                    Line = Line.substring(Line.indexOf("videoData"));
                    buffer.append(Line);
                    break;
                }
            }
            return buffer.toString();
        } catch (Exception e) {
            onError.onError(e.toString());
            return "Invalid Video URL or Check Internet Connection";
        }
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        try {
            JSONObject object = new JSONObject(s.replace("videoData\":",""));
            String Water = object.getJSONObject("itemInfo").getJSONObject("itemStruct").getJSONObject("video").getString("downloadAddr");
            String id = object.getJSONObject("itemInfo").getJSONObject("itemStruct").getJSONObject("video").getString("id");
            String avatarThumb = object.getJSONObject("itemInfo").getJSONObject("itemStruct").getJSONObject("author").getString("avatarMedium");
            String nickname = object.getJSONObject("itemInfo").getJSONObject("itemStruct").getJSONObject("author").getString("nickname");
            String Audio_url = object.getJSONObject("itemInfo").getJSONObject("itemStruct").getJSONObject("music").getString("playUrl");
            String duration = object.getJSONObject("itemInfo").getJSONObject("itemStruct").getJSONObject("video").getString("duration");
            new DownloadTikTokVideo(complete,onError).execute(id,Water,avatarThumb,nickname,Audio_url,duration);
        } catch (JSONException e) {
            onError.onError(e.toString());
            e.printStackTrace();
        }

    }
    static class DownloadTikTokVideo extends AsyncTask<String, Void, Document> {
        Document doc;
        private GetTikTokData.onComplete complete;
        private GetTikTokData.onError onError;
        private String Water,avatarThumb,nickname,Audio_url,duration;

        public DownloadTikTokVideo(GetTikTokData.onComplete complete,GetTikTokData.onError onError) {
            this.complete = complete;
            this.onError = onError;
        }
        @Override
        protected Document doInBackground(String... urls) {
            Water = urls[1];
            avatarThumb = urls[2];
            nickname = urls[3];
            Audio_url = urls[4];
            duration = urls[5];
            try {
                Map<String, String> Headers = new HashMap<String, String>();
                Headers.put("Cookie","1");
                Headers.put("User-Agent","1");
                Headers.put("Accept","application/json");
                Headers.put("Host","api2-16-h2.musical.ly");
                Headers.put("Connection","keep-alive");
                doc = Jsoup.connect("https://api2-16-h2.musical.ly/aweme/v1/aweme/detail/").data("aweme_id",urls[0]).ignoreContentType(true).headers(Headers).get();
            } catch (IOException e) {
                e.printStackTrace();
                onError.onError(e.toString());

            }
            return doc;

        }

        protected void onPostExecute(Document result) {

            String URL = result.body().toString().replace("<body>","").replace("</body>","");
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(URL);
                String URLs = jsonObject.getJSONObject("aweme_detail").getJSONObject("video").getJSONObject("play_addr").getJSONArray("url_list").getString(0);
                String Audio_Urls = jsonObject.getJSONObject("aweme_detail").getJSONObject("music").getJSONObject("play_url").getJSONArray("url_list").getString(0);
                TikTokModels models = new TikTokModels();
                if (!URLs.isEmpty()
                        &&!Water.isEmpty()
                        &&!avatarThumb.isEmpty()
                        &&!nickname.isEmpty()
                        &&!Audio_url.isEmpty()
                        &&!duration.isEmpty()
                ) {
                    models.setWater(Water);
                    models.setAvatarThumb(avatarThumb);
                    models.setNickname(nickname);
                    models.setAudio_url(Audio_url);
                    models.setDuration("0:"+duration);
                    models.setNo_Water(URLs);
                    complete.onComplete(models);
                }

            }catch (JSONException err){
                onError.onError(err.toString());

            }


        }

    }


}
