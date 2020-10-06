package com.example.animals.view

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.animals.R
import com.example.animals.databinding.FragmentDetailBinding
import com.example.animals.model.Animal
import com.example.animals.model.AnimalPalette
import com.example.animals.util.getProgressDrawable
import com.example.animals.util.loadImage
import com.google.android.material.floatingactionbutton.FloatingActionButton


class DetailFragment : Fragment() {
    var animal: Animal?  = null
    private lateinit var binding: FragmentDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_detail, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            animal = DetailFragmentArgs.fromBundle(it).animal
            binding.animal = animal
        }

        setUpBackgroundColor(animal?.imageUrl)


    }

    private fun setUpBackgroundColor(imageUrl: String?) {
        Glide.with(this)
            .asBitmap()
            .load(imageUrl)
            .into(object: CustomTarget<Bitmap> () {
                override fun onResourceReady(resource: Bitmap,
                                             transition: Transition<in Bitmap>?) {
                    Palette.from(resource)
                        .generate() {
                            val intColor = it?.lightMutedSwatch?.rgb ?: 0
                            binding.palette = AnimalPalette(intColor)
                        }
                }

                override fun onLoadCleared(placeholder: Drawable?) {

                }

            })
    }


    companion object {

        @JvmStatic
        fun newInstance() = DetailFragment()
    }
}