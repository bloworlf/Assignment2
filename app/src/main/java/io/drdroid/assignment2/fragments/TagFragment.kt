package io.drdroid.assignment2.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import io.drdroid.assignment2.activities.Home
import io.drdroid.assignment2.adapters.ShowAdapter
import io.drdroid.assignment2.base.BaseFragment
import io.drdroid.assignment2.databinding.FragmentSearchBinding
import io.drdroid.assignment2.databinding.FragmentTagBinding
import io.drdroid.assignment2.dialogs.CustomDialog
import io.drdroid.assignment2.models.data.ShowModel
import io.drdroid.assignment2.network.ApiCall
import io.drdroid.assignment2.network.ApiDetails
import jp.wasabeef.recyclerview.animators.LandingAnimator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class TagFragment : BaseFragment() {

    @Inject
    lateinit var apiCall: ApiCall

    private val id: Int = TagFragment::class.java.hashCode() + System.currentTimeMillis().toInt()

    private lateinit var bind: FragmentTagBinding
    lateinit var strTag: String
    lateinit var recyclerView: RecyclerView
    lateinit var listShow: MutableList<ShowModel>
    lateinit var adapter: ShowAdapter
    private var page: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)

        strTag = requireArguments().getString("tag")!!

        super.restoreRootView(id)?.let {
            bind = super.restoreRootView(id) as FragmentTagBinding
        }
    }

    private fun loadShows(page: Int) {
        val dialog: CustomDialog = CustomDialog.loadingDialog(this@TagFragment.requireContext())
        dialog.show()
        CoroutineScope(Dispatchers.Main).launch {
            try {
                apiCall.getShows(page)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }?.let {
                populateRecyclerView(it.filter { show -> strTag in show.genres })
            }

            dialog.dismiss()
        }
    }

    private fun populateRecyclerView(list: List<ShowModel>) {
        if (page == 1 && this::adapter.isInitialized) {
//            listShow.clear()
            adapter.list.clear()
            adapter.notifyItemRangeRemoved(0, listShow.size)
            listShow.clear()
        }
        if (!this::listShow.isInitialized) {
            listShow = list.toMutableList()
            adapter = ShowAdapter(requireContext(), listShow, findNavController())
            recyclerView.adapter = adapter
        } else {
            listShow.addAll(list)
            adapter.list.addAll(list)
            adapter.notifyItemRangeInserted(listShow.size - list.size, list.size)
        }
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
        (activity as AppCompatActivity).supportActionBar!!.title = strTag

        recyclerView = bind.recyclerView
        recyclerView.itemAnimator = LandingAnimator()
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1)) { //1 for down
                    page++
                    loadShows(page)
                }
            }
        })

        if (!this::listShow.isInitialized || listShow.isEmpty()) {
            page = 1
            loadShows(page)
        }
    }

    override fun onDestroyView() {
        super.storeRootView(id, bind)
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