//
//  VideoViewController.swift
//  ThatDubaiGirl
//
//  Created by Bozo Krkeljas on 7.12.20..
//

import UIKit
import AVKit

class VideoViewController: UIViewController, AVPlayerViewControllerDelegate {

    let vcPlayer = AVPlayerViewController()
    
    override func viewWillAppear(_ animated: Bool) {
        self.navigationController?.setNavigationBarHidden(true, animated: animated)
        super.viewWillAppear(animated)
        getVideoLink()
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view.
    }

    override func viewWillDisappear(_ animated: Bool) {
        self.navigationController?.setNavigationBarHidden(false, animated: animated)
        super.viewWillDisappear(animated)
        
        if (self.isMovingFromParent) {
            UIDevice.current.setValue(Int(UIInterfaceOrientation.portrait.rawValue), forKey: "orientation")
        }
    }
    
    override func viewDidDisappear(_ animated: Bool) {
        super.viewDidDisappear(animated)
        
        NotificationCenter.default.removeObserver(NSNotification.Name.AVPlayerItemDidPlayToEndTime)
    }
    
    @objc func canRotate() -> Void {}
    
    func getVideoLink() {
        UIManager.shared.showHUD(view: self.view)
        APIManager.shared.getVideo({ (success, data) in
            UIManager.shared.hideHUD()
            if success {
                self.playVideo(url: data ?? "")
            } else {
                self.performSegue(withIdentifier: "home", sender: nil)
            }
        })
    }
    
    func playVideo(url :String) {
        print(url)
        let player = AVPlayer(url: URL(string: url)!)
        vcPlayer.player = player
        vcPlayer.delegate = self
        NotificationCenter.default.addObserver(self, selector: #selector(playerDidFinishPlaying), name: NSNotification.Name.AVPlayerItemDidPlayToEndTime, object: vcPlayer.player?.currentItem)

        self.present(vcPlayer, animated: false) {
            self.vcPlayer.player?.play()
            self.vcPlayer.addObserver(self, forKeyPath: #keyPath(UIViewController.view.frame), options: [.old, .new], context: nil)
        }
    }
    
    @objc func playerDidFinishPlaying(note: NSNotification) {
        self.vcPlayer.dismiss(animated: false) {
            self.performSegue(withIdentifier: "home", sender: nil)
        }
    }
    
    override func observeValue(forKeyPath keyPath: String?, of object: Any?, change: [NSKeyValueChangeKey : Any]?, context: UnsafeMutableRawPointer?) {
        if (self.vcPlayer.isBeingDismissed) {
            self.vcPlayer.removeObserver(self, forKeyPath: #keyPath(UIViewController.view.frame))
            self.performSegue(withIdentifier: "home", sender: nil)
        }
    }
}
