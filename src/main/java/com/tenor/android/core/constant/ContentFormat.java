package com.tenor.android.core.constant;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PACKAGE;
import static java.lang.annotation.ElementType.PARAMETER;

@Retention(RetentionPolicy.CLASS)
@StringDef({ContentFormats.IMAGE_GIF, ContentFormats.IMAGE_MP4,
        ContentFormats.IMAGE_JPEG, ContentFormats.IMAGE_PNG})
@Target({METHOD, PARAMETER, FIELD, ANNOTATION_TYPE, PACKAGE})
public @interface ContentFormat {
}
