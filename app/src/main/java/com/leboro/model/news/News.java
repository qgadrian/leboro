package com.leboro.model.news;

import java.util.Date;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.leboro.R;
import com.leboro.util.properties.PropertiesHelper;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import static com.leboro.util.Constants.NEWS_COBROTHERS_PLAYLIST;

public class News implements Parcelable, Comparable<News> {

    public enum NewsKind {
        FEB_ARTICLE(R.string.news_provider_feb, R.color.news_provider_feb_color),
        COBROTHERS_YOUTUBE_VIDEO(R.string.news_provider_cobrothers, R.color.news_provider_cobrothers_color,
                PropertiesHelper.getProperty(NEWS_COBROTHERS_PLAYLIST)),
        ZONA_DE_BASQUET_ARTICLE(R.string.news_provider_zona_de_basquet, R.color.news_provider_zona_de_basquet_color);

        private final int providerNameResourceId;

        private final int providerLabelBackgroundColorId;

        private final String providerValue;

        NewsKind(int providerNameResourceId, int providerLabelBackgroundColorId) {
            this.providerNameResourceId = providerNameResourceId;
            this.providerLabelBackgroundColorId = providerLabelBackgroundColorId;
            this.providerValue = null;
        }

        NewsKind(int providerNameResourceId, int providerLabelBackgroundColorId, String providerValue) {
            this.providerNameResourceId = providerNameResourceId;
            this.providerLabelBackgroundColorId = providerLabelBackgroundColorId;
            this.providerValue = providerValue;
        }

        public int getProviderNameResourceId() {
            return providerNameResourceId;
        }

        public String getProviderValue() {
            return providerValue;
        }

        public int getProviderLabelBackgroundColorId() {
            return providerLabelBackgroundColorId;
        }
    }

    private Date publicationDate;

    private String title;

    private String description;

    private String imageUrl;

    private String articleUrl;

    private String articleText;

    private final NewsKind kind;

    public News(String title, String description, String imageUrl, String articleUrl, NewsKind kind,
            Date publicationDate) {
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.articleUrl = articleUrl;
        this.kind = kind;
        this.publicationDate = publicationDate;
    }

    protected News(Parcel in) {
        title = in.readString();
        description = in.readString();
        imageUrl = in.readString();
        articleUrl = in.readString();
        articleText = in.readString();
        kind = NewsKind.valueOf(in.readString());
        publicationDate = new Date(in.readLong());
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

    public NewsKind getKind() {
        return kind;
    }

    public Date getPublicationDate() {
        return publicationDate;
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
                .append(kind, news.kind)
                .append(publicationDate, news.publicationDate)
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
                .append(kind)
                .append(publicationDate)
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
        dest.writeString(kind.name());
        dest.writeLong(publicationDate.getTime());
    }

    // TODO: Check why RelfectionCompare is returng 0 for dates, until then...
    @Override
    public int compareTo(@NonNull News another) {
        if (publicationDate == null) {
            if (another.publicationDate == null) {
                return 0;
            } else {
                return 1;
            }
        } else if (another.publicationDate == null) {
            return -1;
        } else {
            return another.publicationDate.compareTo(publicationDate);
        }
    }
}
