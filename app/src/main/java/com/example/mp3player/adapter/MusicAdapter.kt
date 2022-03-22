package com.example.mp3player.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.mp3player.R
import com.example.mp3player.databinding.ItemListBinding
import com.example.mp3player.models.Song


class MusicAdapter(var context:Context,var list: List<Song>,val rvClick: RvClick): RecyclerView.Adapter<MusicAdapter.VH>() {

    inner class VH(private var itemRV: ItemListBinding):RecyclerView.ViewHolder(itemRV.root){
        var image = itemRV.itemPhoto
        fun onBind(song: Song,position: Int){
            itemRV.tvItemAuthor.text = song.artist
            itemRV.tvItemName.text = song.title

            itemRV.root.setOnClickListener {
                rvClick.itemClick(song, position)

            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(ItemListBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }


    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBind(list[position],position)
        Glide.with(context).load(list[position].artUri).apply(RequestOptions().placeholder(
            R.drawable.photo)).into(holder.image)

    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface RvClick{
        fun itemClick(song: Song,position: Int)

    }
}