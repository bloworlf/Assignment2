package io.drdroid.assignment2.fragments

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import io.drdroid.assignment2.R
import io.drdroid.assignment2.adapters.EmptyDataObserver
import io.drdroid.assignment2.adapters.ShowAdapter
import io.drdroid.assignment2.base.BaseFragment
import io.drdroid.assignment2.data.repo.Repository
import io.drdroid.assignment2.databinding.FragmentSearchBinding
import io.drdroid.assignment2.dialogs.CustomDialog
import io.drdroid.assignment2.models.data.ShowModel
import io.drdroid.assignment2.models.view_model.SearchItemViewModel
import io.drdroid.assignment2.network.TvShowCall
import io.drdroid.assignment2.utils.Utils.hideKeyboard
import jp.wasabeef.recyclerview.animators.LandingAnimator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : BaseFragment() {

//    @Inject
//    lateinit var tvShowCall: Repository

    private val id: Int = SearchFragment::class.java.hashCode()

    companion object {
        private var listShow: MutableList<ShowModel> = mutableListOf()
    }

    private lateinit var bind: FragmentSearchBinding

    private var searchView: SearchView? = null

    private lateinit var recyclerView: RecyclerView
    lateinit var adapter: ShowAdapter
    lateinit var manager: StaggeredGridLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)

//        super.restoreRootView(id)?.let {
//            bind = super.restoreRootView(id) as FragmentSearchBinding
//        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            manager.spanCount = 2
            adapter.notifyItemRangeChanged(0, adapter.itemCount)
        } else {
            manager.spanCount = 1
            adapter.notifyItemRangeChanged(0, adapter.itemCount)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (!this::bind.isInitialized) {
            bind = FragmentSearchBinding.inflate(inflater, container, false)
        }
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

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

        adapter = ShowAdapter(requireContext(), listShow, findNavController())
        recyclerView.adapter = adapter
        val emptyDataObserver = EmptyDataObserver(recyclerView, bind.emptyDataParent.root)
        adapter.registerAdapterDataObserver(emptyDataObserver)
    }

    override fun onDestroyView() {
//        super.storeRootView(id, bind)
        super.onDestroyView()
    }

    private fun makeQuery(query: String) {
//        val dialog: CustomDialog = CustomDialog.loadingDialog(this@SearchFragment.requireContext())
//        dialog.show()
//
//        CoroutineScope(Dispatchers.Main).launch {
//            try {
//                tvShowCall.search(q = query)
//            } catch (e: Exception) {
//                e.printStackTrace()
//                null
//            }?.let {
//                populateRecyclerView(it.map { searchItemModel -> searchItemModel.show })
//            }
//
//            dialog.dismiss()
//        }

        val viewModel: SearchItemViewModel by viewModels()

//        if (!viewModel.isLoaded) {
        viewModel.searchShow(query)
//        }

//        if (viewModel.isLoaded) {
//            populateRecyclerView(viewModel.searchItemData.value?.map { searchItemModel -> searchItemModel.show }!!)
//        }
        viewModel.searchItemData.observe(viewLifecycleOwner) {
            populateRecyclerView(it.map { searchItemModel -> searchItemModel.show })
        }

    }

    private fun populateRecyclerView(list: List<ShowModel>) {
        if (adapter.list.isEmpty()) {
            for (i in list.indices) {
                adapter.list.add(list[i])
                adapter.notifyItemInserted(i)
            }
        } else {
            while (list.size < adapter.list.size) {
                adapter.list.removeLast()
                adapter.notifyItemRemoved(adapter.list.size)
            }
            for (i in list.indices) {
                if (i < adapter.list.size) {
                    adapter.list.removeAt(i)
                    adapter.notifyItemRemoved(i)
                    adapter.list.add(i, list[i])
                    adapter.notifyItemInserted(i)
                } else {
                    adapter.list.add(list[i])
                    adapter.notifyItemInserted(i)
                }
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        // TODO Add your menu entries here
//        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.search_menu, menu)
        searchView = (menu.findItem(R.id.search).actionView as SearchView)
        searchView!!.maxWidth = Integer.MAX_VALUE

        searchView!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (query.length <= 2) {
                    Toast.makeText(
                        this@SearchFragment.requireContext(),
                        "Query string too short",
                        Toast.LENGTH_SHORT
                    ).show()
                    return false
                }
                makeQuery(query)
                searchView!!.hideKeyboard()
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                return false
            }
        })
    }
}