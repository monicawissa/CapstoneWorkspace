package com.example.Workspace.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
//import retrofit2.Response;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.Workspace.R;
import com.example.Workspace.adapter.RecyclerViewAdapter;
import com.example.Workspace.network.Workspace;
import com.example.Workspace.ui.auth.loginActivity;
import com.example.Workspace.utilities.GoogleApiUrl;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerView_dAdapter;
    public List<Workspace> listItems = new ArrayList<>();
    private ArrayList<Workspace> places=new ArrayList<>();
    private  String  token;
    private FirebaseAuth mAuth;

    private ArrayList<Workspace> mNearByPlaceArrayList = new ArrayList<>();

    private String mLocationTag;
    private String mLocationName;
    private RequestQueue mQueue;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();

        new networkConnection().execute();

        mQueue = Volley.newRequestQueue(this);
        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        getPlaces();

    }

    private class networkConnection extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... voids) {

            ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext()
                    .getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (!aBoolean) //not connected
                Snackbar.make(recyclerView ,"No internet connection",
                        Snackbar.LENGTH_SHORT).show();
        }
    }

    private void getPlaces() {
        String currentLocation = getSharedPreferences(
                GoogleApiUrl.CURRENT_LOCATION_SHARED_PREFERENCE_KEY, 0)
                .getString(GoogleApiUrl.CURRENT_LOCATION_DATA_KEY, null);

        String mLocationQueryStringUrl = GoogleApiUrl.BASE_URL + GoogleApiUrl.NEARBY_SEARCH_TAG + "/" +
                GoogleApiUrl.JSON_FORMAT_TAG + "?" + GoogleApiUrl.LOCATION_TAG + "=" +
                currentLocation + "&" + GoogleApiUrl.RADIUS_TAG + "=" +
                GoogleApiUrl.RADIUS_VALUE + "&" + GoogleApiUrl.KEYWORD_TAG + "=" + GoogleApiUrl.LOCATION_keyword_EXTRA_TEXT +
                "&" + GoogleApiUrl.API_KEY_TAG + "=" + GoogleApiUrl.API_KEY;
        Log.d("taggg", mLocationQueryStringUrl);


        getLocationOnGoogleMap(mLocationQueryStringUrl);
    }
    private void getLocationOnGoogleMap(String locationQueryStringUrl) {
        //Tag to cancel request
        String jsonArrayTag = "jsonArrayTag";
        JsonObjectRequest placeJsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                locationQueryStringUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray rootJsonArray = response.getJSONArray("results");
                            if (rootJsonArray.length() == 0)
                                Toast.makeText(getApplicationContext(), R.string.no_near_by_tag, Toast.LENGTH_LONG).show();

                            else {
                                for (int i = 0; i < rootJsonArray.length(); i++) {
                                    JSONObject singlePlaceJsonObject = (JSONObject) rootJsonArray.get(i);

                                    String currentPlaceId = singlePlaceJsonObject.getString("place_id");
                                    Double currentPlaceLatitude = singlePlaceJsonObject
                                            .getJSONObject("geometry").getJSONObject("location")
                                            .getDouble("lat");
                                    Double currentPlaceLongitude = singlePlaceJsonObject
                                            .getJSONObject("geometry").getJSONObject("location")
                                            .getDouble("lng");
                                    String currentPlaceName = singlePlaceJsonObject.getString("name");
                                    String currentPlaceOpeningHourStatus = singlePlaceJsonObject
                                            .has("opening_hours") ? singlePlaceJsonObject
                                            .getJSONObject("opening_hours").has("open_now")?singlePlaceJsonObject
                                            .getJSONObject("opening_hours")
                                            .getString("open_now"):"Status Not Available" : "Status Not Available";
                                    Double currentPlaceRating = singlePlaceJsonObject.has("rating") ?
                                            singlePlaceJsonObject.getDouble("rating") : 0;
                                    String currentPlaceAddress = singlePlaceJsonObject.has("vicinity") ?
                                            singlePlaceJsonObject.getString("vicinity") :
                                            "Address Not Available";
                                    String photoreference = singlePlaceJsonObject.has("photos") ?
                                            ((JSONObject) singlePlaceJsonObject.getJSONArray("photos").get(0)).getString("photo_reference") :
                                            "";
                                    String height=singlePlaceJsonObject.has("photos") ?((JSONObject) singlePlaceJsonObject.
                                            getJSONArray("photos").get(0)).getString("height"):"200";
                                    Workspace singlePlaceDetail = new Workspace(
                                            currentPlaceId,
                                            currentPlaceLatitude,
                                            currentPlaceLongitude,
                                            currentPlaceName,
                                            currentPlaceOpeningHourStatus,
                                            currentPlaceRating,
                                            currentPlaceAddress,
                                            photoreference,height
                                    );
                                    mNearByPlaceArrayList.add(singlePlaceDetail);
                                }
                                recyclerView.setAdapter(new RecyclerViewAdapter(mNearByPlaceArrayList, getApplicationContext()));
                                recyclerView.smoothScrollToPosition(0);


                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.d("taggg","erreoResponse");
            }
        });
        //Adding request to request queue
        mQueue.add(placeJsonObjectRequest);
        //AppController.getInstance().addToRequestQueue(placeJsonObjectRequest, jsonArrayTag);
    }

    public void sign_out(View view) {
        mAuth.signOut();
        Intent intent=new Intent(getApplicationContext(), loginActivity.class);
        startActivity(intent);
        finish();
    }
}
