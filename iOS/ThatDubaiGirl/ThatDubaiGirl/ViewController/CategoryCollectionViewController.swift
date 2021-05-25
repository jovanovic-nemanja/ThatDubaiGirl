//
//  CategoryCollectionViewController.swift
//  ThatDubaiGirl
//
//  Created by Bozo Krkeljas on 18.12.20..
//

import UIKit

private let reuseIdentifier = "CategoryCell"

class CategoryCollectionViewController: UICollectionViewController, UICollectionViewDelegateFlowLayout {

    var refreshControl: UIRefreshControl = {
        return UIRefreshControl()
    }()
    
    @objc func refresh(sender: AnyObject) {
        DataManager.loadDatas(self.view) {
            DispatchQueue.main.async {
                self.collectionView.reloadData()
                self.refreshControl.endRefreshing()
                self.view.setNeedsDisplay()
            }
        }
    }

    func setupPullToRefresh() {
        refreshControl.attributedTitle = NSAttributedString(string: "Pull to refresh", attributes: [.foregroundColor: UIColor.white])
        refreshControl.backgroundColor = .black
        refreshControl.tintColor = .white
        refreshControl.addTarget(self, action: #selector(refresh), for: .valueChanged)
        self.collectionView.addSubview(refreshControl)
    }

    override func viewDidLoad() {
        super.viewDidLoad()

        // Uncomment the following line to preserve selection between presentations
        // self.clearsSelectionOnViewWillAppear = false

        // Register cell classes
//        self.collectionView!.register(CategoryCollectionViewCell.self, forCellWithReuseIdentifier: reuseIdentifier)

        // Setup Pull to Refresh
        setupPullToRefresh()
        
        // Do any additional setup after loading the view.
        self.collectionView.reloadData()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)

//        self.tabBarController?.title = "Categories"
    }

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using [segue destinationViewController].
        // Pass the selected object to the new view controller.
    }
    */

    // MARK: UICollectionViewDataSource

    override func numberOfSections(in collectionView: UICollectionView) -> Int {
        // #warning Incomplete implementation, return the number of sections
        return 1
    }


    override func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        // #warning Incomplete implementation, return the number of items
        return DataManager.categories.count
    }

    override func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: reuseIdentifier, for: indexPath) as! CategoryCollectionViewCell
    
        // Configure the cell
        cell.contentView.layer.cornerRadius = 8
        cell.contentView.layer.borderWidth = 0.5
        cell.contentView.layer.borderColor = UIColor.green.cgColor
        
        let category = DataManager.categories[indexPath.row]
        cell.ivCategory.sd_setImage(with: URL(string: APIManager.urlImage + category.photo), completed: nil)
        cell.labelCategoryName.text = category.name
    
        return cell
    }

    // MARK: UICollectionViewDelegate

    /*
    // Uncomment this method to specify if the specified item should be highlighted during tracking
    override func collectionView(_ collectionView: UICollectionView, shouldHighlightItemAt indexPath: IndexPath) -> Bool {
        return true
    }
    */

    /*
    // Uncomment this method to specify if the specified item should be selected
    override func collectionView(_ collectionView: UICollectionView, shouldSelectItemAt indexPath: IndexPath) -> Bool {
        return true
    }
    */
    
    override func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        let category = DataManager.categories[indexPath.row]
        DataManager.categoryID = category.id
        self.performSegue(withIdentifier: "discount", sender: nil)
    }

    /*
    // Uncomment these methods to specify if an action menu should be displayed for the specified item, and react to actions performed on the item
    override func collectionView(_ collectionView: UICollectionView, shouldShowMenuForItemAt indexPath: IndexPath) -> Bool {
        return false
    }

    override func collectionView(_ collectionView: UICollectionView, canPerformAction action: Selector, forItemAt indexPath: IndexPath, withSender sender: Any?) -> Bool {
        return false
    }

    override func collectionView(_ collectionView: UICollectionView, performAction action: Selector, forItemAt indexPath: IndexPath, withSender sender: Any?) {
    
    }
    */

    // MARK: UICollectionViewDelegateFlowLayout
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, sizeForItemAt indexPath: IndexPath) -> CGSize {
        let width = (collectionView.frame.width - 32) / 2
        return CGSize(width: width, height: width)
    }
}
