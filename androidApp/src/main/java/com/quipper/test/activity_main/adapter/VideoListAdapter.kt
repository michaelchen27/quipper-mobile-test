package com.quipper.test.activity_main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import coil.load
import com.quipper.test.core.base.BaseAdapter
import com.quipper.test.databinding.ItemVideoBinding
import com.quipper.test.model.VideoData
import model.Video

class VideoListAdapter(private val onClick: (VideoData) -> Unit): BaseAdapter<ItemVideoBinding, VideoData>() {
    override fun bind(holder: ItemVideoBinding, item: VideoData, position: Int) = with(holder) {
        ivThumbnail.load(item.thumbnailUrl)
        tvTitle.text = item.title
        tvPresenter.text = item.presenterName
        tvDuration.text = item.videoDuration

        root.setOnClickListener {
            onClick.invoke(item)
        }
    }

    override fun createBinding(
        viewType: Int,
        inflater: LayoutInflater,
        parent: ViewGroup
    ): ViewBinding = ItemVideoBinding.inflate(inflater, parent, false)

}