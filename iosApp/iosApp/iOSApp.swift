import SwiftUI
import Shared

@main
struct iOSApp: App {
    
    let videoSDK = VideoSDK(databaseDriverFactory: IOSDatabaseDriverFactory())

    var body: some Scene {
        WindowGroup {
            ContentView(
                videoViewModel: .init(videoSDK: videoSDK)
            )
        }
    }
}
