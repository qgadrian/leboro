package com.leboro.model.team.info.roster;


import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Date;

public class TeamRoster {

    public enum RosterKind {
        COACH, PLAYER
    }

    private RosterKind rosterKind;

    private String name;

    private Integer number;

    private String imageUrl;

    private String position;

    private Date birthDate;

    private String country;

    private Integer height;

    public TeamRoster(RosterKind rosterKind, String name, Integer number, String imageUrl, String
            position, Date
            birthDate, String country, Integer height) {
        this.rosterKind = rosterKind;
        this.name = name;
        this.imageUrl = imageUrl;
        this.position = position;
        this.birthDate = birthDate;
        this.country = country;
        this.height = height;
        this.number = number;
    }

    public TeamRoster(RosterKind rosterKind, String name, String imageUrl) {
        this.rosterKind = rosterKind;
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public RosterKind getRosterKind() {
        return rosterKind;
    }

    public void setRosterKind(RosterKind rosterKind) {
        this.rosterKind = rosterKind;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getNumber() {
        return number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        TeamRoster that = (TeamRoster) o;

        return new EqualsBuilder()
                .append(rosterKind, that.rosterKind)
                .append(name, that.name)
                .append(number, that.number)
                .append(imageUrl, that.imageUrl)
                .append(position, that.position)
                .append(birthDate, that.birthDate)
                .append(country, that.country)
                .append(height, that.height)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(rosterKind)
                .append(name)
                .append(number)
                .append(imageUrl)
                .append(position)
                .append(birthDate)
                .append(country)
                .append(height)
                .toHashCode();
    }
}
