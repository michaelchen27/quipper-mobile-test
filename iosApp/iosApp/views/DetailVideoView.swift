//
//  DetailVideoView.swift
//  iosApp
//
//  Created by Michael Chen on 22/05/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import SwiftUI
import AVKit
import Shared

struct DetailVideoView: View {
    var video: Video

    var body: some View {
        ScrollView {
            VStack(alignment: .leading, spacing: 8) {
                // Video Player
                VideoPlayer(player: AVPlayer(url: URL(string: video.videoUrl ?? "")!))
                    .frame(height: 200)
                
                // Duration
                Text("Duration: \( TimeUtil().formatMillisToStopwatch(millis: video.videoDuration ?? 0))")
                    .font(.caption)
                    .foregroundColor(.secondary)

                // Title and Presenter
                Text(video.title ?? "")
                    .font(.largeTitle)

                HStack {
                    Image(systemName: "person.circle.fill")
                        .foregroundColor(.secondary)
                    Text(video.presenterName ?? "")
                        .font(.headline)
                }

                // Description
                Text(video.videoDescription ?? "")
                    .padding(.bottom, 8)


            }
            .padding()
        }
        .navigationTitle(video.title ?? "")
        .navigationBarTitleDisplayMode(.inline)
    }
}
