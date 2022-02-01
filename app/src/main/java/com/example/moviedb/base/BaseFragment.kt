package com.example.moviedb.base

import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope

const val TAG_PROGRESS = "tagProgress"

open class BaseFragment : Fragment() {

    fun launchOnLifecycleScope(execute: suspend () -> Unit) {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            execute()
        }
    }

    fun showProgress() = activity?.let{
        BaseProgress().show(it.supportFragmentManager, TAG_PROGRESS)
    }

    fun hideProgress() = activity?.let{
        it.supportFragmentManager.fragments.filterIsInstance<BaseProgress>()
            .forEach { it.dismiss() }
    }

}