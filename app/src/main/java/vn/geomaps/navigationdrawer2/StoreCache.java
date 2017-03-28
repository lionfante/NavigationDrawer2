package vn.geomaps.navigationdrawer2;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.maps.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/**
 * Created by lionf_000 on 28-Mar-17.
 */

public class StoreCache extends File {
    String pathname;
    public StoreCache(@NonNull String pathname) {
        super(pathname);
        this.pathname = pathname;
    }

    public void createFileCache(String data){
        File fileCache =  new File(pathname);
        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter;
        try {
            fileWriter = new FileWriter(fileCache);
            bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(data);
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createFileCacheFromInputStream(InputStream inputStream){
        StringBuilder dataJSON = new StringBuilder();
        String line="";
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        try {
            while((line=bufferedReader.readLine())!=null){
                dataJSON.append(line);
            }
            Log.d("dataJSON",dataJSON.toString());
        File fileCache =  new File(pathname);
        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter;

            fileWriter = new FileWriter(fileCache);
            bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(dataJSON.toString());
            bufferedWriter.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String readFileCache(){
        String data= "";
        File fileCache = new File(pathname);
        FileReader fileReader = null;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            fileReader = new FileReader(fileCache);
            BufferedReader buffer = new BufferedReader(fileReader);
            while((data = buffer.readLine()) != null){
                stringBuilder.append(data + "/n");
            }

            fileReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}
