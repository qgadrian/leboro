package com.leboro.model.team;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.google.common.base.Objects;

import android.os.Parcel;
import android.os.Parcelable;

public class Team implements Parcelable {

    private final String logoUrl;

    private final String name;

    public Team(String name, String logoUrl) {
        this.name = name;
        this.logoUrl = logoUrl;
    }

    protected Team(Parcel in) {
        logoUrl = in.readString();
        name = in.readString();
    }

    public static final Creator<Team> CREATOR = new Creator<Team>() {
        @Override
        public Team createFromParcel(Parcel in) {
            return new Team(in);
        }

        @Override
        public Team[] newArray(int size) {
            return new Team[size];
        }
    };

    public String getLogoUrl() {
        return logoUrl;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Team team = (Team) o;

        return new EqualsBuilder()
                .append(logoUrl, team.logoUrl)
                .append(name, team.name)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(logoUrl)
                .append(name)
                .toHashCode();
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("name", name)
                .toString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(logoUrl);
        dest.writeString(name);
    }
}
