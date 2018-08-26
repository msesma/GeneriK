package eu.sesma.generik.api.images

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target


abstract class ImageTarget : Target {
    override fun onBitmapLoaded(bitmap: Bitmap, from: Picasso.LoadedFrom) {
    }

    override fun onBitmapFailed(errorDrawable: Drawable?) {
    }

    override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
    }
}