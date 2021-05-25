//
//  DetailViewController.swift
//  ThatDubaiGirl
//
//  Created by Bozo Krkeljas on 7.12.20..
//

import UIKit
import SDWebImage
import HMSegmentedControl
import MessageUI

class DetailViewController: UIViewController, UITableViewDelegate, UITableViewDataSource {
    
    public var discount: Discount?
    private var detail: DiscountDetail?
    private var hasLeftReview: Bool = false
    
    @IBOutlet weak var ivDiscount: UIImageView!
    @IBOutlet weak var ivBanner: UIImageView!
    @IBOutlet weak var labelDiscountTitle: UILabel!
    @IBOutlet weak var labelDiscountDetail: UILabel!
    
    @IBOutlet weak var viewVendor: UIView!
    @IBOutlet weak var ivVendor: UIImageView!
    @IBOutlet weak var labelVendorName: UILabel!
    @IBOutlet weak var labelVendorLocation: UILabel!
//    @IBOutlet weak var labelVendorPhone: UILabel!
    
    @IBOutlet weak var segmentedControl: HMSegmentedControl!
    
    @IBOutlet weak var detailView: UIScrollView!
    @IBOutlet weak var reviewView: UITableView!
    
    @IBOutlet weak var buttonAction: UIButton!
    
    func initData() {
        UIManager.shared.showHUD(view: self.view)
        APIManager.shared.getDiscountDetail(Int((self.discount?.id!)!)!) { (success, detail, msg) in
            UIManager.shared.hideHUD()
            if success {
                if let detail = detail {
                    self.detail = detail[0]
                }
            } else {
                self.detail = nil
            }
            
            self.hasLeftReview = false
            
            if (self.detail != nil && (self.detail?.reviews.count)! > 0) {
                for review in (self.detail?.reviews)! {
                    if review.putter == String(DataManager.currentUser!.id) {
                        self.hasLeftReview = true
                    }
                }
            }

            self.reviewView.reloadData()
        }
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view.
        ivVendor.clipsToBounds = true
        ivVendor.layer.masksToBounds = true
        ivVendor.layer.cornerRadius = ivVendor.bounds.width / 2
        ivVendor.layer.borderWidth = 0.5
        ivVendor.layer.borderColor = UIColor(red: 0, green: 0, blue: 0, alpha: 0.5).cgColor
        
        ivDiscount.sd_setImage(with: URL(string: APIManager.urlImage + (discount?.photo)!), completed: nil)
        labelDiscountTitle.text = discount?.title
        labelDiscountDetail.text = discount?.description
        
        ivVendor.sd_setImage(with: URL(string: APIManager.urlImage + (discount?.vendorPhoto)!), completed: nil)
        labelVendorName.text = discount?.vendorName
        labelVendorLocation.text = discount?.location
        
        ivBanner.isHidden = !(Int((discount?.status)!) == 2)
        
        segmentedControl.sectionTitles = ["Overview", "Reviews"]
        self.segmentedControl.backgroundColor = UIColor.black
        self.segmentedControl.selectionStyle = .fullWidthStripe
        self.segmentedControl.selectionIndicatorLocation = .bottom
        
        self.segmentedControl.titleTextAttributes = [NSAttributedString.Key.foregroundColor: UIColor.white]
        self.segmentedControl.selectedTitleTextAttributes = [NSAttributedString.Key.foregroundColor: UIColor.systemBlue]
        
        let titleFormatterBlock: HMTitleFormatterBlock = {(control: AnyObject!, title: String!, index: UInt, selected: Bool) -> NSAttributedString in
            return NSAttributedString(string: title, attributes: [NSAttributedString.Key.foregroundColor: UIColor.white, NSAttributedString.Key.font: UIFont.systemFont(ofSize: 16)])
        }
        self.segmentedControl.titleFormatter = titleFormatterBlock
        
        self.segmentedControl.addTarget(self, action: #selector(onChanged), for: .valueChanged)
        
        self.reviewView.register(UINib(nibName: "ReviewTableViewCell", bundle: nil), forCellReuseIdentifier: "ReviewCell")

        initData()
    }
    
    @objc func onChanged() {
        switch self.segmentedControl.selectedSegmentIndex {
        case 0:
            self.detailView.isHidden = false
            self.reviewView.isHidden = true
            self.buttonAction.setTitle("Redeem Discount", for: .normal)
            break
            
        case 1:
            self.detailView.isHidden = true
            self.reviewView.isHidden = false
            self.buttonAction.setTitle("Write a Review", for: .normal)
            break
            
        default:
            break
        }
    }
    
    @IBAction func onBack(_ sender: Any) {
        self.navigationController?.popViewController(animated: true)
    }
    
    @IBAction func onGenerate(_ sender: Any) {
        switch self.segmentedControl.selectedSegmentIndex {
        case 0:
            self.performSegue(withIdentifier: "id", sender: discount)
            break
            
        case 1:
            // Removed for multiple review.
//            if (self.hasLeftReview) {
//                UIManager.shared.showAlert(vc: self, title: "", message: "You have already left a review.")
//                break
//            }
            
            self.performSegue(withIdentifier: "review", sender: discount)
            break
            
        default:
            break
        }
    }
    
    @IBAction func onCall(_ sender: Any) {
        if let url = URL(string: "tel://\((discount?.vendorPhone)!)"), UIApplication.shared.canOpenURL(url) {
            UIApplication.shared.open(url)
        }
    }
    
    @IBAction func onFollow(_ sender: Any) {
        if let url = URL(string: (discount?.instagramId)!), UIApplication.shared.canOpenURL(url) {
            UIApplication.shared.open(url)
        }
    }
    
    @IBAction func onMail(_ sender: Any) {
        /*
        if MFMailComposeViewController.canSendMail() {
            let controller = MFMailComposeViewController()
            controller.mailComposeDelegate = self
            controller.setToRecipients([(self.discount?.vendorMail)!])
            present(controller, animated: true) {
            }
        } else {
            if let email = self.discount?.vendorMail {
                UIApplication.shared.open(URL(string: "mailto:\(email)")!)
            }
        }*/
        if let webLink: String = self.discount?.vendorLink {
            UIApplication.shared.open(URL(string: webLink)!)
        }
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if (segue.identifier == "id") {
            let discount = sender as! Discount
            if let vcID = segue.destination as? IDViewController {
                vcID.discount = discount
            }
        } else if (segue.identifier == "review") {
            let discount = sender as! Discount
            if let vcRate = segue.destination as? RateViewController {
                vcRate.delegate = self
                vcRate.discount = discount
                vcRate.review = nil
            }
        } else if (segue.identifier == "review_edit") {
            if let vcRate = segue.destination as? RateViewController {
                vcRate.delegate = self
                
                if let data = sender as? [String: Any] {
                    vcRate.discount = data["discount"] as? Discount
                    vcRate.review = data["review"] as! Review
                }
            }
        }
    }
    
    // MARK: - UITableViewDelegate, UITableViewDataSource
    func numberOfSections(in tableView: UITableView) -> Int {
        // #warning Incomplete implementation, return the number of sections
        var numOfSections: Int = 0
        
        if (self.detail == nil || self.detail?.reviews == nil || self.detail?.reviews.count == 0)
        {
            let noDataLabel: UILabel  = UILabel(frame: CGRect(x: 0, y: 0, width: tableView.bounds.size.width, height: tableView.bounds.size.height))
            noDataLabel.text          = "No reviews yet."
            noDataLabel.textColor     = UIColor.white
            noDataLabel.textAlignment = .center
            tableView.backgroundView  = noDataLabel
            tableView.separatorStyle  = .none
        }
        else
        {
            tableView.separatorStyle = .singleLine
            numOfSections            = 1
            tableView.backgroundView = nil
        }
        
        return numOfSections
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        // #warning Incomplete implementation, return the number of rows
        if (self.detail == nil || self.detail?.reviews == nil) {
            return 0
        }
        
        return (self.detail?.reviews.count)!
    }

    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "ReviewCell", for: indexPath) as! ReviewTableViewCell
        
