//
//  DiscountTableViewCell.swift
//  ThatDubaiGirl
//
//  Created by Bozo Krkeljas on 8.12.20..
//

import UIKit
import Cosmos

class DiscountTableViewCell: UITableViewCell {

    @IBOutlet weak var viewCard: UIView!
    
    @IBOutlet weak var ivImage: UIImageView!
    @IBOutlet weak var ivBanner: UIImageView!
    @IBOutlet weak var labelTitle: UILabel!
    @IBOutlet weak var cosmosView: CosmosView!
    @IBOutlet weak var labelDetail: UILabel!
    
    @IBOutlet weak var ivVendor: UIImageView!
    @IBOutlet weak var labelVendorName: UILabel!
    @IBOutlet weak var labelCategoryName: UILabel!
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }

    public func configure(_ discount:Discount) {
        self.labelTitle.text = discount.title
        self.labelDetail.text = discount.description
        self.ivImage.sd_setImage(with: URL(string: APIManager.urlImage + discount.photo!), completed: nil)
        
        self.ivVendor.sd_setImage(with: URL(string: APIManager.urlImage + discount.vendorPhoto!), completed: nil)
        
        self.labelVendorName.text = discount.vendorName
        self.labelCategoryName.text = discount.location

        self.ivBanner.isHidden = !(Int((discount.status)!) == 2)
        
        if let avgMarks = Double(discount.avgMarks ?? "") {
            self.cosmosView.isHidden = false
            self.cosmosView.rating = avgMarks
        } else {
            self.cosmosView.isHidden = true
        }
    }
}
