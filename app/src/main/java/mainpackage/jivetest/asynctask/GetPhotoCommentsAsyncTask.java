package mainpackage.jivetest.asynctask;

import android.os.AsyncTask;
import android.support.v4.util.Pair;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import mainpackage.jivetest.listener.GetPhotoCommentsListener;
import mainpackage.jivetest.model.PhotoComments;
import mainpackage.jivetest.support.Constants;
import mainpackage.jivetest.support.Utils;

public class GetPhotoCommentsAsyncTask extends AsyncTask<Void, Void, Pair<Integer, JSONObject>> {
    private String photoId;
    private GetPhotoCommentsListener getPhotoCommentsListener;
    private BufferedReader reader;

    public GetPhotoCommentsAsyncTask(String photoId, GetPhotoCommentsListener getPhotoCommentsListener) {
        this.photoId= photoId;
        this.getPhotoCommentsListener = getPhotoCommentsListener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        getPhotoCommentsListener.beforeGetPhotoComments();
    }

    @Override
    protected Pair<Integer, JSONObject> doInBackground(Void... voids) {

        try {
            URL url = new URL(Utils.getPhotoComments(photoId));
            URLConnection urlConn = url.openConnection();
            HttpURLConnection httpConn = (HttpURLConnection) urlConn;
            httpConn.setRequestMethod("GET");
            httpConn.connect();

            reader = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), "UTF-8"));
            JSONObject jsonObject = new JSONObject(reader.readLine());

            return new Pair<Integer, JSONObject>(httpConn.getResponseCode(), jsonObject);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (reader != null)
                    reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onPostExecute(Pair<Integer, JSONObject> responsePair) {
        super.onPostExecute(responsePair);
        if (responsePair != null && responsePair.first == 200) {
            try {

                JSONObject commentObject = (JSONObject) responsePair.second.get(Constants.COMMENTS);
                JSONArray comments=(JSONArray)commentObject.getJSONArray(Constants.COMMENT);

                List<PhotoComments> photoComments = new ArrayList<>();

                for (int i = 0; i < comments.length(); i++) {
                    photoComments.add(new Gson().fromJson(comments.get(i).toString(), PhotoComments.class));
                }

                getPhotoCommentsListener.afterGetPhotoComments(photoComments);
            } catch (JSONException e) {
                e.printStackTrace();
                getPhotoCommentsListener.onError();
            }
        } else {
            getPhotoCommentsListener.onError();
        }
    }
}
