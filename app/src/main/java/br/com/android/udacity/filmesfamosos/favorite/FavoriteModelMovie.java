package br.com.android.udacity.filmesfamosos.favorite;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "favorite")
public class FavoriteModelMovie {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte[] image;

    private String title;
    private String description;
    private String dateRelease;
    private Double voteAverage;

    @Ignore
    public FavoriteModelMovie(byte[] image, String title, String description, String dateRelease, Double voteAverage) {
        this.image = image;
        this.title = title;
        this.description = description;
        this.dateRelease = dateRelease;
        this.voteAverage = voteAverage;

    }

    public FavoriteModelMovie(int id, byte[] image, String title,
                              String description, String dateRelease, Double voteAverage) {
        this.id = id;
        this.image = image;
        this.title = title;
        this.description = description;
        this.dateRelease = dateRelease;
        this.voteAverage = voteAverage;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDateRelease() {
        return dateRelease;
    }

    public void setDateRelease(String dateRelease) {
        this.dateRelease = dateRelease;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }
}
