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
                    .aspectRatio(contentMode: .fill)
            } placeholder: {
                ProgressView()
            }
            .frame(width: 120, height: 80)
            .cornerRadius(8)

            VStack(alignment: .leading) {
                Text(video.title ?? "")
                    .font(.headline)

                HStack {
                    Image(systemName: "person.circle.fill")
                        .foregroundColor(.secondary)
                    
                    Text(video.presenterName ?? "")
                        .font(.subheadline)
                }
                .padding(.top, 2)
                
                Text(formatDuration(milliseconds: Int64(video.videoDuration ?? 0)))
            }
            .padding(.leading, 4)
            .frame(maxWidth: .infinity, alignment: .leading)
        }
        .padding()
        .background(Color(.systemGray6))
        .cornerRadius(12)

    }
}
