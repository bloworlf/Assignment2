package io.drdroid.assignment2.adapters

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import io.drdroid.assignment2.R
import io.drdroid.assignment2.holders.SeasonHolder
import io.drdroid.assignment2.interfaces.SeasonListener
import io.drdroid.assignment2.models.data.SeasonModel
import io.drdroid.assignment2.utils.PaletteUtils
import io.drdroid.assignment2.utils.Utils
import io.drdroid.assignment2.utils.Utils.colorTransition
import io.drdroid.assignment2.utils.Utils.isDark

class SeasonAdapter(
    var context: Context,
    var list: List<SeasonModel>,
    var listener: SeasonListener
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val selected: BooleanArray = BooleanArray(list.size)

    init {
        selected.fill(false)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.model_season, parent, false)
        return SeasonHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(h0: RecyclerView.ViewHolder, position: Int) {
        val season = list[position]
        val holder = h0 as SeasonHolder
        val pos = position

        holder.season.text = "Season ${season.number}"

        var domColor: Int = 0
        var secColor: Int = 0

        if (!selected[pos]) {
            holder.container.colorTransition(Color.WHITE)
            holder.season.setTextColor(Color.BLACK)
        }

        season.image?.let {
            Glide.with(context)
                .load(Uri.parse(season.image.medium))
                .into(holder.thumbnail)

            Glide.with(context).asBitmap().load(Uri.parse(season.image.original)).centerCrop()
                .into(object : CustomTarget<Bitmap>() {
                    override fun onLoadCleared(placeholder: Drawable?) {
                    }

                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {

                        domColor = PaletteUtils.getUpperSideDominantColor(
                            resource
                        )
                        secColor = PaletteUtils.getLowerSideDominantColor(
                            resource
                        )

                    }
                })
        }

        holder.itemView.setOnClickListener {
            if (selected[pos]) {
                return@setOnClickListener
            }
            var oldPos: Int = selected.indexOf(true)
            if (oldPos != -1 && oldPos != pos) {
                selected[oldPos] = false
                holder.season.setTextColor(Color.BLACK)
                notifyItemChanged(oldPos)
            }
            selected[pos] = !selected[pos]

            holder.container.colorTransition(secColor)
            if (isDark(secColor)) {
                holder.season.setTextColor(Color.WHITE)
            } else {
                holder.season.setTextColor(Color.BLACK)
            }
            notifyItemChanged(pos)
            listener.onSeasonSelected(
                season.id, mapOf(
                    "dominant" to domColor,
                    "secondary" to secColor
                )
            )
        }
    }
}