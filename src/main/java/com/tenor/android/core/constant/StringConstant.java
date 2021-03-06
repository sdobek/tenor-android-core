package com.tenor.android.core.constant;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.tenor.android.core.util.AbstractListUtils;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

/**
 * The frequently used {@link String} constants
 */
public abstract class StringConstant {
    /**
     * ""
     */
    public static final String EMPTY = "";
    /**
     * " "
     */
    public static final String SPACE = " ";
    /**
     * "#"
     */
    public static final String HASH = "#";
    /**
     * ","
     */
    public static final String COMMA = ",";
    /**
     * "."
     */
    public static final String DOT = ".";
    /**
     * "-"
     */
    public static final String DASH = "-";
    /**
     * ":"
     */
    public static final String COLON = ":";
    /**
     * "@"
     */
    public static final String AT = "@";
    /**
     * "UNKNOWN"
     */
    public static final String UNKNOWN = "UNKNOWN";
    /**
     * "/"
     */
    public static final String SLASH = "/";
    /**
     * "|"
     */
    public static final String PIPE = "|";
    /**
     * \n
     */
    public static final String NEW_LINE = "\n";
    /**
     * "UTF-8"
     */
    public static final String UTF8 = "UTF-8";

    /**
     * Get string from given package with given resource name
     *
     * @param context      given context
     * @param packageName  the package name
     * @param resourceName the resource name
     * @return the string that the resource represents
     */
    @NonNull
    public static String getString(@NonNull final Context context,
                                   @NonNull final String packageName,
                                   @NonNull final String resourceName) {

        PackageManager pm = context.getPackageManager();

        try {
            //I want to use the clear_activities string in Package com.android.settings
            Resources res = pm.getResourcesForApplication(packageName);
            int resourceId = res.getIdentifier(packageName + ":string/" + resourceName, null, null);
            if (resourceId != 0) {
                return pm.getText(packageName, resourceId, null).toString();
            }
        } catch (Exception ignored) {
        }
        return EMPTY;
    }

    /**
     * Generate String in UTF8 format
     *
     * @param string initial String
     * @return UTF8 encoded String
     */
    @NonNull
    public static String encode(@NonNull final String string) {
        return encode(string, UTF8);
    }

    /**
     * Generate String in given format, UTF8 if none is specified
     *
     * @param string initial String
     * @param format specified encoding format
     * @return encoded String
     */
    @NonNull
    public static String encode(@NonNull final String string, @Nullable final String format) {
        if (TextUtils.isEmpty(string)) {
            return EMPTY;
        }

        try {
            return URLEncoder.encode(string,
                    !TextUtils.isEmpty(format) ? format : UTF8);
        } catch (Throwable ignored) {
            return string;
        }
    }

    /**
     * Generate String in UTF8 format
     *
     * @param string initial String
     * @return UTF8 encoded String
     */
    @NonNull
    public static String decode(@NonNull final String string) {
        return decode(string, UTF8);
    }

    /**
     * Generate String in given format, UTF8 if none is specified
     *
     * @param string initial String
     * @param format specified encoding format
     * @return encoded String
     */
    @NonNull
    public static String decode(@NonNull final String string, @Nullable final String format) {
        if (TextUtils.isEmpty(string)) {
            return EMPTY;
        }

        try {
            return URLDecoder.decode(string,
                    !TextUtils.isEmpty(format) ? format : UTF8);
        } catch (Throwable ignored) {
            return string;
        }
    }

    public interface IJoinable<T> {
        String getJoinableString(T t);
    }

    public static <T> String join(@Nullable final List<T> words,
                                  @Nullable final String separator,
                                  @Nullable final IJoinable<T> joinable) {

        if (AbstractListUtils.isEmpty(words) || joinable == null) {
            return EMPTY;
        }

        if (separator == null) {
            join(words, EMPTY, joinable);
        }

        String word = joinable.getJoinableString(words.get(0));
        StringBuilder sb = new StringBuilder(word);
        final int count = words.size();
        for (int i = 1; i < count; i++) {
            word = joinable.getJoinableString(words.get(i));
            sb.append(separator);
            sb.append(word);
        }
        return sb.toString();
    }

    public static String join(@Nullable final List<String> words,
                              @Nullable final String separator) {

        return join(words, separator, new IJoinable<String>() {
            @Override
            public String getJoinableString(String s) {
                return s;
            }
        });
    }

    public static <T> T getOrDef(@Nullable T t, @NonNull T def) {
        return t != null ? t : def;
    }

    public static String getOrEmpty(@Nullable String str) {
        return getOrDef(str, EMPTY);
    }

    public static CharSequence getOrEmpty(@Nullable CharSequence str) {
        return getOrDef(str, EMPTY);
    }

    /**
     * Parse the given {@link String} into int
     *
     * @param str    the string
     * @param defVal the default value
     */
    public static int parse(@Nullable final String str, final int defVal) {
        if (TextUtils.isEmpty(str) || !TextUtils.isDigitsOnly(str)) {
            return defVal;
        }

        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException ignored) {
            return defVal;
        }
    }

    /**
     * Save {@code content} into device clipboard with {@code label}
     */
    public static boolean copy(@NonNull final Context context,
                               @NonNull final String label,
                               @NonNull final String content) {
        if (TextUtils.isEmpty(label) || TextUtils.isEmpty(content)) {
            return false;
        }

        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        clipboard.setPrimaryClip(ClipData.newPlainText(label, content));
        return true;
    }
}
