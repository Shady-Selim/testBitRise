package com.example.myapplication

import android.R.attr.bitmap
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition


class Palette : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_palette)

        val imageView: ImageView = findViewById<View>(R.id.myimage) as ImageView
        Glide.with(this)
                .asBitmap()
                .load("https://lh6.ggpht.com/9SZhHdv4URtBzRmXpnWxZcYhkgTQurFuuQ8OR7WZ3R7fyTmha77dYkVvcuqMu3DLvMQ=w300")
                .into(
                    object : CustomTarget<Bitmap>() {
                        override fun onResourceReady(
                            resource: Bitmap,
                            transition: Transition<in Bitmap>?
                        ) {
                            imageView.setImageBitmap(resource)
                            //createPaletteAsync(resource)
                            val p: Palette = createPaletteSync(resource)
                            val vibrantSwatch: Palette.Swatch? = checkVibrantSwatch(p)
                            val layout = findViewById<ConstraintLayout>(R.id.mylayout)
                            layout.setBackgroundColor(vibrantSwatch?.rgb!!)

                        }

                        override fun onLoadCleared(placeholder: Drawable?) {
                            // this is called when imageView is cleared on lifecycle call or for
                            // some other reason.
                            // if you are referencing the bitmap somewhere else too other than this imageView
                            // clear it here as you can no longer have the bitmap
                        }
                    })
    }

    fun createPaletteSync(bitmap: Bitmap): Palette {
        return Palette.from(bitmap).generate()
    }


    // Return a palette's vibrant swatch after checking that it exists
    private fun checkVibrantSwatch(p: Palette): Palette.Swatch? {
        return p.vibrantSwatch
    }

    private fun createPaletteAsync(bitmap: Bitmap) {
        Palette.from(bitmap).generate { palette ->
            // Use generated instance
            val defaultValue = 0x000000
            val vibrantLight: Int = palette!!.getLightVibrantColor(defaultValue)
            /*val vibrant: Int = palette!!.getVibrantColor(defaultValue)
            val vibrantDark: Int = palette.getDarkVibrantColor(defaultValue)
            val muted: Int = palette.getMutedColor(defaultValue)
            val mutedLight: Int = palette.getLightMutedColor(defaultValue)
            val mutedDark: Int = palette.getDarkMutedColor(defaultValue)

            val vibrantSwatch: Palette.Swatch? = palette.vibrantSwatch*/

            val layout = findViewById<ConstraintLayout>(R.id.mylayout)
            layout.setBackgroundColor(vibrantLight)
        }
    }
}