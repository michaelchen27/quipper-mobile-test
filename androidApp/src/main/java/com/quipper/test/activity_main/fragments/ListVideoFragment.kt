package com.quipper.test.activity_main.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
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

    override fun initView() {
        binding.rvVideoList.adapter = adapter

        binding.refresh.setOnRefreshListener {
            viewModel.getVideos(true)
        }

    }

    override fun initData() {
        viewLifecycleOwner.lifecycleScope.launch {
            launch {
                viewModel.listVideoUIState.collectLatest { uiState ->
                    when (uiState) {
                        is ListVideoViewModel.VideoUIState.Success -> {
                            binding.refresh.isRefreshing = false
                            adapter.submitList(uiState.videos)
                        }

                        is ListVideoViewModel.VideoUIState.Error -> {
                            binding.refresh.isRefreshing = false

                            Toast.makeText(requireContext(), uiState.errMsg, Toast.LENGTH_SHORT)
                                .show()
                        }

                        is ListVideoViewModel.VideoUIState.Loading -> {
                            binding.refresh.isRefreshing = true

                        }
                    }

                }
            }
        }

        viewModel.setupDbDriver(requireContext())
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