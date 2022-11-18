package com.personal.finalproject.views

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.personal.finalproject.R
import com.personal.finalproject.databinding.FragmentSplashScreenBinding


class SplashScreenFragment : Fragment() {

    private var binding: FragmentSplashScreenBinding? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSplashScreenBinding.inflate(layoutInflater, container, false)
        // Inflate the layout for this fragment
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val frag: View = requireActivity().findViewById(R.id.main_fragment)
        val navController = Navigation.findNavController(frag)
        Handler(Looper.getMainLooper()).postDelayed({
            navController.navigate(SplashScreenFragmentDirections
                .actionSplashScreenFragmentToRootFragment())
        },3000)
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
    }
}