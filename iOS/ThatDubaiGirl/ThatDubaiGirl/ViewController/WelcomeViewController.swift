//
//  ViewController.swift
//  ThatDubaiGirl
//
//  Created by Bozo Krkeljas on 7.12.20..
//

import UIKit
import AuthenticationServices

class WelcomeViewController: UIViewController {

    @IBOutlet weak var btnLogin: UIButton!
    @IBOutlet weak var btnSignup: UIButton!
    @IBOutlet weak var btnAppleLogin: ASAuthorizationAppleIDButton!
    
    override func viewWillAppear(_ animated: Bool) {
        self.navigationController?.setNavigationBarHidden(true, animated: animated)
        super.viewWillAppear(animated)
        
        btnAppleLogin.cornerRadius = 24
        btnAppleLogin.addTarget(self, action: #selector(handleAuthorizationAppleIDButtonPress), for: .touchUpInside)
        
        if (UserDefaults.standard.value(forKey: "appleid") != nil) {
            performExistingAccountSetupFlows()
        } else if ((UserDefaults.standard.value(forKey: "email") != nil) && (UserDefaults.standard.value(forKey: "password") != nil)) {
            
            DataManager.authentication({ (success) in
                if (success) {
                    DispatchQueue.main.async {
                        let email = UserDefaults.standard.value(forKey: "email") as! String
                        let password = UserDefaults.standard.value(forKey: "password") as! String
                        
                        UIManager.shared.showHUD(view: self.view)
                        
                        APIManager.shared.login(email, password: password) { (success, user, message) in
                            UIManager.shared.hideHUD()
                            
                            if (success) {
                                UserDefaults.standard.setValue(user?.email, forKey: "email")
                                UserDefaults.standard.setValue(password, forKey: "password")
                                DataManager.currentUser = user
                                
                                self.performSegue(withIdentifier: "home", sender: nil)
                            } else {
                                UserDefaults.standard.removeObject(forKey: "email")
                                UserDefaults.standard.removeObject(forKey: "password")
                                DataManager.currentUser = nil
                                
                                UIManager.shared.showAlert(vc: self, title: "Error", message: message!)
                                return
                            }
                        }
                    }
                }
            })
        }
    }
    
    func performExistingAccountSetupFlows() {
        // Prepare requests for both Apple ID and password providers.
        let requests = [ASAuthorizationAppleIDProvider().createRequest(),
                        ASAuthorizationPasswordProvider().createRequest()]
        
        // Create an authorization controller with the given requests.
        let authorizationController = ASAuthorizationController(authorizationRequests: requests)
        authorizationController.delegate = self
        authorizationController.presentationContextProvider = self
        authorizationController.performRequests()
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view.
        btnSignup.layer.borderColor = UIColor.white.cgColor
        btnSignup.layer.borderWidth = 1
    }
    
    override func viewWillDisappear(_ animated: Bool) {
        self.navigationController?.setNavigationBarHidden(false, animated: animated)
        super.viewWillDisappear(animated)
    }
    
    @objc func handleAuthorizationAppleIDButtonPress() {
        let appleIDProvider = ASAuthorizationAppleIDProvider()
        let request = appleIDProvider.createRequest()
        request.requestedScopes = [.fullName, .email]
        
        let authorizationController = ASAuthorizationController(authorizationRequests: [request])
        authorizationController.delegate = self
        authorizationController.presentationContextProvider = self
        authorizationController.performRequests()
    }
    
    @IBAction func joinFacebook(_ sender: Any) {
        if let url = URL(string: "https://www.facebook.com/groups/thatdubaigirl/?ref=share") {
            UIApplication.shared.open(url)
        }
    }
    
    @IBAction func joinYoutube(_ sender: Any) {
        if let url = URL(string: "https://www.instagram.com/thatdubaigirl_/?hl=en"/*"https://youtu.be/VSo41Y9i2Ug"*/) {
            UIApplication.shared.open(url)
        }
    }
}

extension WelcomeViewController: ASAuthorizationControllerDelegate {
    func authorizationController(controller: ASAuthorizationController, didCompleteWithAuthorization authorization: ASAuthorization) {
        switch authorization.credential {
        case let appleIDCredential as ASAuthorizationAppleIDCredential:
            
            // Create an account in your system.
            let appleID = appleIDCredential.user
            let fullName = appleIDCredential.fullName
            let email = appleIDCredential.email
            let formatter = PersonNameComponentsFormatter()
            formatter.style = .default
            
            UIManager.shared.showHUD(view: self.view)
            
            APIManager.shared.loginApple(appleID: appleID, email: email, userName: formatter.string(from: fullName!)) { (success, user, newUser, message) in
                UIManager.shared.hideHUD()
                
                if (success) {
                    UserDefaults.standard.setValue(user?.appleID, forKey: "appleid")
                    DataManager.currentUser = user
                    
                    if (newUser) {
                        self.performSegue(withIdentifier: "video", sender: nil)
                    } else {
                        self.performSegue(withIdentifier: "home", sender: nil)
                    }
                } else {
                    UserDefaults.standard.removeObject(forKey: "appleid")
                    DataManager.currentUser = nil
                    
                    UIManager.shared.showAlert(vc: self, title: "Error", message: message!)
                    return
                }
            }
        
        default:
            break
        }
    }

    func authorizationController(controller: ASAuthorizationController, didCompleteWithError error: Error) {
        UIManager.shared.showAlert(vc: self, title: "Error", message: error.localizedDescription)
    }
}

extension WelcomeViewController: ASAuthorizationControllerPresentationContextProviding {
    func presentationAnchor(for controller: ASAuthorizationController) -> ASPresentationAnchor {
        return self.view.window!
    }
}
