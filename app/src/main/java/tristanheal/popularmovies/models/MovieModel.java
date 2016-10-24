package tristanheal.popularmovies.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by tsheal on 17/10/2016.
 */

public class MovieModel extends RealmObject implements Parcelable {

    @PrimaryKey
    public int Id;

    public String PosterPath;
    public String OriginalTitle;
    public String Overview;
    public Double VoteAverage;
    public Date ReleaseDate;

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(Id);
        dest.writeString(PosterPath);
        dest.writeString(OriginalTitle);
        dest.writeString(Overview);
        dest.writeDouble(VoteAverage);
        dest.writeLong(ReleaseDate.getTime());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    private static MovieModel readParcel(Parcel in) {

        MovieModel movie = new MovieModel();
        movie.Id = in.readInt();
        movie.PosterPath = in.readString();
        movie.OriginalTitle = in.readString();
        movie.Overview = in.readString();
        movie.VoteAverage = in.readDouble();
        movie.ReleaseDate = new Date(in.readLong());

        return movie;

    }

    public static final Parcelable.Creator<MovieModel> CREATOR = new Parcelable.Creator<MovieModel>() {

        @Override
        public MovieModel[] newArray(int size) {
            return new MovieModel[size];
        }

        @Override
        public MovieModel createFromParcel(Parcel source) {

            return readParcel(source);
        }
    };
}
