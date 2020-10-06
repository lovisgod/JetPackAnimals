package com.example.animals.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.animals.R
import com.example.animals.databinding.ItemAnimalBinding
import com.example.animals.model.Animal
import com.example.animals.util.getProgressDrawable
import com.example.animals.util.loadImage

class AnimalListAdaptar(private  val animalList: ArrayList<Animal>):
    RecyclerView.Adapter<AnimalListAdaptar.AnimalViewHolder>(), AnimalClickListener {
    class AnimalViewHolder(var view: ItemAnimalBinding): RecyclerView.ViewHolder(view.root)

    fun updateAnimalList(newAnimalList: List<Animal>) {
        animalList.clear()
        animalList.addAll(newAnimalList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            AnimalViewHolder { // we are basically creating a viewholder for our element
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ItemAnimalBinding>(inflater,
            R.layout.item_animal, parent, false)
        return AnimalViewHolder(view)
    }

    override fun onBindViewHolder(holder: AnimalViewHolder, position: Int) {
        holder.view.animal = animalList[position]
        holder.view.listener = this
    }

    override fun getItemCount(): Int = animalList.size

    override fun onClick(v: View) {
      for (animal in animalList) {
          if (v.tag == animal.name) {
            val action = ListFragmentDirections.actionGotoDetail(animal)
            Navigation.findNavController(v).navigate(action)
          }
      }
    }
}