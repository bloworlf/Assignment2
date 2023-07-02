package io.drdroid.assignment2.holders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.drdroid.assignment2.R

class EpisodeHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var thumbnail: ImageView
    var name: TextView
    var runtime: TextView
    var summary: TextView
    var episode_number: TextView

    init {
        thumbnail = itemView.findViewById(R.id.thumbnail)
        name = itemView.findViewById(R.id.name)
        runtime = itemView.findViewById(R.id.runtime)
        summary = itemView.findViewById(R.id.summary)
        episode_number = itemView.findViewById(R.id.episode_number)
    }
}
