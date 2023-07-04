package io.drdroid.assignment2.fragments

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.distinctUntilChanged
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.internal.managers.FragmentComponentManager
import io.drdroid.assignment2.activities.Home
import io.drdroid.assignment2.adapters.ShowAdapter
import io.drdroid.assignment2.base.BaseFragment
import io.drdroid.assignment2.data.repo.Repository
import io.drdroid.assignment2.databinding.FragmentTagBinding
import io.drdroid.assignment2.dialogs.CustomDialog
import io.drdroid.assignment2.models.data.ShowModel
import io.drdroid.assignment2.models.view_model.ShowViewModel
import io.drdroid.assignment2.network.TvShowCall
import jp.wasabeef.recyclerview.animators.LandingAnimator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class TagFragment : BaseFragment() {

//    @Inject
//    lateinit var tvShowCall: Repository

    private val id: Int = TagFragment::class.java.hashCode() + System.currentTimeMillis().toInt()
    private val viewModel: ShowViewModel by viewModels()

    private lateinit var bind: FragmentTagBinding
    lateinit var strTag: String
    lateinit var recyclerView: RecyclerView

    //    lateinit var listShow: MutableList<ShowModel>
    lateinit var adapter: ShowAdapter
    lateinit var manager: StaggeredGridLayoutManager
    private var page: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)

        strTag = requireArguments().getString("tag")!!

//        super.restoreRootView(id)?.let {
//            bind = super.restoreRootView(id) as FragmentTagBinding
//        }
        viewModel.getShowByTag(strTag, page)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (!this::adapter.isInitialized) {
            return
        }
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            manager.spanCount = 2
            adapter.notifyItemRangeChanged(0, adapter.itemCount)
        } else {
            manager.spanCount = 1
            adapter.notifyItemRangeChanged(0, adapter.itemCount)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putString("tag", strTag)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (!this::bind.isInitialized) {
            bind = FragmentTagBinding.inflate(inflater, container, false)
        }
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (activity as Home).showToolbar()
        // TODO: Find a way to fix title after configuration change
        (activity as Home).supportActionBar?.let {
            (activity as Home).supportActionBar!!.title = strTag
        }
//        (FragmentComponentManager.findActivity(view.context) as AppCompatActivity).supportActionBar!!.title =
//            strTag

        recyclerView = bind.recyclerView
        recyclerView.itemAnimator = LandingAnimator()
        manager = StaggeredGridLayoutManager(
            if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                2
            } else {
                1
            }, StaggeredGridLayoutManager.VERTICAL
        )
        recyclerView.layoutManager = manager
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1)) { //1 for down
                    page++
//                    loadShows(page)
                    viewModel.getShowByTag(strTag, page)
                }
            }
        })

        viewModel.showLiveData.observe(viewLifecycleOwner) {
            populateRecyclerView(it)
        }
//        if (!this::listShow.isInitialized || listShow.isEmpty()) {
//            page = 1
//            loadShows(page)
//        }
    }

//    private fun loadShows(page: Int) {
//        val dialog: CustomDialog = CustomDialog.loadingDialog(this@TagFragment.requireContext())
//        dialog.show()
//        CoroutineScope(Dispatchers.Main).launch {
//            try {
//                tvShowCall.getShows(page)
//            } catch (e: Exception) {
//                e.printStackTrace()
//                null
//            }?.let {
//                populateRecyclerView(it.filter { show -> strTag in show.genres })
//            }
//
//            dialog.dismiss()
//        }
//    }

    private fun populateRecyclerView(list: List<ShowModel>) {
        if (this::adapter.isInitialized) {
            if (page == 1) {
                adapter.notifyItemRangeRemoved(0, adapter.list.size)
                adapter.list.clear()
            }
        } else {
            adapter = ShowAdapter(requireContext(), list.toMutableList(), findNavController())
            recyclerView.adapter = adapter
        }
        adapter.list.addAll(list)
        adapter.notifyItemRangeInserted(adapter.list.size - list.size, list.size)

    }

    override fun onDestroyView() {
//        super.storeRootView(id, bind)
        super.onDestroyView()
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
}