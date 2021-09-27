package com.example.parliamentproject.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.parliamentproject.R

/**
 * A Fragment subclass used for displaying basic information of the user's recently viewed member of parliament,
 * the time and date when the Room Database was the last time updated. The Fragment also includes a welcome text.
*/
class MainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_main, container, false)
    }
}