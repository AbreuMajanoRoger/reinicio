package com.example.practicaa

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.practicaa.databinding.ItemDogsBinding
import com.squareup.picasso.Picasso

class DogsViewHolder(view:View):RecyclerView.ViewHolder(view) {
    //private val binding = ItemDogsBinding.bind(view)
    private val imageView: ImageView

    init {
        imageView = view.findViewById(R.id.ivDogs)
    }

    fun bind(image:String){
        Picasso.get().load(image).into(imageView)

    }
}