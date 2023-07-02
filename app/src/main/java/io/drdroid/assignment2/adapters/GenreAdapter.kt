package io.drdroid.assignment2.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import io.drdroid.assignment2.R
import io.drdroid.assignment2.holders.GenreHolder
import io.drdroid.assignment2.utils.Utils
import java.util.Random


class GenreAdapter(var context: Context, var set: List<String>, var controller: NavController) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.model_genre, parent, false)
        return GenreHolder(view)
    }

    override fun getItemCount(): Int {
        return set.size
    }

    override fun onBindViewHolder(h0: RecyclerView.ViewHolder, position: Int) {
        val genre = set[position]
        val holder = h0 as GenreHolder

        holder.genre.text = genre

        val rnd = Random()
        val color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))

        holder.block.setBackgroundColor(color)

        if (Utils.isDark(color)) {
            holder.genre.setTextColor(Color.WHITE)
        } else {
            holder.genre.setTextColor(Color.BLACK)
        }

        holder.itemView.setOnClickListener {
            controller.navigate(
                R.id.genre_to_tag,
                bundleOf(
                    "tag" to genre,
                    "color" to color
                )
            )
        }
    }
}