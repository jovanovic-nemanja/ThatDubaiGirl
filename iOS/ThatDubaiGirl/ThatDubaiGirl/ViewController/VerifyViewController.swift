//
//  VerifyViewController.swift
//  ThatDubaiGirl
//
//  Created by Bozo Krkeljas on 7.12.20..
//

import UIKit

class VerifyViewController: UIViewController, UITextFieldDelegate {

    @IBOutlet weak var tfEmail: UITextField!
    @IBOutlet weak var tfCode: UITextField!
    
    @IBOutlet weak var btnSend: UIButton!
    @IBOutlet weak var btnResend: UIButton!
    @IBOutlet weak var btnConfirm: UIButton!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view.
        self.tfEmail.isHidden = false
        self.tfCode.isHidden = true
        self.btnSend.isHidden = false
        self.btnResend.isHidden = true
        self.btnConfirm.isHidden = true
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if (segue.identifier == "signup") {
            if let vcDest = segue.destination as? SignupViewController {
                vcDest.userMail = sender as? String
            }
        }
    }
    
    func sendCode() {
        let email = self.tfEmail.text
        
        UIManager.shared.showHUD(view: self.view)
        
        APIManager.shared.verify(email!) { (success, message) in
            UIManager.shared.hideHUD()
            
            if success {
                self.tfEmail.isHidden = true
                self.tfCode.isHidden = false
                self.btnSend.isHidden = true
                self.btnResend.isHidden = false
                self.btnConfirm.isHidden = false
            } else {
                UIManager.shared.showAlert(vc: self, title: "Error", message: message!)
            }
        }
    }
    
    @IBAction func onSendCode(_ sender: Any) {
        guard let email = tfEmail.text, !email.isEmpty else {
            UIManager.shared.showAlert(vc: self, title: "Error", message: "Please input email address")
            return
        }
        
        if (!DataManager.isValidEmail(email)) {
            UIManager.shared.showAlert(vc: self, title: "Error", message: "Invalid e-mail address.")
            return
        }

        sendCode()
    }

    @IBAction func onResend(_ sender: Any) {
        sendCode()
    }

    @IBAction func onConfirm(_ sender: Any) {
        let email = self.tfEmail.text
        let codeString =  self.tfCode.text
        let code = Int(codeString!)
        
        UIManager.shared.showHUD(view: self.view)
        APIManager.shared.validate(email!, code: code!) { (success, msg) in
            UIManager.shared.hideHUD()

            if success {
                self.performSegue(withIdentifier: "signup", sender: email)
            } else {
                UIManager.shared.showAlert(vc: self, title: "Error", message: msg!)
            }
        }
    }
    
    @IBAction func onBack(_ sender: Any) {
        self.navigationController?.popViewController(animated: true)
    }
    
    func textField(_ textField: UITextField, shouldChangeCharactersIn range: NSRange, replacementString string: String) -> Bool {
        let currentCharacterCount = textField.text?.count ?? 0
        if range.length + range.location > currentCharacterCount {
            return false
        }
        
        let newLength = currentCharacterCount + string.count - range.length
        return newLength <= 6
    }
}
