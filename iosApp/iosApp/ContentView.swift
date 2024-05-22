import SwiftUI
import Shared

struct ContentView: View {
    @ObservedObject private(set) var videoViewModel: VideoViewModel

    var body: some View {
        NavigationView {
            ListVideoView(videoViewModel: videoViewModel)
        }
    }
}
