//
//  ViewController.swift
//  Koddit
//
//  Created by Andrew Steinmetz on 5/25/20.
//  Copyright © 2020 Plus Mobile Apps. All rights reserved.
//

import UIKit
import SharedCode

class ViewController: UIViewController {

    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view.
        let label = UILabel(frame: CGRect(x: 0, y: 0, width: 300, height: 21))
        label.center = CGPoint(x: 160, y: 285)
        label.textAlignment = .center
        label.font = label.font.withSize(25)
        label.text = CommonKt.createApplicationScreenMessage()
        FeedRepository().getDankMemes()
        view.addSubview(label)
    }


}

