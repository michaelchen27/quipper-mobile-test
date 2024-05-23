package com.quipper.test.activity_main.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.quipper.test.R
import com.quipper.test.activity_main.MainViewModel
import com.quipper.test.activity_main.adapter.VideoListAdapter
import com.quipper.test.core.base.BaseFragment
import com.quipper.test.databinding.FragmentListVideoBinding
import com.quipper.test.model.VideoData
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ListVideoFragment : BaseFragment<FragmentListVideoBinding>() {

    private val viewModel by viewModels<ListVideoViewModel>()
    private val acViewModel by activityViewModels<MainViewModel>()

    private val adapter = VideoListAdapter(::onItemClick)

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentListVideoBinding =
        FragmentListVideoBinding::inflate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun initView() {
        binding.rvVideoList.adapter = adapter
        viewModel.setupDbDriver(requireContext())

        binding.refresh.setOnRefreshListener{
            viewModel.getVideos(forceReload = true)
        }

    }

    override fun initData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.videoList.collectLatest { videos ->
                binding.refresh.isRefreshing = false
                adapter.submitList(videos)
            }
        }
    }

    private fun onItemClick(data: VideoData) {
        acViewModel.loadedVideoDetail = data
        findNavController().navigate(R.id.action_listVideoFragment_to_detailVideoFragment)
    }

    override fun onResume() {
        super.onResume()
        acViewModel.loadedVideoDetail = VideoData()
    }
}