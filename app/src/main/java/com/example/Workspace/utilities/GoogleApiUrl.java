package com.example.Workspace.utilities;

public class GoogleApiUrl {

    /**
     * Google Api Url Constants
     */
    //https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=30.0516033,31.2584125&radius=5000&type=coworkspace&key=AIzaSyAQRniidcoWLf7Olv2mbgfL71OSQIKeY0M

    public static final String BASE_URL = "https://maps.googleapis.com/maps/api/place/";
    public static final String NEARBY_SEARCH_TAG = "nearbysearch";
    public static final String JSON_FORMAT_TAG = "json";
    public static final String LOCATION_TAG = "location";
    public static final String RADIUS_TAG = "radius";
    public static final String RADIUS_VALUE = "5000";
    public static final String PLACE_TYPE_TAG = "type";
    public static final String NEXT_PAGE_TOKEN_TAG = "pagetoken";
    public static final String API_KEY_TAG = "key";
    public static final String RANK_BY_TAG = "rankby";
    public static final String DISTANCE_TAG = "distance";
    public static final String KEYWORD_TAG = "keyword";
    public static final String LOCATION_DETAIL_TAG = "details";
    public static final String LOCATION_PLACE_ID_TAG = "placeid";
    public static final String API_KEY = "AIzaSyCoe32EtamXk4AQvmYg3so-OA1LkuNMISU";

    /**
     * Intent data transfer key
     */

    public static final String LOCATION_TYPE_EXTRA_TEXT = "pharmacy";
    public static final String LOCATION_keyword_EXTRA_TEXT = "coworking_space";
    public static final String LOCATION_NAME_EXTRA_TEXT = "location_name";
    public static final String ALL_NEARBY_LOCATION_KEY = "all_nearby_location_key";
    public static final String LOCATION_ID_EXTRA_TEXT = "location_id";
    public static final String CURRENT_LOCATION_DATA_KEY = "current_location_data";
    public static final String CURRENT_LOCATION_SHARED_PREFERENCE_KEY = "shared_preference_key";
    public static final String CURRENT_LOCATION_USER_RATING_KEY = "current_location_user_rating";
}
