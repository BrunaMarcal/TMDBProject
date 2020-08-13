package br.com.brunamarcal.tmdbproject.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.brunamarcal.tmdbproject.R
import br.com.brunamarcal.tmdbproject.data.database.modeldb.FavoriteMovie
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_image_profile.view.*

class ProfileAdapter(val list: List<Int>, val clickImage: ((image: Int) -> Unit)): RecyclerView.Adapter<ProfileAdapter.AdapterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_image_profile, parent, false)
        return AdapterViewHolder(itemView, clickImage)
    }

    override fun getItemCount() = list.count()

    override fun onBindViewHolder(holder: AdapterViewHolder, position: Int) {
       holder.bind(list[position])
    }

    class AdapterViewHolder(itemView: View, val clickImage: ((image: Int) -> Unit)): RecyclerView.ViewHolder(itemView){
        private val imageProfile = itemView.imageProfile
        private val picasso = Picasso.get()

         fun bind(image: Int){
             picasso.load(image).into(imageProfile)
             itemView.setOnClickListener {
                 clickImage.invoke(image)
             }
         }

    }
}