//
//  RedditPostTableViewCell.swift
//  Koddit
//
//  Created by Andrew Steinmetz on 5/28/20.
//  Copyright © 2020 Plus Mobile Apps. All rights reserved.
//

import UIKit

class RedditPostTableViewCell: UITableViewCell {
    
    @IBOutlet weak var postTitle: UILabel!
    
    @IBOutlet weak var postImage: UIImageView!
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }

}
