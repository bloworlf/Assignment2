package io.drdroid.assignment2.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.drdroid.assignment2.base.BaseFragment
import io.drdroid.assignment2.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment() {

    private val id: Int = HomeFragment::class.java.hashCode()

    lateinit var bind: FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind = FragmentHomeBinding.inflate(inflater, container, false)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //
    }
}