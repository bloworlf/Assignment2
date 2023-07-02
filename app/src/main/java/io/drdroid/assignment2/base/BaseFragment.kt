package io.drdroid.assignment2.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {

    companion object Foo{
        private var bindings: MutableMap<Int, Any> = mutableMapOf()
//        private var dataSet: MutableMap<Int, List<Any>> = mutableMapOf()
    }

    fun storeRootView(id: Int, binding: Any) {
        if (id in bindings) {
            bindings.replace(id, binding)
        } else {
            bindings[id] = binding
        }
    }

    fun restoreRootView(id: Int): Any? {
        return bindings[id]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    abstract override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View

    abstract override fun onViewCreated(view: View, savedInstanceState: Bundle?)
}