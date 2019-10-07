package com.example.Workspace.network;

import android.media.Image;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.ContactsContract;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.Workspace.utilities.GoogleApiUrl;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by iamcs on 2017-04-29.
 */

public class Workspace implements Parcelable {

    public static final Parcelable.Creator<Workspace> CREATOR = new Parcelable.Creator<Workspace>() {

        @Override
        public Workspace createFromParcel(Parcel source) {
            return new Workspace(source);
        }

        @Override
        public Workspace[] newArray(int size) {
            return new Workspace[size];
        }
    };
    /**
     * All Reference Variable
     */
    private String mPlaceId;
    private Double mPlaceLatitude;
    private Double mPlaceLongitude;
    private String mPlaceName;
    private String mPlaceOpeningHourStatus;
    private Double mPlaceRating;
    private String mPlaceAddress;
    private String photo_reference;
    private String mPlacePhoneNumber;
    private String mPlaceWebsite;
    private String mPlaceShareLink;
    String height;

    public String getPhoto_reference() {//https://maps.googleapis.com/maps/api/place/photo?maxheight=720
        return "https://maps.googleapis.com/maps/api/place/photo?maxheight="+height+"&photoreference="+photo_reference+
                "&key="+ GoogleApiUrl.API_KEY;
    }

    public void setPhoto_reference(String photo_reference) {
        this.photo_reference = photo_reference;
    }


    /**
     * @param mPlaceId                Workspace Id
     * @param mPlaceLatitude          Workspace Latitude
     * @param mPlaceLongitude         Workspace Longitude
     * @param mPlaceName              Workspace Name
     * @param mPlaceOpeningHourStatus Workspace Opening Status Weather it is Open or Close
     * @param mPlaceRating            Workspace rating example 4.5
     * @param mPlaceAddress           Workspace Address
     */

    public Workspace(String mPlaceId, Double mPlaceLatitude, Double mPlaceLongitude,
                     String mPlaceName, String mPlaceOpeningHourStatus,
                     Double mPlaceRating, String mPlaceAddress,String photo_reference,String height) {

        this.mPlaceId = mPlaceId;
        this.mPlaceLatitude = mPlaceLatitude;
        this.mPlaceLongitude = mPlaceLongitude;
        this.mPlaceName = mPlaceName;
        this.mPlaceOpeningHourStatus = mPlaceOpeningHourStatus;
        this.mPlaceRating = mPlaceRating;
        this.mPlaceAddress = mPlaceAddress;
        this.photo_reference=photo_reference;
        this.height=height;
    }

    /**
     * @param mPlaceId                Workspace Id
     * @param mPlaceLatitude          Workspace Latitude
     * @param mPlaceLongitude         Workspace Longitude
     * @param mPlaceName              Workspace Name
     * @param mPlaceAddress           Workspace Address
     * @param mPlacePhoneNumber       Workspace Phone number
     * @param mPlaceOpeningHourStatus Workspace open status
     * @param mPlaceWebsite           Workspace Website
     * @param mPlaceShareLink         Workspace Sharing link to direct Social Media ex WhatsApp
     */
    public Workspace(String mPlaceId, Double mPlaceLatitude, Double mPlaceLongitude,
                     String mPlaceName, String mPlaceOpeningHourStatus,
                     Double mPlaceRating, String mPlaceAddress,
                     String mPlacePhoneNumber, String mPlaceWebsite,
                     String mPlaceShareLink,String photo_reference,String height) {

        this.mPlaceId = mPlaceId;
        this.mPlaceLatitude = mPlaceLatitude;
        this.mPlaceLongitude = mPlaceLongitude;
        this.mPlaceName = mPlaceName;
        this.mPlaceRating = mPlaceRating;
        this.mPlaceAddress = mPlaceAddress;
        this.photo_reference=photo_reference;
        this.height=height;
        this.mPlaceOpeningHourStatus = mPlaceOpeningHourStatus;
        this.mPlacePhoneNumber = mPlacePhoneNumber;
        this.mPlaceWebsite = mPlaceWebsite;
        this.mPlaceShareLink = mPlaceShareLink;
    }

    public Workspace(String mPlaceId, Double currentPlaceLatitude, Double mPlaceLatitude, String currentPlaceName, String currentPlaceOpeningHourStatus, Double mPlaceLongitude, String currentPlaceAddress, String photoreference) {
        this.mPlaceId = mPlaceId;
        this.mPlaceLatitude = mPlaceLatitude;
        this.mPlaceLongitude = mPlaceLongitude;
    }

    /**
     * Retrieving Workspace data from Parcel object
     * This constructor is invoked by the method createFromParcel(Parcel source) of
     * the object CREATOR
     **/

    private Workspace(Parcel in) {
        this.mPlaceId = in.readString();
        this.mPlaceLatitude = in.readDouble();
        this.mPlaceLongitude = in.readDouble();
        this.mPlaceName = in.readString();
        this.mPlaceOpeningHourStatus = in.readString();
        this.mPlaceRating = in.readDouble();
        this.photo_reference=in.readString();
        this.mPlaceAddress = in.readString();
        this.height=in.readString();
    }

    public String getPlaceId() {
        return mPlaceId;
    }

    public void setPlaceId(String placeId) {
        mPlaceId = placeId;
    }

    public Double getPlaceLatitude() {
        return mPlaceLatitude;
    }

    public void setPlaceLatitude(Double placeLatitude) {
        mPlaceLatitude = placeLatitude;
    }

    public Double getPlaceLongitude() {
        return mPlaceLongitude;
    }

    public void setPlaceLongitude(Double placeLongitude) {
        mPlaceLongitude = placeLongitude;
    }

    public String getPlaceName() {
        return mPlaceName;
    }

    public void setPlaceName(String placeName) {
        mPlaceName = placeName;
    }

    public String getPlaceOpeningHourStatus() {
        return mPlaceOpeningHourStatus;
    }

    public void setPlaceOpeningHourStatus(String placeOpeningHourStatus) {
        mPlaceOpeningHourStatus = placeOpeningHourStatus;
    }

    public Double getPlaceRating() {
        return mPlaceRating;
    }

    public void setPlaceRating(Double placeRating) {
        mPlaceRating = placeRating;
    }

    public String getPlaceAddress() {
        return mPlaceAddress;
    }

    public void setPlaceAddress(String placeAddress) {
        mPlaceAddress = placeAddress;
    }

    public String getPlacePhoneNumber() {
        return mPlacePhoneNumber;
    }

    public void setPlacePhoneNumber(String placePhoneNumber) {
        mPlacePhoneNumber = placePhoneNumber;
    }

    public String getPlaceWebsite() {
        return mPlaceWebsite;
    }

    public void setPlaceWebsite(String placeWebsite) {
        mPlaceWebsite = placeWebsite;
    }

    public String getPlaceShareLink() {
        return mPlaceShareLink;
    }

    public void setPlaceShareLink(String placeShareLink) {
        mPlaceShareLink = placeShareLink;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Storing the Workspace data to Parcel object
     **/
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mPlaceId);
        dest.writeDouble(mPlaceLatitude);
        dest.writeDouble(mPlaceLongitude);
        dest.writeString(mPlaceName);
        dest.writeString(mPlaceOpeningHourStatus);
        dest.writeDouble(mPlaceRating);
        dest.writeString(mPlaceAddress);
        dest.writeString(photo_reference);
        dest.writeString(height);
    }

}
