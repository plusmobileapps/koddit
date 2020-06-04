//
//  RedditFeedTableViewController.swift
//  Koddit
//
//  Created by Andrew Steinmetz on 5/28/20.
//  Copyright © 2020 Plus Mobile Apps. All rights reserved.
//

import UIKit
import SharedCode

class RedditFeedTableViewController: UITableViewController {
    var posts = [Post]()

    override func viewDidLoad() {
        super.viewDidLoad()
        loadRedditPosts()
    }

    // MARK: - Table view data source

    override func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }

    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return posts.count
    }
    
    private func loadRedditPosts() {
        Di.init().getFeedRepository().getDankMemes(onSuccess: onDankMemesLoaded(response:), onError: onDankMemesError(error:))
    }
    
    func onDankMemesLoaded(response: [Post]) {
        print("on ios")
        print(response)
        posts += response
    }
    
    func onDankMemesError(error: Any) {
        print(error)
    }
    
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cellIdentifier = "RedditPostTableViewCell"
        guard let cell = tableView.dequeueReusableCell(withIdentifier: cellIdentifier, for: indexPath) as? RedditPostTableViewCell else {
            fatalError("Dequeued cell is not instance of ReeditPostTableViewCell")
        }

        let post = posts[indexPath.row]
        cell.postTitle.text = post.title
        cell.postShareButton.addTapGestureRecognizer {
            let string = "https://reddit.com" + post.permalink
            let url = URL(string: string)!
            let activityViewController =
                UIActivityViewController(activityItems: [string, url],
                                         applicationActivities: nil)

            self.present(activityViewController, animated: true) {
                print()
            }
        }
        let imageUrl = URL(string: post.url)!
        
        DispatchQueue.global(qos: .userInitiated).async {
            do{
                let imageData: Data = try Data(contentsOf: imageUrl)

                DispatchQueue.main.async {
                    let image = UIImage(data: imageData)
                    cell.postImage.image = image
//                    cell.postImage.sizeToFit()
//                    self.tableView.reloadData()
                }
            }catch{
                print("Unable to load data: \(error)")
            }
        }
         //Configure the cell...

        return cell
    }
    

    /*
    // Override to support conditional editing of the table view.
    override func tableView(_ tableView: UITableView, canEditRowAt indexPath: IndexPath) -> Bool {
        // Return false if you do not want the specified item to be editable.
        return true
    }
    */

    /*
    // Override to support editing the table view.
    override func tableView(_ tableView: UITableView, commit editingStyle: UITableViewCell.EditingStyle, forRowAt indexPath: IndexPath) {
        if editingStyle == .delete {
            // Delete the row from the data source
            tableView.deleteRows(at: [indexPath], with: .fade)
        } else if editingStyle == .insert {
            // Create a new instance of the appropriate class, insert it into the array, and add a new row to the table view
        }    
    }
    */

    /*
    // Override to support rearranging the table view.
    override func tableView(_ tableView: UITableView, moveRowAt fromIndexPath: IndexPath, to: IndexPath) {

    }
    */

    /*
    // Override to support conditional rearranging of the table view.
    override func tableView(_ tableView: UITableView, canMoveRowAt indexPath: IndexPath) -> Bool {
        // Return false if you do not want the item to be re-orderable.
        return true
    }
    */

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destination.
        // Pass the selected object to the new view controller.
    }
    */

}
