//
//  SignupViewController.swift
//  ThatDubaiGirl
//
//  Created by Bozo Krkeljas on 7.12.20..
//

import UIKit
import Photos

class SignupViewController: UIViewController {

    @IBOutlet weak var ivProfile: UIImageView!
    @IBOutlet weak var tfUserName: UITextField!
    @IBOutlet weak var tfUserMail: UITextField!
    @IBOutlet weak var tfBirthday: UITextField!
    @IBOutlet weak var tfAddress: UITextField!
    @IBOutlet weak var tfPassword: UITextField!
    @IBOutlet weak var tfConfirm: UITextField!
    
    var isPhotoSelected: Bool = false
    public var userMail: String!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view.
        tfUserMail.text = userMail
        tfBirthday.setDatePicker(target: self, selector: #selector(tapDone))
        
        let tapGestureRecognizer = UITapGestureRecognizer(target: self, action: #selector(imageTapped(tapGestureRecognizer:)))
        ivProfile.addGestureRecognizer(tapGestureRecognizer)
    }
    
    override func viewDidLayoutSubviews() {
        ivProfile.layer.cornerRadius = ivProfile.frame.size.width / 2
    }
    
    @IBAction func onBack(_ sender: Any) {
        self.navigationController?.popViewController(animated: true)
    }
    
    @objc func imageTapped(tapGestureRecognizer: UITapGestureRecognizer) {
        self.getImageData()
    }
    
    func getImageData() {
        let alertController = UIAlertController(title: "Choose Image", message: nil, preferredStyle: .actionSheet)
     
        alertController.addAction(UIAlertAction(title: "Camera", style: .default, handler: { _ in
            self.openImagePicker(sourceType: UIImagePickerController.SourceType.camera)
        }))
        
        alertController.addAction(UIAlertAction(title: "Photo Gallery", style: .default, handler: { _ in
            self.openImagePicker(sourceType: UIImagePickerController.SourceType.photoLibrary)
        }))
        
        alertController.addAction(UIAlertAction(title: "Cancel", style: .cancel, handler: nil))
        
        self.present(alertController, animated: true)
    }
    
    func openImagePicker(sourceType: UIImagePickerController.SourceType) {
        switch sourceType {
        case UIImagePickerController.SourceType.camera:
            isCameraPermissionAuthorized(objViewController: self) { (isAuthorized) in
                if isAuthorized {
                    DispatchQueue.main.async {
                        if (UIImagePickerController.isSourceTypeAvailable(.camera)) {
                            let objImagePicker = UIImagePickerController()
                            UINavigationBar.appearance().tintColor = UIColor.white
                            objImagePicker.allowsEditing = true
                            objImagePicker.delegate = self
                            objImagePicker.sourceType =  sourceType//.photoLibrary
                            objImagePicker.mediaTypes = ["public.image"] //,String(kUTTypeVideo),String(kUTTypeMPEG4)
                            objImagePicker.videoQuality = .typeIFrame960x540//.typeIFrame1280x720 //iFrame960x540
                            self.navigationController?.present(objImagePicker, animated: true, completion: nil)
                        }
                    }
                }
            }
            break
            
        case UIImagePickerController.SourceType.photoLibrary:
            isPhotoPermissionAuthorized(objViewController: self) { (isAuthorized) in
                if isAuthorized {
                    DispatchQueue.main.async {
                        if (UIImagePickerController.isSourceTypeAvailable(.photoLibrary)) {
                            let objImagePicker = UIImagePickerController()
                            UINavigationBar.appearance().tintColor = UIColor.white
                            objImagePicker.allowsEditing = true
                            objImagePicker.delegate = self
                            objImagePicker.sourceType =  sourceType//.photoLibrary
                            objImagePicker.mediaTypes = ["public.image"] //,String(kUTTypeVideo),String(kUTTypeMPEG4)
                            objImagePicker.videoQuality = .typeIFrame960x540//.typeIFrame1280x720 //iFrame960x540
                            self.navigationController?.present(objImagePicker, animated: true, completion: nil)
                        }
                    }
                }
            }
            break

        default:
            break
        }
    }
    
    func isCameraPermissionAuthorized(objViewController: UIViewController, completion:@escaping ((Bool) -> Void)) {

        let status = AVCaptureDevice.authorizationStatus(for: .video)

        switch status {
        case .authorized:
            completion(true)
            break

        case .notDetermined:
            AVCaptureDevice.requestAccess(for: .video, completionHandler: { (granted) in
                if granted {
                    completion(true)
                } else {
                    completion(false)
                }
            })
            break

        case .denied, .restricted:
            let strMessage: String = "Please allow access to your photos."
            let alertController = UIAlertController(title: "ThatDubaiGirl", message: strMessage, preferredStyle: .alert)

            let cancelAction = UIAlertAction(title: "Ok", style: .default) { action in
                self.dismiss(animated: true, completion: nil)
            }
            alertController.addAction(cancelAction)
            
            self.present(alertController, animated: true)
            break

        default:
            completion(false)
            break
        }
    }

    func isPhotoPermissionAuthorized(objViewController: UIViewController, completion:@escaping ((Bool) -> Void)) {

        let status = PHPhotoLibrary.authorizationStatus()

        switch status {
        case .authorized:
            completion(true)
            break

        case .notDetermined:
            PHPhotoLibrary.requestAuthorization({ (newStatus) in
                if newStatus == PHAuthorizationStatus.authorized {
                    completion(true)
                } else {
                    completion(false)
                }
            })
            break

        case .denied, .restricted:
            let strMessage: String = "Please allow access to your photos."
            let alertController = UIAlertController(title: "ThatDubaiGirl", message: strMessage, preferredStyle: .alert)

            let cancelAction = UIAlertAction(title: "Ok", style: .default) { action in
                self.dismiss(animated: true, completion: nil)
            }
            alertController.addAction(cancelAction)
            
            self.present(alertController, animated: true)
            break

        default:
            completion(false)
            break
        }
    }
    
    @objc func tapDone() {
        if let datePicker = self.tfBirthday.inputView as? UIDatePicker {
            let dateformatter = DateFormatter()
            dateformatter.dateFormat = "dd-MM-yyyy"
            self.tfBirthday.text = dateformatter.string(from: datePicker.date)
        }
        self.tfBirthday.resignFirstResponder()
    }

    @IBAction func onSignup(_ sender: Any) {
        if !isPhotoSelected {
            UIManager.shared.showAlert(vc: self, title: "ThatDubaiGirl", message: "Please select profile picture.")
            return
        }
        
        guard let name = tfUserName.text, !name.isEmpty else {
            UIManager.shared.showAlert(vc: self, title: "", message: "Please input name.")
            return
        }
        
        guard let email = tfUserMail.text, !email.isEmpty else {
            UIManager.shared.showAlert(vc: self, title: "", message: "Please input email address.")
            return
        }
        
        if (!DataManager.isValidEmail(email)) {
            UIManager.shared.showAlert(vc: self, title: "", message: "Invalid email address.")
            return
        }
        
        guard let address = tfAddress.text, !address.isEmpty else {
            UIManager.shared.showAlert(vc: self, title: "", message: "Please input address.")
            return
        }
        
        guard let birthday = tfBirthday.text, !birthday.isEmpty else {
            UIManager.shared.showAlert(vc: self, title: "", message: "Please input birthday.")
            return
        }
        
        if (!DataManager.validateBirthday(birthday)) {
            UIManager.shared.showAlert(vc: self, title: "", message: "You must be at least 18 years of age to sign up.")
            return
        }
        
        guard let password = tfPassword.text, !password.isEmpty else {
            UIManager.shared.showAlert(vc: self, title: "", message: "Please input password.")
            return
        }
        
        guard let confirm = tfConfirm.text, !confirm.isEmpty else {
            UIManager.shared.showAlert(vc: self, title: "", message: "Please input password")
            return
        }
        
        if (password != confirm) {
            UIManager.shared.showAlert(vc: self, title: "", message: "The password doesn't match.")
            return
        }
        
        if password.count < 6 {
            UIManager.shared.showAlert(vc: self, title: "", message: "Password must be at least 6 characters.")
            return
        }
        
        UIManager.shared.showHUD(view: self.view)

        let formatter = DateFormatter()
        formatter.dateFormat = "dd-MM-yyyy"
        let date = formatter.date(from: birthday)
        formatter.dateFormat = "yyyy-MM-dd"
        let birthdayString = formatter.string(from: date!)

        APIManager.shared.signup(email, username: name, birthday: birthdayString, address: address, password: password, password_confirmation: confirm, image: self.ivProfile.image!) { (success, user, msg) in
            
            UIManager.shared.hideHUD()
            
            if success {
                UserDefaults.standard.setValue(user?.email, forKey: "email")
                UserDefaults.standard.setValue(password, forKey: "password")
                DataManager.currentUser = user

                self.performSegue(withIdentifier: "video", sender: nil)
            } else {
                UIManager.shared.showAlert(vc: self, title: "", message: msg!)
            }
        }
    }
}

extension UITextField {
    func setDatePicker(target: Any, selector: Selector) {
        // Create a UIDatePicker object and assign to inputView
        let screenWidth = UIScreen.main.bounds.width
        let datePicker = UIDatePicker(frame: CGRect(x: 0, y: 0, width: screenWidth, height: 216))
        datePicker.datePickerMode = .date
        if #available(iOS 14, *) {// Added condition for iOS 14
            datePicker.preferredDatePickerStyle = .wheels
            datePicker.sizeToFit()
        }
        self.inputView = datePicker
        
