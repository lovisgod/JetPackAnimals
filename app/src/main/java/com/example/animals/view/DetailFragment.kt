package com.example.animals.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import com.example.animals.R
import com.example.animals.model.Animal
import com.example.animals.util.getProgressDrawable
import com.example.animals.util.loadImage
import com.google.android.material.floatingactionbutton.FloatingActionButton


class DetailFragment : Fragment() {
    var animal: Animal?  = null
    private lateinit var animalImageView: ImageView
    private lateinit var animamName: TextView
    private lateinit var animamLocation: TextView
    private lateinit var animamLifeSpan: TextView
    private lateinit var animamDiet: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        animalImageView = view.findViewById(R.id.animalImage)
        animamName = view.findViewById(R.id.animalName)
        animamDiet = view.findViewById(R.id.animalDiet)
        animamLifeSpan = view.findViewById(R.id.animalLifespan)
        animamLocation = view.findViewById(R.id.animalLocation)
        arguments?.let {
            animal = DetailFragmentArgs.fromBundle(it).animal
        }

        animal?.imageUrl?.let { context?.let { getProgressDrawable(it) }?.let { it1 ->
            animalImageView.loadImage(it,
                it1
            )
        } }
        animamName.text = animal?.name
        animamLifeSpan.text = animal?.lifeSpan
        animamLocation.text = animal?.location
        animamDiet.text = animal?.diet


    }



    companion object {

        @JvmStatic
        fun newInstance() = DetailFragment()
    }
}