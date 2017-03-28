package vn.geomaps.navigationdrawer2;

import android.util.JsonReader;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lionf_000 on 27-Mar-17.
 */

public class ReadBoreholes{
    String data = "";
    public List<Marker> readJsonStreamMarkers(InputStream inputStream, String fileCache) throws IOException {

        JsonReader reader = new JsonReader(new InputStreamReader(inputStream,"UTF-8"));
        reader.beginObject();
        while(reader.hasNext()){
            String field = reader.nextName();
            if(field.equals("data")){
                data = reader.nextString();
                StoreCache storeCache = new StoreCache(fileCache);
                storeCache.createFileCache(data);
            }
        }
        try{
            return readBoreholeArray(data);
        }finally {
            reader.close();
            inputStream.close();
        }
    }

    public List<Marker> readBoreholeArray(String data) throws IOException{
        List<Marker> markerses = new ArrayList<Marker>();
        JsonReader reader = new JsonReader(new StringReader(data));
        reader.beginArray();
        while(reader.hasNext()){
            markerses.add(readMarker(reader));
        }
        reader.endArray();
        return markerses;
    }

    public Marker readMarker(JsonReader reader) throws IOException{
        int id = -1;
        String type = null, name = null;
        double latitude = -1, longtitude = -1;
        reader.beginObject();
        while(reader.hasNext()){
            String field = reader.nextName();
            if(field.equals("type")){
                type = reader.nextString();
            }else if(field.equals("id")){
                id = reader.nextInt();
            }else if(field.equals("nm")){
                name = reader.nextString();
            }else if(field.equals("la")){
                latitude = reader.nextDouble();
            }else if(field.equals("lo")){
                longtitude = reader.nextDouble();
            }else{
                reader.skipValue();
            }
        }
        reader.endObject();
        return new Marker(type,id,name,latitude,longtitude);
    }
}
