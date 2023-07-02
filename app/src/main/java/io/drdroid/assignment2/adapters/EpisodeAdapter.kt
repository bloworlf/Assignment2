package io.drdroid.assignment2.adapters

import android.R.attr.height
import android.R.attr.width
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Matrix
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import io.drdroid.assignment2.R
import io.drdroid.assignment2.holders.EpisodeHolder
import io.drdroid.assignment2.models.data.EpisodeModel
import io.drdroid.assignment2.utils.PaletteUtils
import io.drdroid.assignment2.utils.Utils


class EpisodeAdapter(var context: Context, var list: List<EpisodeModel>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var domColor: Int = 0
    private val expanded: BooleanArray = BooleanArray(list.size)

    init {
        expanded.fill(false)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.model_episode, parent, false)
        return EpisodeHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(h0: RecyclerView.ViewHolder, position: Int) {
        val episode = list[position]
        val holder = h0 as EpisodeHolder
        val pos = position

        holder.name.text = episode.name
        holder.episode_number.text = episode.number.toString()
        holder.runtime.text = "${episode.runtime} min"
        episode.summary?.let {
            holder.summary.visibility = View.VISIBLE
            holder.summary.text = Html.fromHtml(episode.summary)
        }
        if (expanded[pos]) {
            holder.summary.maxLines = 100
            holder.summary.setCompoundDrawables(
                null,
                null,
                ResourcesCompat.getDrawable(context.resources, R.drawable.collapse, null),
                null
            )
            holder.summary.setCompoundDrawablesWithIntrinsicBounds(
                null,
                null,
                ResourcesCompat.getDrawable(context.resources, R.drawable.collapse, null),
                null
            )


//                holder.summary.setCompoundDrawables(
//                    null,
//                    null,
//                    rotateDrawable((holder.summary.compoundDrawables[2] as BitmapDrawable).bitmap),
//                    null
//                )
        } else {
            holder.summary.maxLines = 2
            holder.summary.setCompoundDrawables(
                null,
                null,
                ResourcesCompat.getDrawable(context.resources, R.drawable.expand, null),
                null
            )
            holder.summary.setCompoundDrawablesWithIntrinsicBounds(
                null,
                null,
                ResourcesCompat.getDrawable(context.resources, R.drawable.expand, null),
                null
            )
        }
        holder.summary.setOnClickListener {
            var oldPos: Int = expanded.indexOf(true)
            if (oldPos != -1 && oldPos != pos) {
                expanded[oldPos] = false
                notifyItemChanged(oldPos)
            }
            expanded[pos] = !expanded[pos]
            notifyItemChanged(pos)
        }

        holder.itemView.setOnClickListener {
            Utils.openUrl(context, episode.url, domColor)
        }

        episode.image?.let {
            Glide.with(context)
                .load(Uri.parse(episode.image.medium))
                .into(holder.thumbnail)

            Glide.with(context).asBitmap().load(Uri.parse(episode.image.original)).centerCrop()
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
//                        secColor = PaletteUtils.getLowerSideDominantColor(
//                            resource
//                        )

                    }
                })
        }
    }

    private fun rotateDrawable(bitmap: Bitmap): Drawable {
        val matrix = Matrix()

        matrix.postRotate(90F)
        val scaledBitmap = Bitmap.createScaledBitmap(bitmap, width, height, true)

        val rotatedBitmap = Bitmap.createBitmap(
            scaledBitmap,
            0,
            0,
            scaledBitmap.width,
            scaledBitmap.height,
            matrix,
            true
        )
        return BitmapDrawable(context.resources, rotatedBitmap)
    }

}