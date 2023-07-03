package io.drdroid.assignment2.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.AndroidEntryPoint
import io.drdroid.assignment2.adapters.GenreAdapter
import io.drdroid.assignment2.base.BaseFragment
import io.drdroid.assignment2.databinding.FragmentHomeBinding
import io.drdroid.assignment2.dialogs.CustomDialog
import io.drdroid.assignment2.network.ApiCall
import io.drdroid.assignment2.utils.GridSpacingItemDecoration
import jp.wasabeef.recyclerview.animators.LandingAnimator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject


@AndroidEntryPoint
class HomeFragment : BaseFragment() {

    @Inject
    lateinit var apiCall: ApiCall

    private val id: Int = HomeFragment::class.java.hashCode()

    private lateinit var bind: FragmentHomeBinding
    lateinit var recyclerView: RecyclerView

    private var listGenres: List<String>? = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        super.restoreRootView(id)?.let {
            bind = super.restoreRootView(id) as FragmentHomeBinding
        }
//        val bundle: Bundle = requireActivity().intent?.extras!!
//        listGenres = bundle.getStringArray("genres")!!.toList()
        val bundle = requireArguments()
        val itemType = object : TypeToken<List<String>>() {}.type
        listGenres = Gson().fromJson(bundle.getString("genres"), itemType)
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
        recyclerView.itemAnimator = LandingAnimator()
        if (recyclerView.itemDecorationCount == 0) {
            recyclerView.addItemDecoration(GridSpacingItemDecoration(2, 10, false))
        }

        if (listGenres.isNullOrEmpty()) {
            loadGenres()
        } else {
            setupAdapter(listGenres!!)
        }
    }

    override fun onDestroyView() {
        super.storeRootView(id, bind)
        super.onDestroyView()
    }

    private fun loadGenres() {
        val dialog: CustomDialog = CustomDialog.loadingDialog(this@HomeFragment.requireContext())
        dialog.show()

        CoroutineScope(Dispatchers.Main).launch {
            listGenres = try {
                apiCall.getShows().asSequence().filter { it.genres.isNotEmpty() }
                    .map { showModel -> showModel.genres }.map { l -> l.first() }.toSet().toList()
                    .sorted()
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }

            if (!listGenres.isNullOrEmpty()) {
                setupAdapter(listGenres!!)

                dialog.dismiss()
            } else {
                Toast.makeText(
                    this@HomeFragment.requireContext(),
                    "Cannot load data",
                    Toast.LENGTH_SHORT
                ).show()
            }


        }
    }

    private fun setupAdapter(list: List<String>) {
        val adapter = GenreAdapter(requireContext(), list, findNavController())
        recyclerView.adapter = adapter
    }
}