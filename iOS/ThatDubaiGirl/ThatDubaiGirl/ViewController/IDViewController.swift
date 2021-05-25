//
//  IDViewController.swift
//  ThatDubaiGirl
//
//  Created by Bozo Krkeljas on 9.12.20..
//

import UIKit

class IDViewController: UIViewController {

    @IBOutlet weak var viewIDView: UIView!
    @IBOutlet weak var ivLogo: UIImageView!
    @IBOutlet weak var ivUser: UIImageView!
    @IBOutlet weak var ivVendor: UIImageView!
    @IBOutlet weak var labelTitle: UILabel!
    @IBOutlet weak var labelUser: UILabel!
    @IBOutlet weak var labelUserID: UILabel!
    @IBOutlet weak var labelCoupon: UILabel!
    
    public var discount: Discount!
    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        ivVendor.sd_setImage(with: URL(string: APIManager.urlImage + discount.vendorPhoto!), completed: nil)
        
        if let photo = DataManager.currentUser?.photo {
            ivUser.sd_setImage(with: URL(string: APIManager.urlImage + photo), placeholderImage:UIImage(named: "Placeholder"), completed: nil)
        }
        
        labelTitle.text = discount.title
        labelUser.text = DataManager.currentUser?.name
        
        labelUserID.text = (DataManager.currentUser?.uniqueId)!
        
        if let coupon = discount.coupon {
            labelCoupon.text = coupon
        } else {
            labelCoupon.text = ""
        }
    }
    
    override func viewDidLayoutSubviews() {
        viewIDView.layer.borderWidth = 2
        viewIDView.layer.borderColor = UIColor.white.cgColor

        ivLogo.clipsToBounds = true
        ivLogo.layer.masksToBounds = true
//        ivLogo.layer.cornerRadius = ivLogo.frame.size.width / 2
//        ivLogo.layer.borderWidth = 0.5
//        ivLogo.layer.borderColor = UIColor.white.cgColor

        ivUser.clipsToBounds = true
        ivUser.layer.masksToBounds = true
//        ivUser.layer.cornerRadius = ivUser.bounds.width / 2
        ivUser.layer.borderWidth = 0.5
        ivUser.layer.borderColor = UIColor.white.cgColor

        ivVendor.clipsToBounds = true
        ivVendor.layer.masksToBounds = true
//        ivVendor.layer.cornerRadius = ivVendor.bounds.width / 2
//        ivVendor.layer.borderWidth = 0.5
//        ivVendor.layer.borderColor = UIColor.white.cgColor
    }
    

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destination.
        // Pass the selected object to the new view controller.
    }
    */

    @IBAction func onBack(_ sender: Any) {
        self.navigationController?.popViewController(animated: true)
    }
    
    func generateQRCode() -> UIImage? {
        let appLogo = UIImage(named: "AppIconWhite.png")!
        let qrURLImage = DataManager.currentUser?.uniqueId!.qrImage(using: UIColor.black, logo: appLogo)
        return UIImage(ciImage: qrURLImage!)
    }
}

extension CIImage {
    /// Inverts the colors and creates a transparent image by converting the mask to alpha.
    /// Input image should be black and white.
    var transparent: CIImage? {
        return inverted?.blackTransparent
    }

    /// Inverts the colors.
    var inverted: CIImage? {
        guard let invertedColorFilter = CIFilter(name: "CIColorInvert") else { return nil }

        invertedColorFilter.setValue(self, forKey: "inputImage")
        return invertedColorFilter.outputImage
    }

    /// Converts all black to transparent.
    var blackTransparent: CIImage? {
        guard let blackTransparentFilter = CIFilter(name: "CIMaskToAlpha") else { return nil }
        blackTransparentFilter.setValue(self, forKey: "inputImage")
        return blackTransparentFilter.outputImage
    }

    /// Applies the given color as a tint color.
    func tinted(using color: UIColor) -> CIImage? {
        guard
            let transparentQRImage = transparent,
            let filter = CIFilter(name: "CIMultiplyCompositing"),
            let colorFilter = CIFilter(name: "CIConstantColorGenerator") else { return nil }

        let ciColor = CIColor(color: color)
        colorFilter.setValue(ciColor, forKey: kCIInputColorKey)
        let colorImage = colorFilter.outputImage

        filter.setValue(colorImage, forKey: kCIInputImageKey)
        filter.setValue(transparentQRImage, forKey: kCIInputBackgroundImageKey)

        return filter.outputImage!
    }
    
    /// Combines the current image with the given image centered.
    func combined(with image: CIImage) -> CIImage? {
        guard let combinedFilter = CIFilter(name: "CISourceOverCompositing") else { return nil }
        let centerTransform = CGAffineTransform(translationX: extent.midX - (image.extent.size.width / 2), y: extent.midY - (image.extent.size.height / 2))
        combinedFilter.setValue(image.transformed(by: centerTransform), forKey: "inputImage")
        combinedFilter.setValue(self, forKey: "inputBackgroundImage")
        return combinedFilter.outputImage!
    }
}

extension String {

    /// Creates a QR code for the current URL in the given color.
    func qrImage(using color: UIColor) -> CIImage? {
        return qrImage?.tinted(using: color)
    }

    /// Returns a black and white QR code for this URL.
    var qrImage: CIImage? {
        guard let qrFilter = CIFilter(name: "CIQRCodeGenerator") else { return nil }
        let qrData = self.data(using: String.Encoding.ascii)
        qrFilter.setValue(qrData, forKey: "inputMessage")

        let qrTransform = CGAffineTransform(scaleX: 12, y: 12)
        return qrFilter.outputImage?.transformed(by: qrTransform)
    }
    
    func qrImage(using color: UIColor, logo: UIImage? = nil) -> CIImage? {
        let tintedQRImage = qrImage?.tinted(using: color)

        guard let logo = logo?.cgImage else {
            return tintedQRImage
        }

        return tintedQRImage?.combined(with: CIImage(cgImage: logo))
    }
}

