package project.topka.beacon11;

import android.os.Message;
import android.util.JsonReader;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by atopk on 4/3/2016.
 */
public class UpdateBeacon {

    private String[] getBeaconDataFromJson(String jsonStr, int length) throws JSONException {

        //json nodes
        private static final String BEACON = "beacon";
        private static final String CREATOR = "username";
        private static final String TITLE = "title";
        private static final String LATCOORD = "latCoord";
        private static final String LONGCOORD = "longCoord";
        private static final String STARTTIME = "startTime";
        private static final String ENDTIME = "endTime";
        private static final String PLACENAME = "placeName";
        private static final String ADDRESS = "address";
        private static final String TAGS = "tags";

        JSONObject beaconJson = new JSONObject(jsonStr);
        JSONArray beaconArray = new jsonStr.getJSONArray(BEACON);

        for(int i=0; i<contacts.length(); i++) {
            JSONObject c = contacts.getJSONObject(i);

            String username = c.getString(CREATOR);
            String title = c.getString(TITLE);
            String latCoord = c.getString(LATCOORD);
            String longCoord = c.getString(LONGCOORD);
            String startTime = c.getString(STARTTIME);
            String endTime = c.getString(ENDTIME);
            String placeName = c.getString(PLACENAME);
            String address = c.getString(ADDRESS);
            String tags = c.getString(TAGS);
        }

    }

    //Need to close connection
    //Shouldn't this return something other than null?
    public static String[] getData() {
        HttpsURLConnection urlConnection = null;
        BufferedReader reader = null;
        String jsonstr = null;
        String format = "json";

        try {
            final String BASE_URL = "http://localhost:8080/webapi/API/";
            URL url = new URL(BASE_URL);

            //Create request
            urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));
            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
        } catch (IOException e) {
            Log.e("url", "error");
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e("stream", "Error closing stream", e);
                }
            }
        }
        return null;

    }

    // JSON READER //
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
        String creator = null;
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
            else if (name.equals("creator")) {
                creator = reader.nextString();
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
