//
//  ProfileViewController.swift
//  ThatDubaiGirl
//
//  Created by Bozo Krkeljas on 18.12.20..
//

import UIKit
import Photos

class ProfileViewController: UIViewController {

    @IBOutlet weak var ivProfile: UIImageView!
    
    @IBOutlet weak var tfName: UITextField!
    @IBOutlet weak var tfBirthday: UITextField!
    @IBOutlet weak var tfAddress: UITextField!
    
    @IBOutlet weak var labelUniqueID: UILabel!
    
    var isPhotoSelected: Bool = false
    
    func generateBarcode(from string: String) -> UIImage? {
        let data = string.data(using: String.Encoding.ascii)

        if let filter = CIFilter(name: "CICode128BarcodeGenerator") {
            filter.setValue(data, forKey: "inputMessage")
            let transform = CGAffineTransform(scaleX: 3, y: 3)

            if let output = filter.outputImage?.transformed(by: transform) {
                return UIImage(ciImage: output)
            }
        }

        return nil
    }
    
    func initData() {
        if let photo = DataManager.currentUser?.photo {
            ivProfile.sd_setImage(with: URL(string: APIManager.urlImage + photo), placeholderImage:UIImage(named: "AvatarWhite"), completed: nil)
        }
        
        tfName.text = DataManager.currentUser?.name
        
        if let birthday = DataManager.currentUser?.birthday {
            let formatter = DateFormatter()
            formatter.dateFormat = "yyyy-MM-dd"
            let date = formatter.date(from: birthday)
            formatter.dateFormat = "dd-MM-yyyy"
            tfBirthday.text = formatter.string(from: date!)
        }
        
        tfAddress.text = DataManager.currentUser?.address

        labelUniqueID.text = "Membership Number: " + (DataManager.currentUser?.uniqueId)!
        
        isPhotoSelected = false
    }

    override func viewDidLoad() {
        super.viewDidLoad()

        self.ivProfile.layer.cornerRadius = 4
        self.ivProfile.layer.borderWidth = 2
        self.ivProfile.layer.borderColor = UIColor.white.cgColor
        
        // Do any additional setup after loading the view.

        let tapGestureRecognizer = UITapGestureRecognizer(target: self, action: #selector(imageTapped(tapGestureRecognizer:)))
        ivProfile.addGestureRecognizer(tapGestureRecognizer)

        tfBirthday.setDatePicker(target: self, selector: #selector(tapDone))

        initData()
    }
    
    @objc func tapDone() {
        if let datePicker = self.tfBirthday.inputView as? UIDatePicker {
            let dateformatter = DateFormatter()
            dateformatter.dateFormat = "dd-MM-yyyy"
            self.tfBirthday.text = dateformatter.string(from: datePicker.date)
        }
        self.tfBirthday.resignFirstResponder()
    }

    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)

//        self.tabBarController?.title = "My TDG"
        
//        self.tabBarController?.navigationItem.leftBarButtonItem = UIBarButtonItem(title: "Save", style: .plain, target: self, action: #selector(onSave))
//
//        self.tabBarController?.navigationItem.leftBarButtonItem?.tintColor = UIColor.systemBlue
//
//        self.tabBarController?.navigationItem.rightBarButtonItem = UIBarButtonItem(title: "Logout", style: .plain, target: self, action: #selector(onLogout))
//
//        self.tabBarController?.navigationItem.rightBarButtonItem?.tintColor = UIColor.red
    }
    
    override func viewWillDisappear(_ animated: Bool) {
        super.viewWillDisappear(true)
//        self.tabBarController?.navigationItem.leftBarButtonItem = nil
//        self.tabBarController?.navigationItem.rightBarButtonItem = nil
        
        if isPhotoSelected || somethingChanged() {
            let alertController = UIAlertController(title: "Alert", message: "Changes made are not saved. Do you wish to save changes made?", preferredStyle: .alert)
            
            let cancelOption = UIAlertAction(title: "Cancel", style: .cancel) { (action) in
                self.initData()
                self.isPhotoSelected = false
            }
            
            let saveOption = UIAlertAction(title: "Save", style: .default, handler: { (action) in
                self.updateProfile()
            })
            
            alertController.addAction(saveOption)
            alertController.addAction(cancelOption)
            present(alertController, animated: true)
        }
    }
    
