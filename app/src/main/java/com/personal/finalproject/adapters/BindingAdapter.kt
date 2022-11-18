package com.personal.finalproject.adapters

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.shape.CornerFamily
import com.personal.finalproject.R
import com.personal.finalproject.utility.DateConversion


@BindingAdapter("imageNews")
fun imageNews(spImageView: ShapeableImageView, source: String?){
    val circularProgressDrawable = CircularProgressDrawable(spImageView.context)
    circularProgressDrawable.strokeWidth = 5f
    circularProgressDrawable.centerRadius = 30f
    circularProgressDrawable.start()

    val radius: Float = spImageView.context.resources.getDimension(R.dimen.radius_5dp)

    spImageView.shapeAppearanceModel = spImageView.shapeAppearanceModel.toBuilder()
        .setAllCorners(CornerFamily.ROUNDED, radius).build()

    if(source != null || source != ""){
        Glide.with(spImageView.context)
            .load(source)
            .centerCrop()
            .placeholder(circularProgressDrawable)
            .error(R.drawable.ic_baseline_broken_image_24)
            .into(spImageView)
    }
}

@BindingAdapter("dateConversion")
fun TextView.dateConversion(data: String?){
    if(data != null){
        text = DateConversion.convertDateToText(data)
    }
}