//
//  CategoryTableViewCell.swift
//  ThatDubaiGirl
//
//  Created by Bozo Krkeljas on 8.12.20..
//

import UIKit

class CategoryTableViewCell: UITableViewCell {

    @IBOutlet weak var ivImage: UIImageView!
    @IBOutlet weak var labelName: UILabel!
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }

}
