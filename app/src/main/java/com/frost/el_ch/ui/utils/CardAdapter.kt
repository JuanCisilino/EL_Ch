package com.frost.el_ch.ui.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.frost.el_ch.databinding.CardItemBinding
import com.frost.el_ch.model.Card
import com.squareup.picasso.Picasso

class CardAdapter(private val cardlist: List<Card>) : RecyclerView.Adapter<CardAdapter.ViewHolder>(){

    inner class ViewHolder(val binding: CardItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardAdapter.ViewHolder {
        val binding = CardItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CardAdapter.ViewHolder, position: Int) {
        with(holder){
            with(cardlist[position]) {
                binding.numbtextView.text = number
                binding.exptextView.text = expiration
            }
        }
    }

    override fun getItemCount() = cardlist.size

}