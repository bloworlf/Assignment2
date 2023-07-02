package io.drdroid.assignment2.adapters

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import co.lujun.androidtagview.TagView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.gson.Gson
import io.drdroid.assignment2.R
import io.drdroid.assignment2.holders.ShowHolder
import io.drdroid.assignment2.models.data.ShowModel
import io.drdroid.assignment2.utils.PaletteUtils
import io.drdroid.assignment2.utils.Utils
import io.drdroid.assignment2.utils.Utils.colorTransition


class ShowAdapter(
    var context: Context,
    var list: MutableList<ShowModel>,
    var controller: NavController
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    init {
        hasStableIds()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.model_show, parent, false)
        return ShowHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemId(position: Int): Long {
        return list[position].id.toLong()
    }

    override fun onBindViewHolder(h0: RecyclerView.ViewHolder, position: Int) {
        val show = list[position]
        val holder = h0 as ShowHolder

        holder.name.text = show.name
        holder.language.text = show.language
        show.premiered?.let {
            holder.period.visibility = View.VISIBLE
            holder.period.text = "${show.premiered} : ${
                if (show.status == "Ended") {
                    show.ended
                } else {
                    "ongoing"
                }
            }"
        }

        show.summary?.let {
            holder.summary.visibility = View.VISIBLE
            holder.summary.text = Html.fromHtml(show.summary)
        }

        show.network?.let {
            holder.network.visibility = View.VISIBLE
            holder.network.text = show.network.name
        }
        holder.tags.tags = show.genres

        holder.tags.setOnTagClickListener(object : TagView.OnTagClickListener {
            override fun onTagClick(position: Int, text: String?) {
                //go to fragment for tag
                when (controller.currentDestination?.id) {
                    R.id.menu_search -> {
                        controller.navigate(
                            R.id.to_tags_frag,
                            bundleOf(
                                "tag" to text
                            )
                        )
                    }

                    R.id.tagFragment -> {
                        controller.navigate(
                            R.id.tag_to_tag,
                            bundleOf(
                                "tag" to text
                            )
                        )
                    }
                }

            }

            override fun onTagLongClick(position: Int, text: String?) {
                //
            }

            override fun onSelectedTagDrag(position: Int, text: String?) {
                //
            }

            override fun onTagCrossClick(position: Int) {
                //
            }
        })
        holder.itemView.setOnClickListener {

            when (controller.currentDestination?.id) {
                R.id.menu_search -> {
                    controller.navigate(
                        R.id.to_show_details_frag,
                        bundleOf(
                            "show" to Gson().toJson(show)
                        )
                    )
                }

                R.id.tagFragment -> {
                    controller.navigate(
                        R.id.tag_to_details,
                        bundleOf(
                            "show" to Gson().toJson(show)
                        )
                    )
                }
            }

        }

        show.image?.let {
            Glide.with(context)
                .load(Uri.parse(show.image.medium))
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.thumbnail)

            Glide.with(context).asBitmap().load(Uri.parse(show.image.original)).centerCrop()
                .into(object : CustomTarget<Bitmap>() {
                    override fun onLoadCleared(placeholder: Drawable?) {
                    }

                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {

                        val color: Int = PaletteUtils.getUpperSideDominantColor(
                            resource
                        )
                        holder.linearlayout.colorTransition(color)

                        if (Utils.isDark(color)) {
                            holder.name.setTextColor(Color.WHITE)
                            holder.language.setTextColor(Color.WHITE)
                            holder.period.setTextColor(Color.WHITE)
                            holder.summary.setTextColor(Color.WHITE)
                            holder.tags.tagTextColor = Color.WHITE
                            holder.tags.backgroundColor = -color
                        } else {
                            holder.name.setTextColor(Color.BLACK)
                            holder.language.setTextColor(Color.BLACK)
                            holder.period.setTextColor(Color.BLACK)
                            holder.summary.setTextColor(Color.BLACK)
                            holder.tags.tagTextColor = Color.BLACK
                            holder.tags.backgroundColor = -color
                        }

                    }
                })
        }
    }
}