        // Create a toolbar and assign it to inputAccessoryView
        let toolBar = UIToolbar(frame: CGRect(x: 0.0, y: 0.0, width: screenWidth, height: 44.0))
        let flexible = UIBarButtonItem(barButtonSystemItem: .flexibleSpace, target: nil, action: nil)
        let cancel = UIBarButtonItem(title: "Cancel", style: .plain, target: nil, action: #selector(tapCancel))
        let barButton = UIBarButtonItem(title: "Done", style: .plain, target: target, action: selector)
        toolBar.setItems([cancel, flexible, barButton], animated: false)
        self.inputAccessoryView = toolBar
    }
    
    @objc func tapCancel() {
        self.resignFirstResponder()
    }
}

extension SignupViewController: UINavigationControllerDelegate, UIImagePickerControllerDelegate {
    func imagePickerController(_ picker: UIImagePickerController, didFinishPickingMediaWithInfo info: [UIImagePickerController.InfoKey : Any]) {
            // Local variable inserted by Swift 4.2 migrator.
        let info = convertFromUIImagePickerControllerInfoKeyDictionary(info)

        if let possibleImage = info["UIImagePickerControllerEditedImage"] as? UIImage {
            self.ivProfile.image = possibleImage.resize(targetSize: CGSize(width: 128, height: 128))
            isPhotoSelected = true
        } else if let possibleImage = info["UIImagePickerControllerOriginalImage"] as? UIImage {
            self.ivProfile.image = possibleImage.resize(targetSize: CGSize(width: 128, height: 128))
            isPhotoSelected = true
        } else {
            isPhotoSelected = false
            return
        }

        picker.dismiss(animated: true)
    }

    func imagePickerControllerDidCancel(_ picker: UIImagePickerController) {
        isPhotoSelected = false
        picker.dismiss(animated: true, completion: nil)
    }

    // Helper function inserted by Swift 4.2 migrator.
    fileprivate func convertFromUIImagePickerControllerInfoKeyDictionary(_ input: [UIImagePickerController.InfoKey: Any]) -> [String: Any] {
        return Dictionary(uniqueKeysWithValues: input.map {key, value in (key.rawValue, value)})
    }
}

extension UIImage {
    func resize(targetSize: CGSize) -> UIImage {
        return UIGraphicsImageRenderer(size:targetSize).image { _ in
            self.draw(in: CGRect(origin: .zero, size: targetSize))
        }
    }
}
