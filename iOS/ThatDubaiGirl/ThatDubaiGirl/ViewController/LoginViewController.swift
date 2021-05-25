//
//  LoginViewController.swift
//  ThatDubaiGirl
//
//  Created by Bozo Krkeljas on 7.12.20..
//

import UIKit

class LoginViewController: UIViewController {

    @IBOutlet weak var tfEmail: UITextField!
    @IBOutlet weak var tfPassword: UITextField!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view.
    }
    
    @IBAction func onBack(_ sender: Any) {
        self.navigationController?.popViewController(animated: true)
    }
    
    @IBAction func onLogin(_ sender: Any) {
        guard let email = tfEmail.text, !email.isEmpty else {
            UIManager.shared.showAlert(vc: self, title: "Error", message: "Please input e-mail address.")
            return
        }
        
        if (!DataManager.isValidEmail(email)) {
            UIManager.shared.showAlert(vc: self, title: "Error", message: "Invalid e-mail address.")
            return
        }
        
        guard let password = tfPassword.text, !password.isEmpty else {
            UIManager.shared.showAlert(vc: self, title: "Error", message: "Please input your password.")
            return
        }
        /*
        DataManager.authentication({ (success) in
            if (success) {
                DispatchQueue.main.async {
                    UIManager.shared.showHUD(view: self.view)
                    
                    APIManager.shared.login(email, password: password) { (success, user, message) in
                        UIManager.shared.hideHUD()
                        
                        if (success) {
                            UserDefaults.standard.setValue(user?.email, forKey: "email")
                            UserDefaults.standard.setValue(password, forKey: "password")
                            DataManager.currentUser = user
                            
                            self.performSegue(withIdentifier: "video", sender: nil)
                        } else {
                            UIManager.shared.showAlert(vc: self, title: "Error", message: message!)
                            return
                        }
                    }
                }
            }
        })*/
        UIManager.shared.showHUD(view: self.view)
        
        APIManager.shared.login(email, password: password) { (success, user, message) in
            UIManager.shared.hideHUD()
            
            if (success) {
                UserDefaults.standard.setValue(user?.email, forKey: "email")
                UserDefaults.standard.setValue(password, forKey: "password")
                DataManager.currentUser = user
                
                self.performSegue(withIdentifier: "video", sender: nil)
            } else {
                UIManager.shared.showAlert(vc: self, title: "Error", message: message!)
                return
            }
        }
    }
    
    @IBAction func onReset(_ sender: Any) {
        self.performSegue(withIdentifier: "reset", sender: tfEmail.text)
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if (segue.identifier == "reset") {
            if let vcDest = segue.destination as? ResetViewController {
                vcDest.userMail = sender as? String
            }
        }
    }
}
