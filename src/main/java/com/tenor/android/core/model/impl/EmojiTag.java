package com.tenor.android.core.model.impl;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;
import com.tenor.android.core.constant.StringConstant;

import java.io.Serializable;

/**
 * The model of {@link EmojiTag}
 */
public class EmojiTag implements Serializable {
    private static final long serialVersionUID = -2207206185861282031L;
    @SerializedName("character")
    private String unicodeChars;
    @SerializedName("name")
    private String searchName;
    @SerializedName("path")
    private String imgUrl;
    @SerializedName("searchterm")
    private String searchTerm;

    /**
     * @return String of characters that make up the emoji in unicode form
     */
    @NonNull
    public String getUnicodeChars() {
        return StringConstant.getOrEmpty(unicodeChars);
    }

    /**
     * @return full text of corresponding search word, for display purposes
     */
    @NonNull
    public String getSearchName() {
        return StringConstant.getOrEmpty(searchName);
    }

    /**
     * @return url of the api search endpoint corresponding to the emoji
     */
    @NonNull
    public String getImgUrl() {
        return StringConstant.getOrEmpty(imgUrl);
    }

    /**
     * @return full text of corresponding search word
     */
    @NonNull
    public String getSearchTerm() {
        return StringConstant.getOrEmpty(searchTerm);
    }
}