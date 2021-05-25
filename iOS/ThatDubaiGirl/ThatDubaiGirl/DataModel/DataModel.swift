//
//  DataModel.swift
//  ThatDubaiGirl
//
//  Created by Bozo Krkeljas on 7.12.20..
//

import Foundation
import ObjectMapper

struct User: Mappable {
    var id: Int = 0
    var uniqueId: String?
    var name: String?
    var email: String?
    var photo: String?
    var birthday: String?
    var address: String?
    var appleID: String?

    init?(map: Map) {
    }
    
    mutating func mapping(map: Map) {
        id <- map["id"]
        uniqueId <- map["userUniqueId"]
        name <- map["username"]
        email <- map["email"]
        photo <- map["photo"]
        birthday <- map["birthday"]
        address <- map["address"]
        appleID <- map["apple_id"]
    }
}

struct Intro: Mappable {
    var data: String?
    var status: String?
    
    init?(map: Map) {
    }
    
    mutating func mapping(map: Map) {
        data <- map["data"]
        status <- map["status"]
    }
}

struct Vendor: Mappable {
    var id: String?
    var name: String?
    
    init?(map: Map) {
    }
    
    mutating func mapping(map: Map) {
        id <- map["id"]
        name <- map["vendorname"]
    }
}

struct Category: Mappable {
    var id: Int = -1
    var name: String = ""
    var photo: String = ""
    
    init?(map: Map) {
    }
    
    mutating func mapping(map: Map) {
        id <- map["id"]
        name <- map["category_name"]
        photo <- map["category_photo"]
    }
}

struct Discount: Mappable {
    var id: String?
    var title: String?
    var photo: String?
    var description: String?
    var categoryId: String?
    var categoryName: String?
    var vendorName: String?
    var vendorMail: String?
    var vendorPhone: String?
    var vendorPhoto: String?
    var vendorLink: String?
    var location: String?
    var status: String?
    var instagramId: String?
    var avgMarks: String?
    var countReviews: String?
    var coupon: String?

    init?(map: Map) {
    }
    
    mutating func mapping(map: Map) {
        id <- map["id"]
        title <- map["title"]
        photo <- map["discount_photo"]
        description <- map["description"]
        categoryId <- map["category_id"]
        categoryName <- map["category_name"]
        vendorName <- map["vendorname"]
        vendorMail <- map["email"]
        vendorPhone <- map["phone"]
        vendorPhoto <- map["photo"]
        vendorLink <- map["website_link"]
        location <- map["location"]
        status <- map["status"]
        instagramId <- map["instagram_id"]
        avgMarks <- map["avg_marks"]
        countReviews <- map["count_reviews"]
        coupon <- map["coupon"]

        /*if description!.count > 0 {
            description = description!.replacingOccurrences(of: "\r", with: " ")
            description = description!.replacingOccurrences(of: "\n", with: " ")
        }*/
    }
}

struct Review: Mappable {
    var id: String?
    var putter: String?
    var name: String?
    var photo: String?
    var discountId: String?
    var marks: String?
    var comments: String?

    init?(map: Map) {
    }
    
    mutating func mapping(map: Map) {
        id <- map["id"]
        putter <- map["putter"]
        name <- map["username"]
        photo <- map["photo"]
        discountId <- map["discount_id"]
        marks <- map["mark"]
        comments <- map["comments"]
    }
}

struct DiscountDetail: Mappable {
    var reviews: [Review]

    init?(map: Map) {
        reviews = []
    }
    
    mutating func mapping(map: Map) {
        reviews <- map["reviews"]
    }
}
