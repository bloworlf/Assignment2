package io.drdroid.assignment2.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import io.drdroid.assignment2.activities.Home
import io.drdroid.assignment2.base.BaseFragment
import io.drdroid.assignment2.databinding.FragmentTagBinding

class TagFragment : BaseFragment() {

    lateinit var bind: FragmentTagBinding
    lateinit var strTag: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)

        strTag = requireArguments().getString("tag")!!
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind = FragmentTagBinding.inflate(layoutInflater)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (activity as Home).showToolbar()
        (activity as AppCompatActivity).supportActionBar!!.title = strTag
//        (activity as AppCompatActivity).supportActionBar.
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