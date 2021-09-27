package ru.chebertests.nasaphoto.view.nasa_services

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.behavior.SwipeDismissBehavior
import kotlinx.android.synthetic.main.card_mars_photo.view.*
import ru.chebertests.nasaphoto.R
import ru.chebertests.nasaphoto.model.remote.PhotoMarsResponse
import ru.chebertests.nasaphoto.view.EquilateralImageView

class MarsPicturesRecyclerViewAdapter :
    RecyclerView.Adapter<MarsPicturesRecyclerViewAdapter.MarsViewHolder>() {

    private var data: MutableList<PhotoMarsResponse> = mutableListOf()

    fun setData(newData: List<PhotoMarsResponse>?) {
        data.clear()
        newData?.let {
            data.addAll(newData)
        }
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

        val swipe = SwipeDismissBehavior<EquilateralImageView>()

        fun bind(photoMars: PhotoMarsResponse) {

            swipe.setSwipeDirection(SwipeDismissBehavior.SWIPE_DIRECTION_START_TO_END)
            swipe.listener = object : SwipeDismissBehavior.OnDismissListener {
                override fun onDismiss(view: View?) {
                    val position = data.indexOf(photoMars)
                    data.remove(photoMars)
                    notifyItemRemoved(position)
                }

                override fun onDragStateChanged(state: Int) {}
            }
            val coordinatorParams =
                view.mars_image_view.layoutParams as CoordinatorLayout.LayoutParams
            coordinatorParams.behavior = swipe

            Glide
                .with(view.mars_image_view)
                .load(photoMars.imgSrc)
                .into(view.mars_image_view)
        }

    }
}