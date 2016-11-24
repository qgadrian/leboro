package com.leboro.model.team.info;


import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.api.client.util.Lists;
import com.leboro.model.team.Team;
import com.leboro.model.team.info.roster.TeamRoster;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.List;

public class TeamInfo extends Team implements Parcelable, Comparable<TeamInfo> {

    private String arenaName;

    private String arenaAddress;

    private long id;

    private List<TeamRoster> teamRoster = Lists.newArrayList();

    public TeamInfo(String name, String logoUrl, String arenaName, String arenaAddress, long id) {
        super(name, logoUrl);
        this.arenaName = arenaName;
        this.arenaAddress = arenaAddress;
        this.id = id;
    }

    public TeamInfo(Parcel in) {
        super(in);
        arenaName = in.readString();
        arenaAddress = in.readString();
        id = in.readLong();
    }

    public String getArenaName() {
        return arenaName;
    }

    public void setArenaName(String arenaName) {
        this.arenaName = arenaName;
    }

    public String getArenaAddress() {
        return arenaAddress;
    }

    public void setArenaAddress(String arenaAddress) {
        this.arenaAddress = arenaAddress;
    }

    public long getId() {
        return id;
    }

    public List<TeamRoster> getTeamRoster() {
        return teamRoster;
    }

    public void setTeamRoster(List<TeamRoster> teamRoster) {
        this.teamRoster = teamRoster;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        TeamInfo teamInfo = (TeamInfo) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(id, teamInfo.id)
                .append(arenaName, teamInfo.arenaName)
                .append(arenaAddress, teamInfo.arenaAddress)
                .append(teamRoster, teamInfo.teamRoster)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode())
                .append(arenaName)
                .append(arenaAddress)
                .append(id)
                .append(teamRoster)
                .toHashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(arenaName);
        dest.writeString(arenaAddress);
        dest.writeLong(id);
    }

    @Override
    public int compareTo(@NonNull TeamInfo teamInfo) {
        return CompareToBuilder.reflectionCompare(getName(), teamInfo.getName());
    }
}
