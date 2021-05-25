//
//  ReviewTableViewCell.swift
//  ThatDubaiGirl
//
//  Created by Bozo Krkeljas on 1/4/21.
//

import UIKit
import Cosmos

class ReviewTableViewCell: UITableViewCell {

    @IBOutlet weak var ivUser: UIImageView!
    @IBOutlet weak var ratingView: CosmosView!
    @IBOutlet weak var labelComments: UILabel!
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }
    
    public func configure(_ review:Review) {
        if let photo = review.photo {
            self.ivUser.sd_setImage(with: URL(string: APIManager.urlImage + photo), completed: nil)
        }
        
        self.labelComments.text = review.comments

        if let marks = Double(review.marks ?? "") {
            self.ratingView.rating = marks
        }
    }
}
