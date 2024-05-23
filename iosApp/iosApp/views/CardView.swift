//
//  CardView.swift
//  iosApp
//
//  Created by Michael Chen on 22/05/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import SwiftUI
import Shared

struct CardView: View {
    let video: Video

    var body: some View {
        HStack(alignment: .top, spacing: 12) {
            AsyncImage(url: URL(string: video.thumbnailUrl ?? "")) { image in
                image
                    .resizable()
                    .aspectRatio(contentMode: .fit)
            } placeholder: {
                ProgressView()
            }
            .frame(width: 120, height: 70)
            .cornerRadius(8)

            VStack(alignment: .leading, spacing: 4) {
                Text(video.title ?? "")
                    .font(.headline)

                
                HStack(spacing: 4) {
                    Image(systemName: "person.circle.fill")
                        .foregroundColor(.secondary)
                    
                    Text(video.presenterName ?? "")
                        .font(.subheadline)
                }
                .padding(.top, 2)
                            
                Text(TimeUtil().formatMillisToStopwatch(millis: video.videoDuration ?? 0))
                    .font(.caption)
                    .foregroundColor(.secondary)
                    .padding(.top, 2)
                
                
            }
            .padding(.leading, 4)
            .frame(maxWidth: .infinity, alignment: .leading)
        }
        .padding(10)
        .background(Color(.white))
        .overlay(
            RoundedRectangle(cornerRadius: 4)
                .stroke(Color(.systemGray2), lineWidth: 1)
        )
        .cornerRadius(4)
        .padding(.horizontal, 8)
        .padding(.vertical, 1)
    }
}

struct CardView_Previews: PreviewProvider {
    static var previews: some View {
        CardView(video: Video(
            title: "Video Title",
            presenterName: "Mykull Chieen",
            videoDescription: "AAAAAA",
            thumbnailUrl: "https://cdn.dribbble.com/users/13213684/screenshots/19501224/meme_surprised_shocked_pikachu_1x.png",
            videoUrl: "https://example.com/example.jpg",
            videoDuration: 3600000
        ))
        .previewLayout(.sizeThatFits)
    }
}

