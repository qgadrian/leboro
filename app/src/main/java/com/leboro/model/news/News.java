package com.leboro.model.news;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import android.os.Parcel;
import android.os.Parcelable;

public class News implements Parcelable {

    private String title;

    private String description;

    private String imageUrl;

    private String articleUrl;

    private String articleText;

    public News(String title, String description, String imageUrl, String articleUrl) {
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.articleUrl = articleUrl;
    }

    protected News(Parcel in) {
        title = in.readString();
        description = in.readString();
        imageUrl = in.readString();
        articleUrl = in.readString();
        articleText = in.readString();
    }

    public static final Creator<News> CREATOR = new Creator<News>() {
        @Override
        public News createFromParcel(Parcel in) {
            return new News(in);
        }

        @Override
        public News[] newArray(int size) {
            return new News[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getArticleUrl() {
        return articleUrl;
    }

    public void setArticleUrl(String articleUrl) {
        this.articleUrl = articleUrl;
    }

    public String getArticleText() {
        return articleText;
    }

    public void setArticleText(String articleText) {
        this.articleText = articleText;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        News news = (News) o;

        return new EqualsBuilder()
                .append(title, news.title)
                .append(description, news.description)
                .append(imageUrl, news.imageUrl)
                .append(articleUrl, news.articleUrl)
                .append(articleText, news.articleText)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(title)
                .append(description)
                .append(imageUrl)
                .append(articleUrl)
                .append(articleText)
                .toHashCode();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(imageUrl);
        dest.writeString(articleUrl);
        dest.writeString(articleText);
    }
}
