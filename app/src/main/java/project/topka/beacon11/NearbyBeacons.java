package project.topka.beacon11;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;

public class NearbyBeacons extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_beacons);

        // ATTEMPT TO CONNNECT TO BACK-END //
        new GetBeacons().execute();

        // TEST - HARDCODED BEACONS //
        Beacon beacon1 = new Beacon("Chuck Liang", "Compiler Theory Tutoring", 40.714175, -73.600418);
        Beacon beacon2 = new Beacon("NYU", "HackNY", 40.729554, -73.996450);
        Beacon beacon3 = new Beacon("Chris Davie", "Programming Party", 40.733757, -74.003370);

        final Beacon[] testBeacons = new Beacon[]{beacon1, beacon2, beacon3};
        ArrayList<String> beaconTitles = new ArrayList<String>();

//        LatLng testLoc = new LatLng(beacon1.latitude, beacon1.longitude);

        //populate arraylist
        for (int i = 0; i < testBeacons.length; i++) {
            beaconTitles.add(testBeacons[i].title);
        }

        ArrayAdapter<String> itemsAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, beaconTitles);
        ListView beaconList = (ListView) findViewById(R.id.beacon_list_view);
        beaconList.setAdapter(itemsAdapter);

        beaconList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = ((TextView) view).getText().toString();
                double latCoord = 0.0, longCoord = 0.0;
                //match item with title (redundant?)
                for(int i=0;i<testBeacons.length;i++){
                    if (item == testBeacons[i].title) {
                        latCoord = testBeacons[i].latitude;
                        longCoord = testBeacons[i].longitude;
                    }
                }
                goBack(view,latCoord,longCoord,item);

            }

        });
        } //end onCreate

    public class getBeacons extends AsyncTask<Void,Void,Void> {
        @Override
        protected Void doInBackground(Void...arg0) {
            ServiceHandler sh = new ServiceHandler();
            String url = "http://localhost:8080/webapi/API/";

            String jsonStr = sh.makeServiceCall(url, ServiceHandle.GET);

            if(jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject();

                    beacons = jsonObj.getJSONArray();
                }
            }
        }
    }

    public void goBack(View view, double lat, double longit,String title) {
        Intent intent = new Intent(this, MapsActivity.class);
        intent.putExtra("lat",lat);
        intent.putExtra("longit",longit);
        intent.putExtra("title",title);
        startActivity(intent);
        finish();
    }

    public void BTN_TEST_DATABASE(View view){
        //display json string
    }


    public class Beacon {
        public String creator;
        public String title;
        public double latitude;
        public double longitude;

        public Beacon(String c, String t, double lat, double longit) {
            this.creator = c;
            this.title = t;
            this.latitude = lat;
            this.longitude = longit;
        }
    }
}
