package mainpackage.jivetest.asynctask;

import android.os.AsyncTask;
import android.support.v4.util.Pair;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import mainpackage.jivetest.listener.GetUserInfoListener;
import mainpackage.jivetest.model.UserInfo;
import mainpackage.jivetest.support.Constants;
import mainpackage.jivetest.support.Utils;

public class GetUserInfoAsyncTask extends AsyncTask<Void, Void, Pair<Integer, JSONObject>> {
    private String userId;
    private GetUserInfoListener getUserInfoListener;
    private BufferedReader reader;

    public GetUserInfoAsyncTask(String userId, GetUserInfoListener getUserInfoListener) {
        this.userId = userId;
        this.getUserInfoListener = getUserInfoListener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        getUserInfoListener.beforeGetUserInfo();
    }

    @Override
    protected Pair<Integer, JSONObject> doInBackground(Void... voids) {

        try {
            URL url = new URL(Utils.getUserUrl(userId));
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

                JSONObject userObject = (JSONObject) responsePair.second.get(Constants.PERSON);
                JSONObject usernameObject=userObject.getJSONObject(Constants.USERNAME);
                JSONObject realnameObject=userObject.getJSONObject(Constants.REALNAME);
                JSONObject locationObject=userObject.getJSONObject(Constants.LOCATION);

                String username=usernameObject.getString(Constants.CONTENT);
                String realname=realnameObject.getString(Constants.CONTENT);
                String location=locationObject.getString(Constants.CONTENT);

                UserInfo userInfo=new UserInfo();
                userInfo.setUsername(username);
                userInfo.setRealname(realname);
                userInfo.setLocation(location);

                getUserInfoListener.afterGetUserInfo(userInfo);
            } catch (JSONException e) {
                e.printStackTrace();
                getUserInfoListener.onError();
            }
        } else {
            getUserInfoListener.onError();
        }
    }
}
