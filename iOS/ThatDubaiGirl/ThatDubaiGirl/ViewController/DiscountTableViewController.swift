//
//  DiscountTableViewController.swift
//  ThatDubaiGirl
//
//  Created by Bozo Krkeljas on 8.12.20..
//

import UIKit

class DiscountTableViewController: UITableViewController, UISearchResultsUpdating {
    private var prevCategoryID: Int = 0
    private var discounts: [Discount] = []
    private var filteredDiscounts: [Discount] = []
    
    var searchController = UISearchController()
    
    func updateSearchResults(for searchController: UISearchController) {
        filteredDiscounts.removeAll()
        
        let key = searchController.searchBar.text
        if (key?.count == 0) {
            filteredDiscounts = discounts.filter({ (Discount) -> Bool in
                return true
            })
        } else {
            for discount in discounts {
                if discount.vendorName!.contains(key!) {
                    filteredDiscounts.append(discount)
                }
            }
        }
        
        self.tableView.reloadData()
    }
    
    func updateData() {
        let categoryID = DataManager.categoryID
        
        UIManager.shared.showHUD(view: self.view)
        APIManager.shared.getDiscounts("", categoryID) { (success, discounts, msg) in
            UIManager.shared.hideHUD()
            if success {
                self.discounts = discounts!
                self.tableView.reloadData()
            } else {
                self.discounts = []
                self.tableView.reloadData()
            }

            print(self.discounts)
        }

        tableView.reloadData()
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        searchController = ({
            let controller = UISearchController(searchResultsController: nil)
            controller.searchResultsUpdater = self
            controller.searchBar.sizeToFit()
            controller.searchBar.placeholder = "Search by Vendor Name"
            controller.obscuresBackgroundDuringPresentation = false;
            
            tableView.tableHeaderView = controller.searchBar
            return controller
        })()
        
        self.definesPresentationContext = true
        
        self.tableView.tableFooterView = UIView()
        
        updateData()

        // Uncomment the following line to preserve selection between presentations
        // self.clearsSelectionOnViewWillAppear = false

        // Uncomment the following line to display an Edit button in the navigation bar for this view controller.
        // self.navigationItem.rightBarButtonItem = self.editButtonItem
    }
    
    // MARK: - Table view data source

    override func numberOfSections(in tableView: UITableView) -> Int {
        // #warning Incomplete implementation, return the number of sections
        return 1
    }

    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        // #warning Incomplete implementation, return the number of rows
        if (searchController.isActive) {
            return filteredDiscounts.count
        }
        
        return self.discounts.count
    }
    
    override func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return tableView.frame.width / 2 + 138
    }

    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "DiscountCell", for: indexPath) as! DiscountTableViewCell
        
        cell.viewCard.layer.cornerRadius = 8
        
        
        cell.ivVendor.layer.cornerRadius = cell.ivVendor.bounds.width / 2


        // Configure the cell...
        if (searchController.isActive) {
            let discount = filteredDiscounts[indexPath.row]
            cell.labelTitle.text = discount.title
            cell.labelDetail.text = discount.description
            cell.ivImage.sd_setImage(with: URL(string: APIManager.urlImage + discount.photo!), completed: nil)
            
            cell.ivVendor.sd_setImage(with: URL(string: APIManager.urlImage + discount.vendorPhoto!), completed: nil)
            
            cell.labelVendorName.text = discount.vendorName
            cell.labelCategoryName.text = discount.categoryName
        } else {
            let discount = discounts[indexPath.row]
            cell.labelTitle.text = discount.title
            cell.labelDetail.text = discount.description
            cell.ivImage.sd_setImage(with: URL(string: APIManager.urlImage + discount.photo!), completed: nil)
            
            cell.ivVendor.sd_setImage(with: URL(string: APIManager.urlImage + discount.vendorPhoto!), completed: nil)
            
            cell.labelVendorName.text = discount.vendorName
            cell.labelCategoryName.text = discount.categoryName
        }

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
    
    override func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        let discount = searchController.isActive ? filteredDiscounts[indexPath.row] : discounts[indexPath.row]
        self.performSegue(withIdentifier: "detail", sender: discount)
    }

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destination.
        // Pass the selected object to the new view controller.
        if (segue.identifier == "detail") {
            let discount = sender as! Discount
            if let vcDetail = segue.destination as? DetailViewController {
                vcDetail.discount = discount
            }
        }
    }

    @IBAction func onLogout(_ sender: Any) {
        UserDefaults.standard.removeObject(forKey: "appleid")
        UserDefaults.standard.removeObject(forKey: "email")
        UserDefaults.standard.removeObject(forKey: "password")
        DataManager.currentUser = nil
        
        self.navigationController?.popToRootViewController(animated: true)
    }
}
