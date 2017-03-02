package com.example.Utils;

/**
 * Created by 李果 on 2016/5/5.
 */

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 李果 on 2016/5/5.
 */
public class Mp3Info implements Parcelable {
    private long id;
    private String artist;
    private String title;
    private long duration;
    private long size;
    private String url;

    public Mp3Info() {

    }

    public Mp3Info(long id, String artist, String title, long duration, long size, String url) {
        this.id = id;
        this.artist = artist;
        this.title = title;
        this.duration = duration;
        this.size = size;
        this.url = url;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeLong(duration);
        dest.writeLong(size);
        dest.writeString(artist);
        dest.writeString(title);
        dest.writeString(url);
    }

    public static final Parcelable.Creator<Mp3Info> CREATOR=new Creator<Mp3Info>() {
        @Override
        public Mp3Info createFromParcel(Parcel source) {
            Mp3Info mp3Info = new Mp3Info();
            mp3Info.id=source.readLong();
            mp3Info.duration=source.readLong();
            mp3Info.size=source.readLong();
            mp3Info.artist=source.readString();
            mp3Info.title=source.readString();
            mp3Info.url=source.readString();
            return mp3Info;
        }

        @Override
        public Mp3Info[] newArray(int size) {
            return new Mp3Info[size];
        }
    };
}

