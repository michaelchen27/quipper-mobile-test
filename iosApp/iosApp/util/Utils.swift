//
//  Utils.swift
//  iosApp
//
//  Created by Michael Chen on 22/05/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation


func formatDuration(milliseconds: Int64) -> String {
    let seconds = milliseconds / 1000 // Convert milliseconds to seconds
    let formatter = DateComponentsFormatter()
    formatter.allowedUnits = [.hour, .minute, .second]
    formatter.zeroFormattingBehavior = [.pad]

    // Use TimeInterval(seconds) for formatting
    let formattedDuration = formatter.string(from: TimeInterval(seconds)) ?? "00:00"

    if seconds < 3600 {
        return formattedDuration.replacingOccurrences(of: "^0:", with: "", options: .regularExpression)
    }

    return formattedDuration
}
