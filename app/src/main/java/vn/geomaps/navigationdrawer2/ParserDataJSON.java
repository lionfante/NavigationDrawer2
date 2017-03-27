package vn.geomaps.navigationdrawer2;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;
import org.json.JSONTokener;

/**
 * Created by lionf_000 on 27-Mar-17.
 */

public class ParserDataJSON {
    String data;
    public ParserDataJSON(String data){
        this.data = data;
    }
    public void getData(){
        Log.d("Alldata",data);
    }
}
