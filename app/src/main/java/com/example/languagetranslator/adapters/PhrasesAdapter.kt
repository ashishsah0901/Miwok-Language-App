package com.example.languagetranslator.adapters

import android.content.Context
import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.languagetranslator.data.Phrase
import com.example.languagetranslator.databinding.ItemPhrasesBinding
import com.example.languagetranslator.fragments.PhrasesFragment

class PhrasesAdapter(private val context: Context, private val items: ArrayList<Phrase>, private val listener: PhrasesAdapterListener): RecyclerView.Adapter<PhrasesAdapter.PhrasesViewHolder>() {
    inner class PhrasesViewHolder(val binding: ItemPhrasesBinding): RecyclerView.ViewHolder(binding.root)
    private var mediaPlayer: MediaPlayer? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhrasesViewHolder {
        return PhrasesViewHolder(ItemPhrasesBinding.inflate(LayoutInflater.from(context),parent,false))
    }

    override fun onBindViewHolder(holder: PhrasesViewHolder, position: Int) {
        holder.binding.engPhraseTv.text = items[position].eng
        holder.binding.lanPhraseTv.text = items[position].lan
        holder.binding.phrasesLayout.setOnClickListener {
            listener.onItemClicked(items[position].music)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}
interface PhrasesAdapterListener{
    fun onItemClicked(id: Int)
}