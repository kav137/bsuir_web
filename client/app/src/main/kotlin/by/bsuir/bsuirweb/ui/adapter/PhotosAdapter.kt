package by.bsuir.bsuirweb.ui.adapter

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.Adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.bsuir.bsuirweb.R
import by.bsuir.bsuirweb.core.backend.entity.Photo

import kotlinx.android.synthetic.main.item_photo.*
import kotlinx.android.synthetic.main.item_photo.view.*

class PhotosAdapter(val photos: List<Photo>): Adapter<PhotosAdapter.PhotoViewHolder>() {


    override fun onBindViewHolder(holder: PhotoViewHolder?, position: Int) {
        holder?.bind(photos[position])
    }

    override fun getItemCount(): Int {
        return photos.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): PhotoViewHolder {
        return PhotoViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.item_photo, parent, false))
    }


    class PhotoViewHolder(view: View): RecyclerView.ViewHolder(view) {

        fun bind(photo: Photo) {
            itemView.date.text = String.format("Date:%s", photo.date)
            itemView.name.text = String.format("Name:%s", photo.name)
        }
    }
}