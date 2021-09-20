package ru.chebertests.nasaphoto.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.card_mars_photo.view.*
import ru.chebertests.nasaphoto.R
import ru.chebertests.nasaphoto.model.remote.PhotoMarsResponse

class MarsPicturesRecyclerViewAdapter :
    RecyclerView.Adapter<MarsPicturesRecyclerViewAdapter.MarsViewHolder>() {

    private var data: List<PhotoMarsResponse> = listOf()

    fun setData(newData: List<PhotoMarsResponse>) {
        data = newData
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarsViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.card_mars_photo, parent, false)
        return MarsViewHolder(view)
    }

    override fun onBindViewHolder(holder: MarsViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

    inner class MarsViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(photoMars: PhotoMarsResponse) {
            Glide
                .with(view.mars_image_view)
                .load(photoMars.imgSrc)
                .into(view.mars_image_view)
        }

    }
}