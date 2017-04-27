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
import mainpackage.jivetest.listener.GetRecentPhotosListener;
import mainpackage.jivetest.model.FlickerPhotos;
import mainpackage.jivetest.support.Constants;
import mainpackage.jivetest.support.Utils;

public class GetRecentPhotosAsyncTask extends AsyncTask<Void, Void, Pair<Integer, JSONObject>> {
    private GetRecentPhotosListener getRecentPhotosListener;
    private BufferedReader reader;

    public GetRecentPhotosAsyncTask(GetRecentPhotosListener getRecentPhotosListener) {
        this.getRecentPhotosListener = getRecentPhotosListener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        getRecentPhotosListener.beforeGetRecntPhotos();
    }

    @Override
    protected Pair<Integer, JSONObject> doInBackground(Void... voids) {

        try {
            URL url = new URL(Utils.FLICKERRECENTPHOTOSLINK);
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
        }finally {
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

                JSONObject photosObject=(JSONObject) responsePair.second.get(Constants.PHOTOS);

                JSONArray jsonArray = (JSONArray) photosObject.get(Constants.PHOTO);
                List<FlickerPhotos> flickerPhotosList = new ArrayList<>();

                for (int i = 0; i < jsonArray.length(); i++) {
                    flickerPhotosList.add(new Gson().fromJson(jsonArray.get(i).toString(), FlickerPhotos.class));
                }

                getRecentPhotosListener.afterGetRecentPhotos(flickerPhotosList);
            } catch (JSONException e) {
                e.printStackTrace();
                getRecentPhotosListener.onError();
            }
        } else {
            getRecentPhotosListener.onError();
        }

    }
}
