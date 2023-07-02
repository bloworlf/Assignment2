package io.drdroid.assignment2.fragments

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import io.drdroid.assignment2.R
import io.drdroid.assignment2.activities.Home
import io.drdroid.assignment2.adapters.EmptyDataObserver
import io.drdroid.assignment2.adapters.EpisodeAdapter
import io.drdroid.assignment2.adapters.SeasonAdapter
import io.drdroid.assignment2.base.BaseFragment
import io.drdroid.assignment2.databinding.FragmentShowDetailsBinding
import io.drdroid.assignment2.interfaces.SeasonListener
import io.drdroid.assignment2.models.data.EpisodeModel
import io.drdroid.assignment2.models.data.SeasonModel
import io.drdroid.assignment2.models.data.ShowModel
import io.drdroid.assignment2.network.ApiCall
import io.drdroid.assignment2.utils.PaletteUtils
import io.drdroid.assignment2.utils.Utils
import io.drdroid.assignment2.utils.Utils.colorTransition
import jp.wasabeef.recyclerview.animators.LandingAnimator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ShowDetailsFragment : BaseFragment(), SeasonListener {

    private val id: Int = ShowDetailsFragment::class.java.hashCode()

    @Inject
    lateinit var apiCall: ApiCall

    private lateinit var bind: FragmentShowDetailsBinding
    private lateinit var show: ShowModel
    var dominantColor: Int = 0
    var secondaryColor: Int = 0

    private lateinit var seasonRecycler: RecyclerView
    private lateinit var seasons: List<SeasonModel>
    private lateinit var seasonAdapter: SeasonAdapter


    private lateinit var episodeRecycler: RecyclerView
    private var episodes: List<EpisodeModel> = listOf()
    private lateinit var episodeAdapter: EpisodeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)

        show = Gson().fromJson(requireArguments().getString("show"), ShowModel::class.java)

        CoroutineScope(Dispatchers.Main).launch {
            try {
                seasons = apiCall.getSeasons(id = show.id)

                populateSeason()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind = FragmentShowDetailsBinding.inflate(inflater, container, false)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (activity as Home).showToolbar()
        (activity as AppCompatActivity).supportActionBar!!.title = show.name

        show.image?.let {
            Glide.with(this).asBitmap().load(Uri.parse(show.image?.original)).centerCrop()
                .into(object : CustomTarget<Bitmap>() {
                    override fun onLoadCleared(placeholder: Drawable?) {
                    }

                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {

                        dominantColor = PaletteUtils.getUpperSideDominantColor(
                            resource
                        )
                        secondaryColor = PaletteUtils.getLowerSideDominantColor(
                            resource
                        )

                        (activity as AppCompatActivity).supportActionBar!!.setBackgroundDrawable(
                            ColorDrawable(dominantColor)
                        )
                        requireActivity().window.statusBarColor = dominantColor

                        bind.root.setBackgroundColor(secondaryColor)

//                        val home = requireActivity().actionBar.hom
                        val wrapDrawable = DrawableCompat.wrap(
                            ResourcesCompat.getDrawable(
                                resources,
                                R.drawable.back,
                                null
                            )!!
                        )
                        if (Utils.isDark(dominantColor)) {
                            Utils.clearLightStatusBar(requireActivity())
                            (activity as Home).titleColor(Color.WHITE)


                            DrawableCompat.setTint(wrapDrawable, Color.WHITE)
                            (requireActivity() as Home).supportActionBar?.setHomeAsUpIndicator(
                                wrapDrawable
                            )
                        } else {
                            Utils.setLightStatusBar(requireActivity())
                            (activity as Home).titleColor(Color.BLACK)

                            DrawableCompat.setTint(wrapDrawable, Color.BLACK)
                            (requireActivity() as Home).supportActionBar?.setHomeAsUpIndicator(
                                wrapDrawable
                            )
                        }
                        if (Utils.isDark(secondaryColor)) {
                            bind.summary.setTextColor(Color.WHITE)
                        } else {
                            bind.summary.setTextColor(Color.BLACK)
                        }

                    }
                })
        }

        bind.summary.text = Html.fromHtml(show.summary)
        bind.summary.setOnClickListener {
            if (bind.summary.maxLines == 5) {
                bind.summary.maxLines = 100
                bind.summary.setCompoundDrawables(
                    null,
                    null,
                    ResourcesCompat.getDrawable(
                        requireActivity().resources,
                        R.drawable.collapse,
                        null
                    ),
                    null
                )
                bind.summary.setCompoundDrawablesWithIntrinsicBounds(
                    null,
                    null,
                    ResourcesCompat.getDrawable(
                        requireActivity().resources,
                        R.drawable.collapse,
                        null
                    ),
                    null
                )
            } else {
                bind.summary.maxLines = 5
                bind.summary.setCompoundDrawables(
                    null,
                    null,
                    ResourcesCompat.getDrawable(
                        requireActivity().resources,
                        R.drawable.expand,
                        null
                    ),
                    null
                )
                bind.summary.setCompoundDrawablesWithIntrinsicBounds(
                    null,
                    null,
                    ResourcesCompat.getDrawable(
                        requireActivity().resources,
                        R.drawable.expand,
                        null
                    ),
                    null
                )
            }
        }

        seasonRecycler = bind.recyclerViewSeasons
        seasonRecycler.itemAnimator = LandingAnimator()
        val snapHelper: LinearSnapHelper = object : LinearSnapHelper() {
            override fun findTargetSnapPosition(
                layoutManager: RecyclerView.LayoutManager,
                velocityX: Int,
                velocityY: Int
            ): Int {
                val centerView = findSnapView(layoutManager) ?: return RecyclerView.NO_POSITION
                val position = layoutManager.getPosition(centerView)
                var targetPosition = -1
                if (layoutManager.canScrollHorizontally()) {
                    targetPosition = if (velocityX < 0) {
                        position - 1
                    } else {
                        position + 1
                    }
                }
                if (layoutManager.canScrollVertically()) {
                    targetPosition = if (velocityY < 0) {
                        position - 1
                    } else {
                        position + 1
                    }
                }
                val firstItem = 0
                val lastItem = layoutManager.itemCount - 1
                targetPosition = Math.min(lastItem, Math.max(targetPosition, firstItem))
                return targetPosition
            }
        }
        snapHelper.attachToRecyclerView(seasonRecycler)

        episodeRecycler = bind.recyclerViewEpisodes
        episodeRecycler.itemAnimator = LandingAnimator()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val activity = activity as? Home
        return when (item.itemId) {
            android.R.id.home -> {
                activity?.onSupportNavigateUp()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun populateSeason() {
        if (!this::seasonAdapter.isInitialized) {
            seasonAdapter = SeasonAdapter(requireContext(), seasons, this)
            seasonRecycler.adapter = seasonAdapter
            seasonAdapter.notifyItemRangeRemoved(0, seasons.size)
            seasonAdapter.notifyItemRangeInserted(0, seasons.size)
        } else {

        }
    }

    override fun onDestroy() {
        (activity as Home).titleColor(
            ResourcesCompat.getColor(
                requireActivity().resources,
                android.R.color.tab_indicator_text,
                null
            )
        )
        (activity as AppCompatActivity).supportActionBar!!.setBackgroundDrawable(
            ColorDrawable(
                ResourcesCompat.getColor(
                    requireActivity().resources,
                    R.color.navigation,
                    null
                )
            )
        )

        requireActivity().window.statusBarColor =
            ResourcesCompat.getColor(resources, R.color.navigation, null)
        Utils.setLightStatusBar(requireActivity())

        super.onDestroy()
    }

    private fun loadEpisodes(id: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                if (episodes.isNotEmpty()) {
//                    episodeAdapter.notifyItemRangeRemoved(0, episodes.size)
                    episodes = listOf()
                }
                episodes = apiCall.getSeasonEpisodes(id)
//                episodeAdapter.notifyItemRangeInserted(0, episodes.size)

//                episodeAdapter = EpisodeAdapter(this@ShowDetailsFragment.requireContext(), episodes)
//                episodeRecycler.adapter = episodeAdapter

//                episodeRecycler.smoothScrollToPosition(0)
                populateEpisodes(episodes)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun populateEpisodes(list: List<EpisodeModel>) {
        if(!this::episodeAdapter.isInitialized){
            episodeAdapter = EpisodeAdapter(this@ShowDetailsFragment.requireContext(), list.toMutableList())
            episodeRecycler.adapter = episodeAdapter
            val emptyDataObserver = EmptyDataObserver(episodeRecycler, bind.emptyDataParent.root)
            episodeAdapter.registerAdapterDataObserver(emptyDataObserver)
        }
        episodeAdapter.updateExpandedList(list.size)
        if (episodeAdapter.list.isEmpty()) {
            for (i in list.indices) {
                episodeAdapter.list.add(list[i])
                episodeAdapter.notifyItemInserted(i)
            }
        } else {
            while (list.size < episodeAdapter.list.size) {
                episodeAdapter.list.removeLast()
                episodeAdapter.notifyItemRemoved(episodeAdapter.list.size)
            }
            for (i in list.indices) {
                if (i < episodeAdapter.list.size) {
                    episodeAdapter.list.removeAt(i)
                    episodeAdapter.notifyItemRemoved(i)
                    episodeAdapter.list.add(i, list[i])
                    episodeAdapter.notifyItemInserted(i)
                } else {
                    episodeAdapter.list.add(list[i])
                    episodeAdapter.notifyItemInserted(i)
                }
            }
        }
        episodeRecycler.smoothScrollToPosition(0)
    }

    override fun onSeasonSelected(id: Int, colors: Map<String, Int>) {
        //load the episodes into episodeRecyclerview
        seasonRecycler.smoothScrollToPosition(seasons.indexOf(seasons.first { it.id == id }))
        loadEpisodes(id)

        bind.root.colorTransition(colors["dominant"]!!)
        if (Utils.isDark(colors["dominant"]!!)) {
            bind.summary.setTextColor(Color.WHITE)
        } else {
            bind.summary.setTextColor(Color.BLACK)
        }
    }
}