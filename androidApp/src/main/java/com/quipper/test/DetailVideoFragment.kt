package com.quipper.test

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.OptIn
import androidx.fragment.app.activityViewModels
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import com.quipper.test.activity_main.MainViewModel
import com.quipper.test.core.base.BaseFragment
import com.quipper.test.databinding.FragmentDetailVideoBinding
import com.quipper.test.model.VideoData


class DetailVideoFragment : BaseFragment<FragmentDetailVideoBinding>() {

    private val acViewModel by activityViewModels<MainViewModel>()

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentDetailVideoBinding =
        FragmentDetailVideoBinding::inflate

    private lateinit var videoData: VideoData
    private lateinit var player: ExoPlayer

    @OptIn(UnstableApi::class)
    private fun setupPlayerView() = with(binding) {
        player = ExoPlayer.Builder(requireContext()).build()
        videoData = acViewModel.loadedVideoDetail

        videoData.videoUrl?.let {
            val mediaItem = MediaItem.fromUri(it)
            player.setMediaItem(mediaItem)
            player.prepare()
        }

        playerView.showController()
        playerView.controllerHideOnTouch = true
        playerView.player = player

        player.play()
    }

    override fun initView() = with(binding) {
        setupPlayerView()
        toolbar.title = videoData.title
        tvTitle.text = videoData.title
        tvPresenter.text = videoData.presenterName
        tvDescription.text = videoData.videoDescription

    }

    override fun initData() {

    }

    override fun onStop() {
        super.onStop()
        player.stop()
        player.release()
        binding.playerView.player = null
    }
}