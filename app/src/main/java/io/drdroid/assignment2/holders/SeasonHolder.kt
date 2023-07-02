package io.drdroid.assignment2.holders

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.drdroid.assignment2.R

class SeasonHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var container: LinearLayout
    var thumbnail: ImageView
    var season: TextView

    init {
        container = itemView.findViewById(R.id.info_container)
        thumbnail = itemView.findViewById(R.id.thumbnail)
        season = itemView.findViewById(R.id.season_number)
    }

}
