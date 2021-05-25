//
//  RateViewController.swift
//  ThatDubaiGirl
//
//  Created by Bozo Krkeljas on 1/4/21.
//

import UIKit
import Cosmos

protocol RateViewControllerDelegate: class {
    func updateReview()
}

class RateViewController: UIViewController, UITextViewDelegate {

    public var discount: Discount!
    public var review: Review!

    @IBOutlet weak var cosmosView: CosmosView!
    @IBOutlet weak var textField: UITextField!
    
    weak var delegate: RateViewControllerDelegate?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        if review != nil {
            textField.text = review.comments
        }
    }

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destination.
        // Pass the selected object to the new view controller.
    }
    */
    
    @IBAction func onCancel(_ sender: Any) {
        self.navigationController?.popViewController(animated: true)
    }
    
    @IBAction func onSubmit(_ sender: Any) {
        UIManager.shared.showHUD(view: self.view)
        
        let userId = String(DataManager.currentUser!.id)
        let discountId = self.discount.id
        let marks = Int(self.cosmosView.rating)
        let comments = self.textField.text
        let reviewID = (review != nil) ? review.id : nil
        
        APIManager.shared.putReview(userId, discountId!, marks, comments!, reviewID) { (success, message) in
            UIManager.shared.hideHUD()
            
            if (success) {
                if (self.delegate != nil) {
                    self.delegate?.updateReview()
                }
                self.navigationController?.popViewController(animated: true)
            } else {
                UIManager.shared.showAlert(vc: self, title: "Error", message: message!)
                return
            }
        }
    }
}
