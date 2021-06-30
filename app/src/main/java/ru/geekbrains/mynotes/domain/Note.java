package ru.geekbrains.mynotes.domain;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.StringRes;

import java.util.Date;

public class Note implements Parcelable {

    private String id;
    private Date date;
    private String title;
    private String description;

    public Note(String id, Date date, String title, String description){

        this.id = id;
        this.date = date;
        this.title = title;
        this.description = description;
    }

    protected Note(Parcel in) {
        id = in.readString();
        date = new Date(date.getTime());
        title = in.readString();
        description = in.readString();
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };


    public String getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeLong(date.getTime());
        dest.writeString(title);
        dest.writeString(description);
    }
}
