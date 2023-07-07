package io.drdroid.assignment2.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import io.drdroid.assignment2.R
import io.drdroid.assignment2.base.BaseFragment
import io.drdroid.assignment2.databinding.FragmentMoreBinding
import io.drdroid.assignment2.utils.Utils

class MoreFragment : BaseFragment() {

    private val id: Int = MoreFragment::class.java.hashCode()

    private lateinit var bind: FragmentMoreBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        super.restoreRootView(id)?.let {
            bind = super.restoreRootView(id) as FragmentMoreBinding
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (!this::bind.isInitialized) {
            bind = FragmentMoreBinding.inflate(inflater, container, false)
        }
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        bind.favorites.setOnClickListener {
            findNavController().navigate(
                R.id.favoriteFragment
            )
        }
        bind.moreProjects.setOnClickListener {
            Utils.openUrl(requireContext(), "https://github.com/bloworlf")
        }
    }

    override fun onDestroyView() {
        super.storeRootView(id, bind)
        super.onDestroyView()
    }
}