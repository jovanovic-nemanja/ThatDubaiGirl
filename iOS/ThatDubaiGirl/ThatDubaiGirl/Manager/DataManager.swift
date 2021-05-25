//
//  DataManager.swift
//  ThatDubaiGirl
//
//  Created by Bozo Krkeljas on 7.12.20..
//

import UIKit
import Foundation
import LocalAuthentication

class DataManager {
    static let shared = DataManager()
    
    public static var categoryID = 0
    
    public static var currentUser: User?
    
    public static var categories: [Category] = []
    public static var discounts: [Discount] = []

    public static func isValidEmail(_ email: String) -> Bool {
        let emailRegEx = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,64}"

        let emailPred = NSPredicate(format:"SELF MATCHES %@", emailRegEx)
        return emailPred.evaluate(with: email)
    }
    
    public static func validateBirthday(_ birthday: String) ->Bool {
        let now = Date()

        let dateformatter = DateFormatter()
        dateformatter.dateFormat = "dd-MM-yyyy"
        if let from = dateformatter.date(from: birthday) {
            let calendar = Calendar.current
            
            let ageComponent = calendar.dateComponents([.year], from: from, to: now)
            if (ageComponent.year! < 18) {
                return false
            }
            
            return true
        }
        
        return false
    }
    
    public static func loadDatas(_ view: UIView, completion: @escaping () -> ()) {
        UIManager.shared.showHUD(view: view)
        APIManager.shared.getCategories { (success, categories, msg) in
            
            if success {
                self.categories = categories!
            } else {
                self.categories = []
                UIManager.shared.hideHUD()
                completion()
                return
            }
            
            APIManager.shared.getDiscounts("", 0) { (success, discounts, msg) in
                if success {
                    self.discounts = discounts!
                } else {
                    self.discounts = []
                }
                
                print(self.discounts)

                UIManager.shared.hideHUD()
                completion()
            }
        }
    }
    
    public static func authentication(_ completion: @escaping (Bool) -> ()) {
        let AUth = LAContext()
        var autherror:NSError?
        AUth.canEvaluatePolicy(LAPolicy.deviceOwnerAuthenticationWithBiometrics, error: &autherror)
        // Do any additional setup after loading the view.
        if autherror != nil
        {
            // there is an error : not available
            print("auth is not available on the ios")
            completion(true)
        }
        else
        {
            // auth is available
            AUth.evaluatePolicy(LAPolicy.deviceOwnerAuthenticationWithBiometrics, localizedReason: "The app contains SENSETIVE contents, use your biometric ID to unlock.", reply: { ( complete:Bool! , error:Error!) ->Void in
                if error != nil
                {
                    //there is an error
                    print(error.localizedDescription)
                }
                else{
                    //all set
                    if complete == true
                    {
                        print("auth successful")
                        // is auth success , goes to next page
                        completion(true)
                    }
                    else
                    {
                        //user was not the correct user
                        print("auth failed")
                        //we have an error
                        print(autherror?.localizedDescription ?? "...")
                        //show the normal screen
                        AUth.canEvaluatePolicy(LAPolicy.deviceOwnerAuthentication, error: &autherror)
                        completion(false)
                    }
                }
            })
        }
    }
}
