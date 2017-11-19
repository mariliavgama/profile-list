package com.example.mariliavgama.profilelist.data;
import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Immutable model class for an Item.
 */
@Entity(tableName = "items")
public final class Item {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "entryid")
    private final String mId;

    @Nullable
    @ColumnInfo(name = "real_name")
    private final String mRealName;

    @Nullable
    @ColumnInfo(name = "name")
    private final String mName;

    @Nullable
    @ColumnInfo(name = "tz")
    private final String mTz;

    @Nullable
    @ColumnInfo(name = "tz_label")
    private final String mTzLabel;

    @Nullable
    @ColumnInfo(name = "image")
    private final String mImage;

    @Nullable
    @ColumnInfo(name = "real_name_normalized")
    private final String mRealNameNormalized;

    @Nullable
    @ColumnInfo(name = "title")
    private final String mTitle;

    @Nullable
    @ColumnInfo(name = "team")
    private final String mTeam;

    @Nullable
    @ColumnInfo(name = "phone")
    private final String mPhone;

    @Nullable
    @ColumnInfo(name = "color")
    private final String mColor;

   /**
     * Use this constructor to create a new Item.
     *
     * @param id                    id
     * @param realName              real name
     * @param name                  name (username)
     * @param tz                    timezone
     * @param tzLabel               timezone label
     * @param image                 image
     * @param realNameNormalized    real name to display in profile picture
     * @param title                 title
     * @param team                  team
     * @param phone                 phone
     * @param color                 color
     */
    public Item(@NonNull String id, @Nullable String realName, @Nullable String name,
                @Nullable String tz, @Nullable String tzLabel, @Nullable String image,
                @Nullable String realNameNormalized, @Nullable String title,
                @Nullable String team, @Nullable String phone, @Nullable String color) {
        mId = id;
        mRealName = realName;
        mName = name;
        mTz = tz;
        mTzLabel = tzLabel;
        mImage = image;
        mRealNameNormalized = realNameNormalized;
        mTitle = title;
        mTeam = team;
        mPhone = phone;
        mColor = color;
    }

    @NonNull
    public String getId() {
        return mId;
    }

    @Nullable
    public String getRealName() {
        return mRealName;
    }

    @Nullable
    public String getName() {
        return mName;
    }

    @Nullable
    public String getTz() {
        return mTz;
    }

    @Nullable
    public String getTzLabel() {
        return mTzLabel;
    }

    @Nullable
    public String getImage() {
        return mImage;
    }

    @Nullable
    public String getRealNameNormalized() {
        return mRealNameNormalized;
    }

    @Nullable
    public String getTitle() {
        return mTitle;
    }

    @Nullable
    public String getTeam() {
        return mTeam;
    }

    @Nullable
    public String getPhone() {
        return mPhone;
    }

    @Nullable
    public String getColor() {
        return mColor;
    }

    @Override
    public String toString() {
        return "Item with name " + mRealName;
    }
}