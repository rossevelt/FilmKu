package com.yosua.filmku.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.yosua.filmku.R
import com.yosua.filmku.activities.DetailMovieActivity
import com.yosua.filmku.api.Api
import com.yosua.filmku.model.MainModel

class MainAdapter(private val context: Context, private val mainModel: ArrayList<MainModel>):
    RecyclerView.Adapter<MainAdapter.GridViewHolder>(){
    class GridViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var ivPoster: ImageView = itemView.findViewById(R.id.imageItemPoster)
        var tvName: TextView = itemView.findViewById(R.id.textItemName)
        var lGridUpcoming: LinearLayout = itemView.findViewById(R.id.lGridUpcoming)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): GridViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_content_main, viewGroup, false)
        return GridViewHolder(view)
    }

    override fun onBindViewHolder(holder: GridViewHolder, position: Int) {
        val data = mainModel[position]
        Glide.with(context)
            .load(Api.poster+data.imgSrc)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(holder.ivPoster)
        holder.tvName.text =  data.txtName
        holder.lGridUpcoming.setOnClickListener{
            val intent = Intent(context, DetailMovieActivity::class.java)
            intent.putExtra(DetailMovieActivity.DETAIL_UPCOMING, mainModel[position])
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return mainModel.size
    }

}