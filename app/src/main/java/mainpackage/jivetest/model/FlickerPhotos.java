package mainpackage.jivetest.model;

import android.os.Parcel;
import android.os.Parcelable;

public class FlickerPhotos implements Parcelable{

    private String id; //photoId
    private String owner; //userId
    private String secret;
    private String server;
    private String farm;
    private String title;


    protected FlickerPhotos(Parcel in) {
        id = in.readString();
        owner = in.readString();
        secret = in.readString();
        server = in.readString();
        farm = in.readString();
        title = in.readString();
    }

    public static final Creator<FlickerPhotos> CREATOR = new Creator<FlickerPhotos>() {
        @Override
        public FlickerPhotos createFromParcel(Parcel in) {
            return new FlickerPhotos(in);
        }

        @Override
        public FlickerPhotos[] newArray(int size) {
            return new FlickerPhotos[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getFarm() {
        return farm;
    }

    public void setFarm(String farm) {
        this.farm = farm;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(owner);
        dest.writeString(secret);
        dest.writeString(server);
        dest.writeString(farm);
        dest.writeString(title);
    }
}
