package io.drdroid.assignment2.holders

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import co.lujun.androidtagview.TagContainerLayout
import io.drdroid.assignment2.R

class ShowHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var thumbnail: ImageView
    var name: TextView
    var language: TextView
    var period: TextView
    var summary: TextView
    var network: TextView
    var tags: TagContainerLayout
    var linearlayout: LinearLayout

    init {
        thumbnail = itemView.findViewById(R.id.thumbnail)
        name = itemView.findViewById(R.id.name)
        language = itemView.findViewById(R.id.language)
        period = itemView.findViewById(R.id.period)
        summary = itemView.findViewById(R.id.summary)
        network = itemView.findViewById(R.id.network)
        tags = itemView.findViewById(R.id.tags)
        linearlayout = itemView.findViewById(R.id.info_container)
    }

}
