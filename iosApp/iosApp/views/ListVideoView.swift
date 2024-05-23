//
//  ListVideoView.swift
//  iosApp
//
//  Created by Michael Chen on 22/05/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import SwiftUI

struct ListVideoView: View {
    
    @ObservedObject var videoViewModel: VideoViewModel

    var body: some View {
        ScrollView (.vertical, showsIndicators: false) {
            VStack (alignment: .leading) {
                videoList()

            }
        }.refreshable {
            videoViewModel.loadVideos(forceReload: true)
        }
        .navigationTitle("Video List")
        .navigationBarTitleDisplayMode(.inline)
    }
    
    
    private func videoList() -> AnyView {
        switch videoViewModel.videos {
            
        case .loading:
            return AnyView(Text("Loading...").multilineTextAlignment(.center))
            
            
        case .result(let videos):
            return AnyView(
                    ForEach(videos, id: \.self) { video in
                        NavigationLink(destination: DetailVideoView(video: video)) {
                            CardView(video: video)
                        }
                        .buttonStyle(PlainButtonStyle())

                    }
            )
            
        case .error(let errMsg):
            return AnyView(
                Text(errMsg)
                    .multilineTextAlignment(.center)
                    .padding(.bottom, 150)
                    .padding(.horizontal, 16)
            )
        }
    }
}

