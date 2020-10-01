package com.example.animals.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.animals.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ListFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btn : FloatingActionButton = view.findViewById(R.id.btnDetail)
        btn.setOnClickListener {
            val action = ListFragmentDirections.actionGotoDetail()
            Navigation.findNavController(it).navigate(action)

        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            ListFragment()
    }
}