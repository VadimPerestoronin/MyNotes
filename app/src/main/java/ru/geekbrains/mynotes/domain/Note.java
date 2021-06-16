package ru.geekbrains.mynotes.domain;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.StringRes;

public class Note implements Parcelable {

    @StringRes
    private int date;

    @StringRes
    private int title;

    @StringRes
    private int description;

    public Note(int date, int title, int description){
        this.date = date;
        this.title = title;
        this.description = description;
    }

    protected Note(Parcel in) {
        date = in.readInt();
        title = in.readInt();
        description = in.readInt();
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

    public int getDate() {
        return date;
    }

    public int getTitle() {
        return title;
    }

    public int getDescription() {
        return description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(date);
        dest.writeInt(title);
        dest.writeInt(description);
    }
}