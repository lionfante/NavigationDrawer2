package vn.geomaps.navigationdrawer2;

import android.os.AsyncTask;
import android.renderscript.ScriptGroup;
import android.support.annotation.RequiresPermission;
import android.util.JsonReader;
import android.util.Log;

import com.google.android.gms.maps.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lionf_000 on 27-Mar-17.
 */

public class DownloadBoreholes extends AsyncTask<String,Void,List<Marker>> {
    @Override
    protected List<Marker> doInBackground(String... params) {
        URL url = null;
        List<Marker> listMarker = null;
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;
        StringBuilder builder = new StringBuilder();
        try {
            url = new URL(params[0]);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.connect();
            inputStream = httpURLConnection.getInputStream();
            ReadBoreholes readBoreholes = new ReadBoreholes();
            listMarker = readBoreholes.readJsonStreamMarkers(inputStream);
            httpURLConnection.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return listMarker;
    }

    @Override
    protected void onPostExecute(List<Marker> markers) {
        super.onPostExecute(markers);
    }
}