        cell.ivUser.layer.cornerRadius = cell.ivUser.bounds.width / 2


        // Configure the cell...
        let review = self.detail?.reviews[indexPath.row]
        cell.configure(review!)

        return cell
    }
    
    func tableView(_ tableView: UITableView, canEditRowAt indexPath: IndexPath) -> Bool {
        let review = self.detail?.reviews[indexPath.row]
        
        if review?.putter == String(DataManager.currentUser!.id) {
            return true
        }
        
        return false
    }
    
    func tableView(_ tableView: UITableView, editActionsForRowAt indexPath: IndexPath) -> [UITableViewRowAction]? {
//        let delete = UITableViewRowAction(style: .destructive, title: "Delete") { (action, indexPath) in
//            // delete item at indexPath
//        }

        let edit = UITableViewRowAction(style: .destructive, title: "Edit") { (action, indexPath) in
            var sender:[String:Any] = [:]
            sender.updateValue(self.discount!, forKey: "discount")
            let review = self.detail?.reviews[indexPath.row]
            sender.updateValue(review!, forKey: "review")
            self.performSegue(withIdentifier: "review_edit", sender: sender)
        }

//        return [delete, edit]
        return [edit]
    }
}

extension DetailViewController: RateViewControllerDelegate {
    func updateReview() {
        initData()
    }
}

extension DetailViewController: MFMailComposeViewControllerDelegate {
    func mailComposeController(_ controller: MFMailComposeViewController, didFinishWith result: MFMailComposeResult, error: Error?) {
        controller.dismiss(animated: true) {
        }
    }
}
