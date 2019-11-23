package com.bumptech.glide.load.resource.bitmap;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.bumptech.glide.load.engine.bitmap_recycle.d;
import com.bumptech.glide.load.engine.p;
import com.bumptech.glide.load.f;
import com.bumptech.glide.load.g;
import com.bumptech.glide.load.resource.b.e;

/* compiled from: ResourceBitmapDecoder */
public class t implements g<Uri, Bitmap> {
    private final d bm;
    private final e mH;

    public t(e eVar, d dVar) {
        this.mH = eVar;
        this.bm = dVar;
    }

    public boolean a(@NonNull Uri uri, @NonNull f fVar) {
        return "android.resource".equals(uri.getScheme());
    }

    @Nullable
    /* renamed from: b */
    public p<Bitmap> a(@NonNull Uri uri, int i, int i2, @NonNull f fVar) {
        p<Drawable> b2 = this.mH.a(uri, i, i2, fVar);
        if (b2 == null) {
            return null;
        }
        return o.a(this.bm, b2.get(), i, i2);
    }
}
