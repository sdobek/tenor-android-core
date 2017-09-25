package com.tenor.android.core.loader;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.tenor.android.core.model.impl.Media;

public class GlideLoader {

    public static RequestBuilder applyDimens(@NonNull RequestBuilder requestBuilder,
                                             @NonNull RequestOptions requestOptions,
                                                    @NonNull GlideTaskParams payload) {
        final Media media = payload.getMedia();
        if (media != null) {
            requestOptions = requestOptions.override(media.getWidth(), media.getHeight());
        }
        requestBuilder.apply(requestOptions);
        return requestBuilder;
    }

    public static <T extends ImageView> void load(@NonNull final RequestBuilder requestBuilder,
                                                  @NonNull final RequestOptions requestOptions,
                                                  @NonNull final GlideTaskParams<T> payload) {

        if (payload.isThumbnail()) {
            requestBuilder.thumbnail(payload.getThumbnailMultiplier());
        }

        requestBuilder.apply(requestOptions.placeholder(payload.getPlaceholder()))
                .into(new DrawableImageViewTarget(payload.getTarget()) {
                    @Override
                    public void onLoadFailed(@Nullable Drawable errorDrawable) {
                        if (payload.getCurrentRetry() < payload.getMaxRetry()) {
                            payload.incrementCurrentRetry();
                            load(requestBuilder, requestOptions, payload);
                        } else {
                            super.onLoadFailed(errorDrawable);
                            payload.getListener().failure(payload.getTarget(), errorDrawable);
                        }
                    }

                    @Override
                    public void onResourceReady(Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        super.onResourceReady(resource, transition);
                        payload.getListener().success(payload.getTarget(), resource);
                    }
                });
    }
}
