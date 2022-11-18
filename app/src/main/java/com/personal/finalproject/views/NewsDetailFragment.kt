package com.personal.finalproject.views

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.personal.finalproject.R
import com.personal.finalproject.databinding.FragmentNewsDetailBinding
import com.personal.finalproject.models.Articles
import com.personal.finalproject.utility.DateConversion
import java.io.OutputStream


class NewsDetailFragment : Fragment() {

    private var binding: FragmentNewsDetailBinding? = null
    private var detailNews: Articles? = null

    @SuppressLint("IntentReset")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewsDetailBinding.inflate(layoutInflater, container, false)
        detailNews = NewsDetailFragmentArgs.fromBundle(requireArguments()).newsData
        setValue(detailNews)

        binding!!.actionShare.setOnClickListener {
            Glide.with(requireContext())
                .asBitmap()
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .load(detailNews?.urlToImage)
                .centerCrop()
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        startActivity(
                            Intent.createChooser(Intent().apply {
                                type = "*/*"
                                action = Intent.ACTION_SEND
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                                    putExtra(Intent.EXTRA_STREAM, saveImage(resource))
                                } else {
                                    putExtra(Intent.EXTRA_STREAM, saveImageOld(resource))
                                }
                                putExtra(
                                    Intent.EXTRA_TEXT,
                                    "${detailNews?.title} \n ${detailNews?.url}"
                                )
                                flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                            }, "Share via")
                        )
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {

                    }
                })
        }

        return binding!!.root
    }

    private fun setValue(detailNews: Articles?) {
        val circularProgressDrawable = CircularProgressDrawable(requireContext())
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()

        binding!!.apply {
            Glide.with(requireContext())
                .asBitmap()
                .load(detailNews?.urlToImage)
                .centerCrop()
                .placeholder(circularProgressDrawable)
                .error(R.drawable.ic_baseline_broken_image_24)
                .into(imageView)

            tvAuthor.text = detailNews?.author ?: "No author"
            tvDate.text =
                DateConversion.convertDateToText(detailNews?.publishedAt) ?: "No date published"
            tvTitle.text = detailNews?.title ?: "No title"
            tvContent.text = detailNews?.content ?: "No Content"
        }
    }

    private fun saveImage(bitmap: Bitmap): Uri? {
        val filename = "${detailNews?.title}.jpg"
        var fos: OutputStream? = null
        var imageUri: Uri? = null
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                put(MediaStore.Video.Media.IS_PENDING, 1)
            }
        }
        val contentResolver = requireContext().contentResolver
        contentResolver.also { resolver ->
            imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
            fos = imageUri?.let { resolver.openOutputStream(it) }
        }
        fos?.use {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, it)
        }
        contentValues.clear()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            contentValues.put(MediaStore.Video.Media.IS_PENDING, 0)
        }
        contentResolver.update(imageUri!!, contentValues, null, null)
        return imageUri
    }

    private fun saveImageOld(bitmap: Bitmap): Uri{
        val contentResolver = requireContext().contentResolver
        val path = MediaStore.Images.Media.insertImage(
            contentResolver,
            bitmap,
            "",
            null
        )
        return Uri.parse(path)
    }
}