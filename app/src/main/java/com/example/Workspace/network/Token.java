package com.example.Workspace.network;

import java.io.Serializable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Token implements Serializable, Parcelable
{

    @SerializedName("x-auth-token")
    @Expose
    private String xAuthToken;
    public final static Parcelable.Creator<Token> CREATOR = new Creator<Token>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Token createFromParcel(Parcel in) {
            return new Token(in);
        }

        public Token[] newArray(int size) {
            return (new Token[size]);
        }

    }
            ;

    protected Token(Parcel in) {
        this.xAuthToken = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     *
     */
    public Token() {
    }

    /**
     *
     * @param xAuthToken
     */
    public Token(String xAuthToken) {
        super();
        this.xAuthToken = xAuthToken;
    }

    public String getXAuthToken() {
        return xAuthToken;
    }

    public void setXAuthToken(String xAuthToken) {
        this.xAuthToken = xAuthToken;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(xAuthToken);
    }

    public int describeContents() {
        return 0;
    }

}