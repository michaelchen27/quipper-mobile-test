import Foundation
import Shared
import Network

class VideoViewModel: ObservableObject {
    
    private let networkMonitor = NWPathMonitor()
        
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
                                
            } else if let error = error as? NSError {
                switch error.kotlinException {
                    
                case is CustomException.NetworkError:
                    self.videos = .error("Network error. Please try again later.")
                    
                case is CustomException.OfflineError:
                    self.videos = .error("No internet connection. Please check your network and try again.")
                    
                case is CustomException.UnknownError:
                    self.checkWithNetworkMonitoring()
                    
                default:
                    self.videos = .error("Other error.")
                }
                
            } else {
                self.videos = .error(error?.localizedDescription ?? "Error")
            }
            
        })
    }
    
    
    // Error Domain=NSURLErrorDomain Code=-1009 "The Internet connection appears to be offline.".
    // iOS Specific error always go to this block because kotlin Ktor doesn't support NSError. How to catch iOS error in commonMain? Current work around is using network monitor.
    private func checkWithNetworkMonitoring() {
        self.networkMonitor.pathUpdateHandler = { [weak self] path in
            DispatchQueue.main.async {
                if path.status != .satisfied {
                    self!.videos = .error("No internet connection. Please check your network and try again.")
                    return
                    
                } else {
                    self!.videos = .error("An unexpected error occurred. Please try again.")
                    return

                }
            }
        }
        
        let queue = DispatchQueue(label: "Monitor")
        self.networkMonitor.start(queue: queue)
    }

    
    
}
