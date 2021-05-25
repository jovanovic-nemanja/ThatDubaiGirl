//
//  UIManager.swift
//  ThatDubaiGirl
//
//  Created by Bozo Krkeljas on 7.12.20..
//

import Foundation
import JGProgressHUD

class UIManager {
    static let shared = UIManager()
    var HUD: JGProgressHUD?
    
    func showHUD(view: UIView) {
        self.HUD = JGProgressHUD(style: .light)
        self.HUD?.indicatorView = JGProgressHUDIndeterminateIndicatorView()
        self.HUD?.show(in: view)
    }
    
    func hideHUD() {
        if (self.HUD != nil) {
            if (self.HUD?.isVisible)! {
                self.HUD?.dismiss()
            }
        }
    }
    
    func showAlert(vc: UIViewController, title: String?, message: String, handler: ((UIAlertAction) -> Swift.Void)? = nil) -> Void {
        let alertVC = UIAlertController(title: title, message: message, preferredStyle: .alert)
        let okAlert = UIAlertAction(title: "OK", style: .default, handler: handler)
        alertVC.addAction(okAlert)
        vc .present(alertVC, animated: true, completion: nil)
    }
}
