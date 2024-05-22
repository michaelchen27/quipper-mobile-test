import Foundation
import Shared

class VideoViewModel: ObservableObject {
    
    init(videoSDK: VideoSDK) {
        self.videoSDK = videoSDK
        self.loadVideos(forceReload: false)
    }
    
    let videoSDK: VideoSDK
    
    enum LoadableVideo {
        case loading
        case result([Video])
        case error(String)
    }
    
    @Published var videos = LoadableVideo.loading

    
    func loadVideos(forceReload: Bool) {
        self.videos = .loading
        
        videoSDK.getVideos(forceReload: forceReload, completionHandler: { videos, error in
            if let videos = videos {
                self.videos = .result(videos)
            } else {
                self.videos = .error(error?.localizedDescription ?? "Error")
            }
            
        })
    }

    
    
}
