package com.personal.finalproject.views

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.material.shape.CornerFamily
import com.personal.finalproject.R
import com.personal.finalproject.databinding.FragmentAboutBinding
import com.personal.finalproject.viewmodel.AccountViewModel

class AboutFragment : Fragment() {

    private var binding: FragmentAboutBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAboutBinding.inflate(layoutInflater, container, false)
        val radius: Float = requireContext().resources.getDimension(R.dimen.margin_10dp)

        binding!!.sivFotodiri.shapeAppearanceModel = binding!!.sivFotodiri.shapeAppearanceModel.toBuilder()
            .setAllCorners(CornerFamily.ROUNDED, radius).build()
        return binding!!.root
    }

}