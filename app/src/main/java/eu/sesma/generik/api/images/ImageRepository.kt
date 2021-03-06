package eu.sesma.generik.api.images

import eu.sesma.generik.R
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import javax.inject.Inject

class ImageRepository
@Inject
constructor(private val picasso: Picasso) {

    fun getCurrentIcon(url: String, iconTarget: Target) = picasso.load(url)
            .resizeDimen(R.dimen.main_icon_width, R.dimen.main_icon_height)
            .into(iconTarget)

}
