//
//  ResetViewController.swift
//  ThatDubaiGirl
//
//  Created by Bozo Krkeljas on 18.12.20..
//

import UIKit

class ResetViewController: UIViewController {

    public var userMail: String!
    @IBOutlet weak var tfEmail: UITextField!
    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        tfEmail.text = userMail
    }
    
    @IBAction func onReset(_ sender: Any) {
        guard let email = tfEmail.text, !email.isEmpty else {
            UIManager.shared.showAlert(vc: self, title: "Error", message: "Please input e-mail address.")
            return
        }
        
        if (!DataManager.isValidEmail(email)) {
            UIManager.shared.showAlert(vc: self, title: "Error", message: "Invalid e-mail address.")
            return
        }
        
        UIManager.shared.showHUD(view: self.view)
        
        APIManager.shared.resetPassword(email) { (success, message) in
            UIManager.shared.hideHUD()
            
            if (success) {
                UIManager.shared.showAlert(vc: self, title: "Success", message: message!) { (action) in
                    self.navigationController?.popViewController(animated: true)
                }
            } else {
                UIManager.shared.showAlert(vc: self, title: "Error", message: message!)
            }
        }
    }
    
    @IBAction func onBack(_ sender: Any) {
        self.navigationController?.popViewController(animated: true)
    }
    
    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destination.
        // Pass the selected object to the new view controller.
    }
    */

}