    func somethingChanged() -> Bool {
        if (tfName.text != DataManager.currentUser?.name) {
            return true
        }
        
        if let birthday = DataManager.currentUser?.birthday {
            let formatter = DateFormatter()
            formatter.dateFormat = "yyyy-MM-dd"
            let date = formatter.date(from: birthday)
            formatter.dateFormat = "dd-MM-yyyy"
            let birthday = formatter.string(from: date!)

            if (tfBirthday.text != birthday) {
                return true
            }
        } else {
            if tfBirthday.text?.count != 0 {
                return true
            }
        }
        
        if let address = DataManager.currentUser?.address {
            if (tfAddress.text != address) {
                return true
            }
        }
        
        return false
    }
    
    func updateProfile() {
        UIManager.shared.showHUD(view: self.view)
        
        let username = (tfName.text != DataManager.currentUser?.name) ? tfName.text : nil
        
        let formatter = DateFormatter()
        formatter.dateFormat = "yyyy-MM-dd"
        
        var birthday:String? = nil
        if let xyz = DataManager.currentUser?.birthday {
            let date = formatter.date(from: xyz)
            formatter.dateFormat = "dd-MM-yyyy"
            let birthdayString = formatter.string(from: date!)
            birthday = (tfBirthday.text != birthdayString) ? tfBirthday.text : nil
        } else {
            birthday = tfBirthday.text
        }

        let address = (tfAddress.text != DataManager.currentUser?.address) ? tfAddress.text : nil
        let image = isPhotoSelected ? self.ivProfile.image : nil

        if !(birthday ?? "").isEmpty {
            formatter.dateFormat = "dd-MM-yyyy"
            let newBirthday = formatter.date(from: birthday!)
            formatter.dateFormat = "yyyy-MM-dd"
            birthday = formatter.string(from: newBirthday!)
        }
        
        if (DataManager.currentUser?.email != nil) {
            APIManager.shared.updateAccount((DataManager.currentUser?.email)!, username: username, birthday: birthday, address: address, image: image, { (success, user, message) in
                UIManager.shared.hideHUD()
                
                if success {
                    DataManager.currentUser = user
                    self.initData()
                } else {
                    UIManager.shared.showAlert(vc: self, title: "", message: message!)
                }
            })
        } else if (DataManager.currentUser?.appleID != nil) {
            APIManager.shared.updateAppleAccount((DataManager.currentUser?.appleID)!, username: username, birthday: birthday, address: address, image: image, { (success, user, message) in
                UIManager.shared.hideHUD()
                
                if success {
                    DataManager.currentUser = user
                    self.initData()
                } else {
                    UIManager.shared.showAlert(vc: self, title: "", message: message!)
                }
            })
        }
    }

    @IBAction func onSave(_ sender: Any) {
        if (!isPhotoSelected && !somethingChanged()) {
            return
        }
        
        self.updateProfile()
    }
    /*
    @objc func onSave() {
        if (!isPhotoSelected && !somethingChanged()) {
            return
        }
        
        self.updateProfile()
    }*/

    @IBAction func onLogout(_ sender: Any) {
        UserDefaults.standard.removeObject(forKey: "appleid")
        UserDefaults.standard.removeObject(forKey: "email")
        UserDefaults.standard.removeObject(forKey: "password")
        DataManager.currentUser = nil
        
        if let navController = self.navigationController {
            if let parentNav = navController.navigationController {
                parentNav.popToRootViewController(animated: true)
            }
        }
    }
    /*
    @objc func onLogout() {
        UserDefaults.standard.removeObject(forKey: "appleid")
        UserDefaults.standard.removeObject(forKey: "email")
        UserDefaults.standard.removeObject(forKey: "password")
        DataManager.currentUser = nil
        
        self.navigationController?.popToRootViewController(animated: true)
    }*/

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destination.
        // Pass the selected object to the new view controller.
    }
    */

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
}

extension ProfileViewController: UINavigationControllerDelegate, UIImagePickerControllerDelegate {
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
