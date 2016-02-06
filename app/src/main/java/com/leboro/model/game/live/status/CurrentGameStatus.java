package com.leboro.model.game.live.status;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class CurrentGameStatus {

    private final int quarter;

    private final String timeLeft;

    public CurrentGameStatus(int quarter, String timeLeft) {
        this.quarter = quarter;
        this.timeLeft = timeLeft;
    }

    public int getQuarter() {
        return quarter;
    }

    public String getTimeLeft() {
        return timeLeft;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CurrentGameStatus that = (CurrentGameStatus) o;

        return new EqualsBuilder()
                .append(quarter, that.quarter)
                .append(timeLeft, that.timeLeft)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(quarter)
                .append(timeLeft)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("quarter", quarter)
                .append("timeLeft", timeLeft)
                .toString();
    }
}
