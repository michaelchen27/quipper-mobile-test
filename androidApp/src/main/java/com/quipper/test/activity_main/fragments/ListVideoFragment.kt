package com.quipper.test.activity_main.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.quipper.test.R
import com.quipper.test.core.base.BaseFragment
import com.quipper.test.databinding.FragmentListVideoBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ListVideoFragment : BaseFragment<FragmentListVideoBinding>() {

    private val viewModel by viewModels<ListVideoViewModel>()

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentListVideoBinding =
        FragmentListVideoBinding::inflate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun initView() {
        viewModel.setupDbDriver(requireContext())

    }

    override fun initData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.videoList.collectLatest { videos ->
                videos.forEach {
                    Log.d("cek", "initData: ${it.title}")
                }
            }
        }


    }
}