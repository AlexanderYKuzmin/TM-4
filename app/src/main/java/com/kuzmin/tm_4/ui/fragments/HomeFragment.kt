package com.kuzmin.tm_4.ui.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.kuzmin.tm_4.R

class HomeFragment : Fragment() {


    private var removeActionbarBackArrow: (() -> Unit)? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d("remove arrow", "on attach")
        if (context is AppCompatActivity) removeActionbarBackArrow = {
            Log.d("remove arrow", "remove arrow invoked")
            context.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("remove arrow", "on create")

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("remove arrow", "on create view")
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onResume() {
        removeActionbarBackArrow?.invoke()
        super.onResume()
    }
}