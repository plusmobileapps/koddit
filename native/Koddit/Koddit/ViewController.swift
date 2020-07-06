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
    var subscription: (() -> KotlinUnit)?

    @IBAction func buttonClicked(_ sender: Any) {
        AppStateKt.getStore().dispatch(IncrementCount())
    }
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view.
        let label = UILabel(frame: CGRect(x: 0, y: 0, width: 300, height: 21))
        label.center = CGPoint(x: 160, y: 285)
        label.textAlignment = .center
        label.font = label.font.withSize(25)
        label.text = CommonKt.createApplicationScreenMessage()
        subscription = AppStateKt.getStore().subscribe({ () -> KotlinUnit in
            let state = AppStateKt.getStore().state as? AppState
            label.text = state?.count.description
            return KotlinUnit.init()
        })
//        Di.init().getFeedRepository().getDankMemes(onSuccess: onDankMemesLoaded(response:), onError: onDankMemesError(error:))
        view.addSubview(label)
    }
    
    override func viewDidDisappear(_ animated: Bool) {
        super.viewDidDisappear(animated)
        subscription?()
    }

    func onDankMemesLoaded(response: [Post]) {
        print("on ios")
        print(response)
    }
    
    func onDankMemesError(error: Any) {
        print(error)
    }

}

