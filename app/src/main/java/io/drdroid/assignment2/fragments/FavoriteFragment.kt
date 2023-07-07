package io.drdroid.assignment2.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import io.drdroid.assignment2.activities.Home
import io.drdroid.assignment2.base.BaseFragment
import io.drdroid.assignment2.databinding.FragmentFavoritesBinding

class FavoriteFragment : BaseFragment() {

    lateinit var bind: FragmentFavoritesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind = FragmentFavoritesBinding.inflate(inflater, container, false)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //
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