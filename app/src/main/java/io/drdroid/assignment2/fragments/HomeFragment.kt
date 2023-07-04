package io.drdroid.assignment2.fragments

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.AndroidEntryPoint
import io.drdroid.assignment2.adapters.GenreAdapter
import io.drdroid.assignment2.base.BaseFragment
import io.drdroid.assignment2.databinding.FragmentHomeBinding
import io.drdroid.assignment2.dialogs.CustomDialog
import io.drdroid.assignment2.models.view_model.GenreViewModel
import io.drdroid.assignment2.utils.GridSpacingItemDecoration
import jp.wasabeef.recyclerview.animators.LandingAnimator


@AndroidEntryPoint
class HomeFragment : BaseFragment() {

//    @Inject
//    lateinit var tvShowCall: Repository

    private lateinit var adapter: GenreAdapter
    private val id: Int = HomeFragment::class.java.hashCode()

    private lateinit var bind: FragmentHomeBinding
    lateinit var recyclerView: RecyclerView
    lateinit var manager: GridLayoutManager
//    lateinit var decoration: GridSpacingItemDecoration

    //    private var listGenres: List<String>? = mutableListOf()
    private val viewModel: GenreViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        super.restoreRootView(id)?.let {
//            bind = super.restoreRootView(id) as FragmentHomeBinding
//        }
//        val bundle: Bundle = requireActivity().intent?.extras!!
//        listGenres = bundle.getStringArray("genres")!!.toList()
        val bundle = requireArguments()
        val itemType = object : TypeToken<List<String>>() {}.type
//        listGenres = Gson().fromJson(bundle.getString("genres"), itemType)

        viewModel.saveGenre(Gson().fromJson(bundle.getString("genres"), itemType))
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            recyclerView.removeItemDecorationAt(0)
            recyclerView.addItemDecoration(GridSpacingItemDecoration(3, 10, true))
            manager.spanCount = 3
            adapter.notifyItemRangeChanged(0, adapter.itemCount)
        } else {
            recyclerView.removeItemDecorationAt(0)
            recyclerView.addItemDecoration(GridSpacingItemDecoration(2, 10, true))
            manager.spanCount = 2
            adapter.notifyItemRangeChanged(0, adapter.itemCount)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (!this::bind.isInitialized) {
            bind = FragmentHomeBinding.inflate(inflater, container, false)
        }
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        recyclerView = bind.recyclerView
        manager = GridLayoutManager(
            requireContext(),
            if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                3
            } else {
                2
            }
        )
        recyclerView.layoutManager = manager
        recyclerView.itemAnimator = LandingAnimator()
        if (recyclerView.itemDecorationCount == 0) {
            recyclerView.addItemDecoration(GridSpacingItemDecoration(if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                3
            } else {
                2
            }, 10, true))
        }

//        val viewModel: ShowViewModel by viewModels()
//
//        if (listGenres.isNullOrEmpty()) {
//
//
//            viewModel.getSHowData()
//            if (!viewModel.isLoaded) {
//                loadGenres()
//            }
//
//        } else {
//            setupAdapter(listGenres!!)
//        }
//        viewModel.showLiveData.observe(viewLifecycleOwner) { it ->
//            //load data in UI
//            listGenres = it.asSequence()
//                .filter { it.genres.isNotEmpty() }
//                .map { showModel -> showModel.genres }
//                .map { l -> l.first() }
//                .toSet()
//                .toList()
//                .sorted()
//
//            if (!listGenres.isNullOrEmpty()) {
//                setupAdapter(listGenres!!)
//
////                dialog.dismiss()
//            } else {
//                Toast.makeText(
//                    this@HomeFragment.requireContext(),
//                    "Cannot load data",
//                    Toast.LENGTH_SHORT
//                ).show()
//            }
//        }

        viewModel.genreData.observe(viewLifecycleOwner) {
            setupAdapter(it)
        }

    }

    override fun onDestroyView() {
//        super.storeRootView(id, bind)
        super.onDestroyView()
    }

    private fun loadGenres() {
        val dialog: CustomDialog = CustomDialog.loadingDialog(this@HomeFragment.requireContext())
        dialog.show()


//        CoroutineScope(Dispatchers.Main).launch {
//            listGenres = try {
//                tvShowCall.getShows().asSequence().filter { it.genres.isNotEmpty() }
//                    .map { showModel -> showModel.genres }.map { l -> l.first() }.toSet().toList()
//                    .sorted()
//            } catch (e: Exception) {
//                e.printStackTrace()
//                null
//            }
//
//            if (!listGenres.isNullOrEmpty()) {
//                setupAdapter(listGenres!!)
//
//                dialog.dismiss()
//            } else {
//                Toast.makeText(
//                    this@HomeFragment.requireContext(),
//                    "Cannot load data",
//                    Toast.LENGTH_SHORT
//                ).show()
//            }
//
//
//        }
    }

    private fun setupAdapter(list: List<String>) {
        adapter = GenreAdapter(requireContext(), list, findNavController())
        recyclerView.adapter = adapter
    }
}