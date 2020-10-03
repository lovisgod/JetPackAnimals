package com.example.animals.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.animals.R
import com.example.animals.model.Animal

class AnimalListAdaptar(private  val animalList: ArrayList<Animal>):
    RecyclerView.Adapter<AnimalListAdaptar.AnimalViewHolder>() {
    class AnimalViewHolder(var view: View): RecyclerView.ViewHolder(view) {
        val name = view.findViewById<TextView>(R.id.animalName)
        val image = view.findViewById<ImageView>(R.id.animalImage)
    }

    fun updateAnimalList(newAnimalList: List<Animal>) {
        animalList.clear()
        animalList.addAll(newAnimalList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalViewHolder { // we are basically creating a viewholder for our element
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_animal, parent, false)
        return AnimalViewHolder(view)
    }

    override fun onBindViewHolder(holder: AnimalViewHolder, position: Int) {
        holder.name.text = animalList.get(position).name
    }

    override fun getItemCount(): Int = animalList.size
}