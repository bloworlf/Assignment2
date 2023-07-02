package io.drdroid.assignment2.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import io.drdroid.assignment2.R
import io.drdroid.assignment2.adapters.ShowAdapter
import io.drdroid.assignment2.base.BaseFragment
import io.drdroid.assignment2.databinding.FragmentSearchBinding
import io.drdroid.assignment2.dialogs.CustomDialog
import io.drdroid.assignment2.models.data.ShowModel
import io.drdroid.assignment2.network.ApiDetails
import io.drdroid.assignment2.utils.Utils.hideKeyboard
import jp.wasabeef.recyclerview.animators.LandingAnimator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchFragment : BaseFragment() {

    lateinit var bind: FragmentSearchBinding

    private var searchView: SearchView? = null

    private lateinit var recyclerView: RecyclerView
    lateinit var adapter: ShowAdapter
    private var listShow: List<ShowModel> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind = FragmentSearchBinding.inflate(inflater, container, false)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        recyclerView = bind.recyclerView
        recyclerView.itemAnimator = LandingAnimator()
//        adapter = ShowAdapter(requireContext(), listShow, findNavController())
//        recyclerView.adapter = adapter
    }

    private fun makeQuery(query: String) {
        val dialog: CustomDialog = CustomDialog.loadingDialog(this@SearchFragment.requireContext())
//        val dialog: CustomDialog = CustomDialog(this@SearchFragment.requireContext(), false)
        dialog.show()

        CoroutineScope(Dispatchers.Main).launch {
            //api call
            /*var result: ArrayList<SearchItemModel>? = */
            try {
                ApiDetails.apiClient.search(q = query)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }?.let {
//                println("${it.size}")
                listShow =
                    it.map { searchItemModel -> searchItemModel.show }

                populateRecyclerView(it.size)
            }

            dialog.dismiss()
        }
    }

    private fun populateRecyclerView(size: Int) {
        if (!this::adapter.isInitialized) {
            adapter = ShowAdapter(requireContext(), listShow, findNavController())
            recyclerView.adapter = adapter
            adapter.notifyItemRangeRemoved(0, listShow.size)
            adapter.notifyItemRangeInserted(0, listShow.size)
        } else {
            if (recyclerView.adapter == null) {
                recyclerView.adapter = adapter
            }
            adapter.notifyDataSetChanged()
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
                if (query.length <= 3) {
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