package io.drdroid.assignment2.holders

import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.drdroid.assignment2.R

class GenreHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var block: RelativeLayout
    var genre: TextView

    init {
        block = itemView.findViewById(R.id.block)
        genre = itemView.findViewById(R.id.genre)
    }
}