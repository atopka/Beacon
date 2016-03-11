package project.topka.beacon11;

import android.net.http.HttpResponseCache;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.params.HttpConnectionParams;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CreateBeacon extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_beacon);



    }



    public void sendBeacon(View view)
    {
        //Fields
        EditText mTitleField = (EditText) findViewById(R.id.beacon_name);
        EditText mDescriptionField = (EditText) findViewById(R.id.beacon_description);
        EditText mLocationField = (EditText) findViewById(R.id.beacon_location);
        EditText mTimeStartField = (EditText) findViewById(R.id.beacon_time_start);
        EditText mTimeEndField = (EditText) findViewById(R.id.beacon_time_end);
        EditText mInterestsField = (EditText) findViewById(R.id.beacon_interests);


        String title = mTitleField.getText().toString();
        String desc = mDescriptionField.getText().toString();
        Double latcoord;
        Double longcoord;
        ArrayList<String> interests = new ArrayList<String>(); //use loop to fill
        Double duration;
        Double range;
        String location;

        String path = "http://localhost:8080/webapi/API/";
//        HttpClient client = new DefaultHttpClient();
//        HttpConnectionParams.setConnectionTimeout(client.getParams(),100000);
//        HttpResponse response;
//        JSONObject json = new JSONObject();
//        try {
//
//        } catch (Exception e) {
//            Object n=e.getStackTrace();
//            Toast.makeText(getApplicationContext(),n.toString(), Toast.LENGTH_SHORT).show();
//        }


    }

    //JSON READER
    public List readJsonStream(InputStream in) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        try {
            return readBeaconArray(reader);
        }finally {
            reader.close();
        }
    }

    public List readBeaconArray (JsonReader reader) throws IOException {
        List beacons = new ArrayList();

        reader.beginArray();
        while (reader.hasNext()) {
            beacons.add(readMessage(reader));
        }
        reader.endArray();
        return beacons;
    }

    public ArrayList<String> readStringArray(JsonReader reader) throws IOException {
        ArrayList<String> strings = new ArrayList<String>();
        reader.beginArray();
        while(reader.hasNext()) {
            strings.add(reader.nextString());
        }
        reader.endArray();
        return strings;
    }

    public Message readMessage(JsonReader reader) throws IOException {
        int length;
        String title = null;
        String desc = null;
        Double latcoord = null;
        Double longcoord = null;
        ArrayList<String> interests = new ArrayList<String>();
        Double duration = null;
        Double range = null;
        String location = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("title")) {
                title = reader.nextString();
            }
            else if (name.equals("desc")) {
                desc = reader.nextString();
            }
            else if (name.equals("latcoord")) {
                latcoord = reader.nextDouble();
            }
            else if (name.equals("longcoord")) {
                longcoord = reader.nextDouble();
            }
            else if (name.equals("interests")) {
                interests = readStringArray(reader);
            }
            else if (name.equals("duration")) {
                duration = reader.nextDouble();
            }
            else if (name.equals("range")) {
                range = reader.nextDouble();
            }
            else if (name.equals("location")) {
                location = reader.nextString();
            }
            else{
                reader.skipValue();
            }
        }
        reader.endObject();
        //return new Message(title,desc,latcoord,longcoord,interests,duration,range,location);
        return new Message();
    }

}
