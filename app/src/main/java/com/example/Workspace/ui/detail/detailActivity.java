package com.example.Workspace.ui.detail;

import android.Manifest;
import android.annotation.SuppressLint;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.Workspace.R;
import com.example.Workspace.network.PlaceUserRating;
import com.example.Workspace.network.Workspace;
import com.example.Workspace.ui.auth.Registration;
import com.example.Workspace.utilities.GoogleApiUrl;
import com.example.Workspace.widget.Coworkspace_Widget;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.BottomNavigationView.OnNavigationItemSelectedListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class detailActivity extends AppCompatActivity {
    @BindView(R.id.place_profile)
    ImageView placeProfile;
    @BindView(R.id.place_name)
    TextView placeName;
    @BindView(R.id.rate_bar)
    RatingBar rateBar;
    @BindView(R.id.rating_from5)
    TextView ratingFrom5;
    @BindView(R.id.place_address)
    TextView placeAddress;
    @BindView(R.id.call_tv)
    TextView callTv;
    @BindView(R.id.place_favorite)
    ImageView place_favorite;
    @BindView(R.id.place_face)
    FloatingActionButton placeFace;
    @BindView(R.id.place_call)
    FloatingActionButton placeCall;
    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigation;
    @BindView(R.id.fragment)
    FrameLayout fragment;


    Workspace currentPlaceDetail;
    Bundle currentPlaceUserRatingDetail;
    Bundle placeAboutFragmentBundle;

    private String mCurrentPlaceDetailUrl;
    private ArrayList<PlaceUserRating> mPlaceUserRatingsArrayList = new ArrayList<>();

    String id;
    private RequestQueue mQueue;
    private String mPlaceShareUrl;
    public static final String SHARED_PREFS = "sharedPrefs";

    String json;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        id = getIntent().getStringExtra(GoogleApiUrl.LOCATION_ID_EXTRA_TEXT);
        Log.d("taggg", id);
        mCurrentPlaceDetailUrl = GoogleApiUrl.BASE_URL + GoogleApiUrl.LOCATION_DETAIL_TAG + "/" +
                GoogleApiUrl.JSON_FORMAT_TAG + "?" + GoogleApiUrl.LOCATION_PLACE_ID_TAG + "=" +
                id + "&" + GoogleApiUrl.API_KEY_TAG + "=" + GoogleApiUrl.API_KEY;
        Log.d("taggg", mCurrentPlaceDetailUrl);
        mQueue = Volley.newRequestQueue(this);
        getCurrentPlaceAllDetails(mCurrentPlaceDetailUrl);


    }

    private void getCurrentPlaceAllDetails(final String currentPlaceDetailUrl) {
        String jsonArrayTag = "jsonArrayTag";
        JsonObjectRequest placeJsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                currentPlaceDetailUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject rootJsonObject = response.getJSONObject("result");

                            String currentPlaceId = rootJsonObject.getString("place_id");
                            Double currentPlaceLatitude = rootJsonObject
                                    .getJSONObject("geometry").getJSONObject("location")
                                    .getDouble("lat");
                            Double currentPlaceLongitude = rootJsonObject
                                    .getJSONObject("geometry").getJSONObject("location")
                                    .getDouble("lng");
                            String currentPlaceName = rootJsonObject.getString("name");
                            String currentPlaceOpeningHourStatus = rootJsonObject
                                    .has("opening_hours") ? rootJsonObject
                                    .getJSONObject("opening_hours").has("open_now") ? rootJsonObject
                                    .getJSONObject("opening_hours")
                                    .getString("open_now") : "Status Not Available" : "Status Not Available";
                            if (currentPlaceOpeningHourStatus.equals("true"))
                                currentPlaceOpeningHourStatus = "Open";
                            else if (currentPlaceOpeningHourStatus.equals("false"))
                                currentPlaceOpeningHourStatus = "Closed";

                            Double currentPlaceRating = rootJsonObject.has("rating") ?
                                    rootJsonObject.getDouble("rating") : 0;
                            String currentPlaceAddress = rootJsonObject.has("formatted_address") ?
                                    rootJsonObject.getString("formatted_address") :
                                    "Address Not Available";
                            String currentPlacePhoneNumber = rootJsonObject
                                    .has("formatted_phone_number") ? rootJsonObject
                                    .getString("formatted_phone_number") :
                                    "";
                            String currentPlaceWebsite = rootJsonObject.has("website") ?
                                    rootJsonObject.getString("website") :
                                    "Website Not Registered";
                            String currentPlaceShareLink = rootJsonObject.getString("url");
                            mPlaceShareUrl = currentPlaceShareLink;
                            String photoreference = rootJsonObject.has("photos") ?
                                    ((JSONObject) rootJsonObject.getJSONArray("photos").get(0))
                                            .getString("photo_reference") :
                                    "";
                            String height = ((JSONObject) rootJsonObject.
                                    getJSONArray("photos").get(0)).getString("height");
                            currentPlaceDetail = new Workspace(
                                    currentPlaceId,
                                    currentPlaceLatitude,
                                    currentPlaceLongitude,
                                    currentPlaceName,
                                    currentPlaceOpeningHourStatus,
                                    currentPlaceRating,
                                    currentPlaceAddress,
                                    currentPlacePhoneNumber,
                                    currentPlaceWebsite, currentPlaceShareLink, photoreference, height);
                            if (rootJsonObject.has("reviews")) {

                                JSONArray reviewJsonArray = rootJsonObject.getJSONArray("reviews");
                                for (int i = 0; i < reviewJsonArray.length(); i++) {
                                    JSONObject currentUserRating = (JSONObject) reviewJsonArray.
                                            get(i);

                                    PlaceUserRating currentPlaceUserRating = new PlaceUserRating(
                                            currentUserRating.getString("author_name"),
                                            currentUserRating.getString("profile_photo_url"),
                                            currentUserRating.getDouble("rating"),
                                            currentUserRating.getString("relative_time_description"),
                                            currentUserRating.getString("text"));

                                    mPlaceUserRatingsArrayList.add(currentPlaceUserRating);
                                }
                            }
                            placeAboutFragmentBundle= new Bundle();
                            placeAboutFragmentBundle.putParcelable(
                                    GoogleApiUrl.CURRENT_LOCATION_DATA_KEY, currentPlaceDetail);

                            currentPlaceUserRatingDetail = new Bundle();
                            currentPlaceUserRatingDetail.putParcelableArrayList(
                                    GoogleApiUrl.CURRENT_LOCATION_USER_RATING_KEY,
                                    mPlaceUserRatingsArrayList);
                            setUI(currentPlaceDetail);


                        } catch (JSONException e) {
                            e.printStackTrace();
                            getCurrentPlaceAllDetails(currentPlaceDetailUrl);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("taggg", error.getMessage());
                    }
                });
        //Adding request to request queue
        mQueue.add(placeJsonObjectRequest);
    }

    private OnNavigationItemSelectedListener mOnNavigationItemSelectedListener =
            new OnNavigationItemSelectedListener() {

                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selsectedfrag = null;

                    switch (item.getItemId()) {
                        case R.id.nav_review:
                            selsectedfrag = new ReviewFragment();
                            selsectedfrag.setArguments(currentPlaceUserRatingDetail);
                            break;


                        case R.id.nav_location:
                            selsectedfrag = new LocationFragment();
                            selsectedfrag.setArguments(placeAboutFragmentBundle);

                            break;
                        default:
                            selsectedfrag = new ReviewFragment();
                            selsectedfrag.setArguments(currentPlaceUserRatingDetail);
                            break;

                    }
                    assert selsectedfrag != null;
                    // give the fragment  currentPlaceUserRatingDetail
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment, selsectedfrag).commit();
                    return true;
                }

            };


    private void setUI(Workspace currentPlaceDetail) {
        Log.d("taggg", "hi this is the photo reference =" + currentPlaceDetail.getPhoto_reference());
        Picasso.with(getApplicationContext())
                .load(R.drawable.coworkspace_logo)
                .into(placeProfile);
        placeAddress.setText(currentPlaceDetail.getPlaceAddress());
        callTv.setText(currentPlaceDetail.getPlacePhoneNumber());
        rateBar.setNumStars(currentPlaceDetail.getPlaceRating().intValue());
        ratingFrom5.setText("(" + currentPlaceDetail.getPlaceRating().doubleValue() + "/5)");
        placeName.setText(currentPlaceDetail.getPlaceName());
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        setfavorite_photo();

        Fragment selsectedfrag = new ReviewFragment();
        selsectedfrag.setArguments(currentPlaceUserRatingDetail);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, selsectedfrag).commit();
    }

    private void setfavorite_photo() {
        DatabaseReference myRef = FirebaseDatabase.getInstance()
                .getReference().child("favorites")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child(id);
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()==null){
                    place_favorite.setImageDrawable(getResources().getDrawable(R.drawable.not_favorite));
                }
                else{
                    place_favorite.setImageDrawable(getResources().getDrawable(R.drawable.favorite));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void makeCallToPlace() {
        Log.d("taggg", currentPlaceDetail.getPlacePhoneNumber());
        if (currentPlaceDetail.getPlacePhoneNumber() == null || currentPlaceDetail.getPlacePhoneNumber().isEmpty())
            Toast.makeText(getApplicationContext(), "Not Registered",
                    Toast.LENGTH_SHORT).show();
        else {
            try
            {
                if(Build.VERSION.SDK_INT > 22)
                {
                    if (checkSelfPermission( Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling

                        requestPermissions( new String[]{Manifest.permission.CALL_PHONE}, 101);

                        return;
                    }

                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + currentPlaceDetail.getPlacePhoneNumber()));
                    startActivity(callIntent);

                }
                else {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + currentPlaceDetail.getPlacePhoneNumber()));
                    startActivity(callIntent);
                }
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }
    }
    private void websiteofPlace() {
        if (currentPlaceDetail.getPlaceWebsite().charAt(0) != 'h')
            Toast.makeText(getApplicationContext(), "Not registered",
                    Toast.LENGTH_SHORT).show();
        else {
            Intent websiteUrlIntent = new Intent(Intent.ACTION_VIEW);
            websiteUrlIntent.setData(Uri.parse(currentPlaceDetail.getPlaceWebsite()));
            websiteUrlIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getApplicationContext().startActivity(websiteUrlIntent);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @OnClick({R.id.place_favorite, R.id.place_face, R.id.place_call})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.place_favorite:
                add_removeToFavorite();
                break;
            case R.id.place_face:
                websiteofPlace();
                break;
            case R.id.place_call:
                makeCallToPlace();
                break;

        }
    }

    private void add_removeToFavorite() {
        Log.d("taggg", placeName.getText().toString());
        if(placeName.getText().toString().equals("Sydney")){
            Toast.makeText(getApplicationContext(),"can't load the place data ;)",Toast.LENGTH_SHORT).show();
            return;
        }

        Log.d( "taggg","hi this is id ="+id);

        DatabaseReference myRef = FirebaseDatabase.getInstance()
                .getReference().child("favorites")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child(id);
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("taggg",dataSnapshot.getKey());
                if(dataSnapshot.getValue()==null){
                    myRef.setValue(currentPlaceDetail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                place_favorite.setImageDrawable(getResources().getDrawable(R.drawable.favorite));
                                Toast.makeText(detailActivity.this, "Add to Favorite", Toast.LENGTH_LONG).show();
                            } else {

                                Toast.makeText(detailActivity.this, "can't add workspace to favorite list." + task.getException(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else{
                    dataSnapshot.getRef().removeValue();
                    place_favorite.setImageDrawable(getResources().getDrawable(R.drawable.not_favorite));
                    Toast.makeText(detailActivity.this, "removed from Favorite", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
