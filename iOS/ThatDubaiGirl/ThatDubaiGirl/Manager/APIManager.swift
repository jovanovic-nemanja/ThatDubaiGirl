//
//  APIManager.swift
//  ThatDubaiGirl
//
//  Created by Bozo Krkeljas on 7.12.20..
//

import UIKit
import Alamofire
import ObjectMapper

enum EndPoint: String {
    case login = "loginUser"
    case loginApple = "loginwithApple"
    case register = "register"
    case emailVerify = "emailverify"
    case validateCode = "validateCode"
    case vendors = "vendros"
    case categories = "categories"
    case discounts = "getdiscountlistsbyvendor"
    case getvideolink = "getvideolink"
    case getDiscountlists = "getDiscountlists"
    case getDiscountDetail = "getdetaildiscountbyid"
    case putReview = "putReviewsbyAPI"
    case resetPassword = "forgotpassword"
    case updateAccount = "updateAccount"
    case updateAppleAccount = "updateAppleAccount"
}

class APIManager {
    static let shared = APIManager()
    
    let urlMain = "https://tdguae.com/api/v1/"
    public static let urlImage = "https://tdguae.com/uploads/"
    
    func login(_ email: String, password: String, _ callback: @escaping (Bool, User?, String?) -> Void) {
        let urlString = urlMain + EndPoint.login.rawValue
        let params = [
            "email": email,
            "password": password
        ]
                        
        AF.request(urlString, method: .post, parameters: params, encoding: JSONEncoding.default, headers: nil).validate(statusCode: 200 ..< 299).responseJSON { AFdata in
            do {
                if AFdata.data == nil {
                    callback(false, nil, "Error: Unable to connect to server")
                    return
                }
                
                guard let jsonObject = try JSONSerialization.jsonObject(with: AFdata.data!) as? [String: Any] else {
                    callback(false, nil, "Error: Cannot convert data to JSON object")
                    return
                }
                
                guard let prettyJsonData = try? JSONSerialization.data(withJSONObject: jsonObject, options: .prettyPrinted) else {
                    callback(false, nil, "Error: Cannot convert JSON object to Pretty JSON data")
                    return
                }
                
                guard let prettyPrintedJson = String(data: prettyJsonData, encoding: .utf8), !prettyPrintedJson.isEmpty else {
                    callback(false, nil, "Error: Could print JSON in String")
                    return
                }
                
                if let status = jsonObject["status"] as? String {
                    let msg = jsonObject["msg"] as? String
                    if status == "failed" {
                        callback(false, nil, msg)
                    } else {
                        if let data = jsonObject["data"] as? [String: Any] {
                            print(data)
                            guard let user = Mapper<User>().map(JSON: data) else {
                                callback(false, nil, "No objects found")
                                return
                            }
                            callback(true, user, msg)
                        } else {
                            callback(false, nil, msg)
                        }
                    }
                }
            } catch {
                callback(false, nil, "Error: Trying to convert JSON data to string")
                return
            }
        }
    }
    
    func loginApple(appleID: String, email: String?, userName: String?, _ callback: @escaping (Bool, User?, Bool, String?) -> Void) {
        let urlString = urlMain + EndPoint.loginApple.rawValue
        var params = [
            "apple_id": appleID,
        ]
        
        if let email = email {
            params["user_mail"] = email
        }
        
        if let userName = userName {
            params["user_name"] = userName
        }
                        
        AF.request(urlString, method: .post, parameters: params, encoding: JSONEncoding.default, headers: nil).validate(statusCode: 200 ..< 299).responseJSON { AFdata in
            do {
                if AFdata.data == nil {
                    callback(false, nil, false, "Error: Unable to connect to server")
                    return
                }
                
                guard let jsonObject = try JSONSerialization.jsonObject(with: AFdata.data!) as? [String: Any] else {
                    callback(false, nil, false, "Error: Cannot convert data to JSON object")
                    return
                }
                
                guard let prettyJsonData = try? JSONSerialization.data(withJSONObject: jsonObject, options: .prettyPrinted) else {
                    callback(false, nil, false, "Error: Cannot convert JSON object to Pretty JSON data")
                    return
                }
                
                guard let prettyPrintedJson = String(data: prettyJsonData, encoding: .utf8), !prettyPrintedJson.isEmpty else {
                    callback(false, nil, false, "Error: Could print JSON in String")
                    return
                }
                
                if let status = jsonObject["status"] as? String {
                    let msg = jsonObject["msg"] as? String
                    if status == "failed" {
                        callback(false, nil, false, msg)
                    } else {
                        if let data = jsonObject["data"] as? [String: Any], let newUser = jsonObject["isnewUser"] as? Int {
                            print(data)
                            print(newUser)
                            guard let user = Mapper<User>().map(JSON: data) else {
                                callback(false, nil, false, "No objects found")
                                return
                            }
                            callback(true, user, newUser.boolValue, msg)
                        } else {
                            callback(false, nil, false, msg)
                        }
                    }
                }
            } catch {
                callback(false, nil, false, "Error: Trying to convert JSON data to string")
                return
            }
        }
    }
    
    func verify(_ email: String, _ callback: @escaping (Bool, String?) -> Void) {
        let urlString = urlMain + EndPoint.emailVerify.rawValue
        let params = [
            "email": email
        ]

        AF.request(urlString, method: .post, parameters: params, encoding: JSONEncoding.default, headers: nil).validate(statusCode: 200 ..< 299).responseJSON { AFdata in
            do {
                if AFdata.data == nil {
                    callback(false, "Error: Unable to connect to server")
                    return
                }
                
                guard let jsonObject = try JSONSerialization.jsonObject(with: AFdata.data!) as? [String: Any] else {
                    callback(false, "Error: Cannot convert data to JSON object")
                    return
                }
                
                if let status = jsonObject["status"] as? String {
                    let msg = jsonObject["msg"] as? String
                    if status == "failed" {
                        callback(false, msg)
                    } else {
                        callback(true, msg)
                    }
                }
                
            } catch {
                callback(false, "Error: Trying to convert JSON data to string")
                return
            }
        }
    }
    
    func validate(_ email: String, code: Int, _ callback: @escaping (Bool, String?) -> Void) {
        let urlString = urlMain + EndPoint.validateCode.rawValue
        let params = [
            "email": email,
            "code": code
        ] as [String : Any]
                
        AF.request(urlString, method: .post, parameters: params, encoding: JSONEncoding.default, headers: nil).validate(statusCode: 200 ..< 299).responseJSON { AFdata in
            do {
                if AFdata.data == nil {
                    callback(false, "Error: Unable to connect to server")
                    return
                }
                
                guard let jsonObject = try JSONSerialization.jsonObject(with: AFdata.data!) as? [String: Any] else {
                    callback(false, "Error: Cannot convert data to JSON object")
                    return
                }
                
                if let status = jsonObject["status"] as? String {
                    let msg = jsonObject["msg"] as? String
                    if status == "failed" {
                        callback(false, msg)
                    } else {
                        callback(true, msg)
                    }
                }
                
            } catch {
                callback(false, "Error: Trying to convert JSON data to string")
                return
            }
        }
    }
    
    func signup(_ email: String, username: String, birthday: String, address: String, password: String, password_confirmation: String,image: UIImage, _ callback: @escaping (Bool, User?, String?) -> Void) {
        let urlString = urlMain + EndPoint.register.rawValue
        let params:[String:String] = [
            "email": email,
            "username": username,
            "birthday": birthday,
            "address": address,
            "password": password,
            "password_confirmation": password_confirmation
        ]
                
        let url = URL(string: urlString)
        let imageData = image.jpegData(compressionQuality: 0.8)
        
        AF.upload(multipartFormData: { multipartFormData in
            for (key, value) in params {
                multipartFormData.append(value.data(using: .utf8)!, withName: key)
            }
            
            if let data = imageData {
                multipartFormData.append(data, withName: "photo", fileName: "\(Date.init().timeIntervalSince1970).png", mimeType: "image/png")
            }
        },
        to: url!, method: .post , headers: nil)
        .responseJSON(completionHandler: { (response) in
            
            print("response is",response)
            
            if let err = response.error{
                print(err)
                callback(false, nil, "failed")
                return
            }
            
            print("Succesfully uploaded")
            
            if let jsonObject = response.value as? [String:Any]{
                if let status = jsonObject["status"] as? String {
                    let msg = jsonObject["msg"] as? String
                    if status == "failed" {
                        callback(false, nil, msg)
                    } else {
                        if let data = jsonObject["data"] as? [String: Any] {
                            guard let user = Mapper<User>().map(JSON: data) else {
                                callback(false, nil, "No objects found")
                                return
                            }
                            callback(true, user, msg)
                        } else {
                            callback(false, nil, msg)
                        }
                    }
                }
            }
        })
    }
    
    func getVideo(_ callback: @escaping (Bool, String?) -> Void) {
        let urlString = urlMain + EndPoint.getvideolink.rawValue
                        
        AF.request(urlString, method: .get, parameters: nil, headers: nil).validate(statusCode: 200 ..< 299).responseJSON { AFdata in
            do {
                if AFdata.data == nil {
                    callback(false, "Error: Unable to connect to server")
                    return
                }
                
                guard let jsonObject = try JSONSerialization.jsonObject(with: AFdata.data!) as? [String: Any] else {
                    print("Error: Cannot convert data to JSON object")
                    return
                }
                
                if let status = jsonObject["status"] as? String {
                    let datas = jsonObject["data"] as? String
                    if status == "failed" {
                        callback(false , datas)
                    } else {
                        if let data = jsonObject["data"] as? String {
                           // let objects = Mapper<video>()
                            callback(true , data)
                        } else {
                            callback(false , datas)
                        }
                    }
                }
            } catch {
                print("Error: Trying to convert JSON data to string")
                return
            }
        }
    }
    
    func getVendors(_ categoryId: Int, _ callback: @escaping (Bool, [Vendor]?, String?) -> Void) {
        let urlString = urlMain + EndPoint.vendors.rawValue
        let params = [
            "category_id": categoryId
        ]
                
        AF.request(urlString, method: .get, parameters: params, headers: nil).validate(statusCode: 200 ..< 299).responseJSON { AFdata in
            do {
                if AFdata.data == nil {
                    callback(false, nil, "Error: Unable to connect to server")
                    return
                }
                
                guard let jsonObject = try JSONSerialization.jsonObject(with: AFdata.data!) as? [String: Any] else {
                    callback(false, nil, "Error: Cannot convert data to JSON object")
                    return
                }
                
                if let status = jsonObject["status"] as? String {
                    let msg = jsonObject["msg"] as? String
                    if status == "failed" {
                        callback(false, nil, msg)
                    } else {
                        if let data = jsonObject["data"] as? [[String: Any]] {
                            let objects = Mapper<Vendor>().mapArray(JSONArray: data)
                            callback(true, objects, msg)
                        } else {
                            callback(false, nil, msg)
                        }
                        
                    }
                }
            } catch {
                callback(false, nil, "Error: Trying to convert JSON data to string")
                return
            }
        }
    }
    
    func getCategories(_ callback: @escaping (Bool, [Category]?, String?) -> Void) {
        let urlString = urlMain + EndPoint.categories.rawValue
                            
        AF.request(urlString, method: .get, parameters: nil, headers: nil).validate(statusCode: 200 ..< 299).responseJSON { AFdata in
            do {
                if AFdata.data == nil {
                    callback(false, nil, "Error: Unable to connect to server")
                    return
                }
                
                guard let jsonObject = try JSONSerialization.jsonObject(with: AFdata.data!) as? [String: Any] else {
                    callback(false, nil, "Error: Cannot convert data to JSON object")
                    return
                }
                
                if let status = jsonObject["status"] as? String {
                    let msg = jsonObject["msg"] as? String
                    if status == "failed" {
                        callback(false, nil, msg)
                    } else {
                        if let data = jsonObject["data"] as? [[String: Any]] {
                            let objects = Mapper<Category>().mapArray(JSONArray: data)
                            callback(true, objects, msg)
                        } else {
                            callback(false, nil, msg)
                        }
                    }
                }
            } catch {
                callback(false, nil, "Error: Trying to convert JSON data to string")
                return
            }
        }
    }
    
    func getDiscounts(_ strToSrearch: String,_ categoryID: Int, _ callback: @escaping (Bool, [Discount]?, String?) -> Void) {
        var params = [String:Any]()
        params["vendor_name"] = strToSrearch
        
        let urlString = urlMain + EndPoint.getDiscountlists.rawValue

        if (categoryID > 0) {
            params["category_id"] = categoryID
        }
        
        AF.request(urlString, method: .get, parameters: params, headers: nil).validate(statusCode: 200 ..< 299).responseJSON { AFdata in
            do {
                if AFdata.data == nil {
                    callback(false, nil, "Error: Unable to connect to server")
                    return
                }
                
                guard let jsonObject = try JSONSerialization.jsonObject(with: AFdata.data!) as? [String: Any] else {
                    callback(false, nil, "Error: Cannot convert data to JSON object")
                    return
                }

                if let status = jsonObject["status"] as? String {
                    let msg = jsonObject["msg"] as? String
                    if status == "failed" {
                        callback(false, nil, msg)
                    } else {
                        if let data = jsonObject["data"] as? [[String: Any]] {
                            let objects = Mapper<Discount>().mapArray(JSONArray: data)
                            callback(true, objects, msg)
                        } else {
                            callback(false, nil, msg)
                        }
                    }
                }
            } catch {
                callback(false, nil, "Error: Trying to convert JSON data to string")
                return
            }
        }
    }
    
    func getDiscountDetail(_ discountID: Int, _ callback: @escaping (Bool, [DiscountDetail]?, String?) -> Void) {
        var params = [String:Any]()
        params["id"] = discountID

        let urlString = urlMain + EndPoint.getDiscountDetail.rawValue
        
        AF.request(urlString, method: .get, parameters: params, headers: nil).validate(statusCode: 200 ..< 299).responseJSON { AFdata in
            do {
                if AFdata.data == nil {
                    callback(false, nil, "Error: Unable to connect to server")
                    return
                }
                
                guard let jsonObject = try JSONSerialization.jsonObject(with: AFdata.data!) as? [String: Any] else {
                    callback(false, nil, "Error: Cannot convert data to JSON object")
                    return
                }

                if let status = jsonObject["status"] as? String {
                    let msg = jsonObject["msg"] as? String
                    if status == "failed" {
                        callback(false, nil, msg)
                    } else {
                        if let data = jsonObject["data"] as? [[String: Any]] {
                            let object = Mapper<DiscountDetail>().mapArray(JSONArray: data)
                            callback(true, object, msg)
                        } else {
                            callback(false, nil, msg)
                        }
                    }
                }
            } catch {
                callback(false, nil, "Error: Trying to convert JSON data to string")
                return
            }
        }
    }
    
    func putReview(_ putterId: String, _ discountId: String, _ marks: Int, _ comments: String, _ reviewID: String?, _ callback: @escaping (Bool, String?) -> Void) {
        let urlString = urlMain + EndPoint.putReview.rawValue
        let params = [
            "putter": putterId,
            "discount_id": discountId,
            "mark": String(marks),
            "comments": comments,
            "review_id": reviewID
        ]

        AF.request(urlString, method: .post, parameters: params, encoding: JSONEncoding.default, headers: nil).validate(statusCode: 200 ..< 299).responseJSON { AFdata in
            do {
                if AFdata.data == nil {
                    callback(false, "Error: Unable to connect to server")
                    return
                }
                
                guard let jsonObject = try JSONSerialization.jsonObject(with: AFdata.data!) as? [String: Any] else {
                    callback(false, "Error: Cannot convert data to JSON object")
                    return
                }
                
                if let status = jsonObject["status"] as? String {
                    let msg = jsonObject["msg"] as? String
                    if status == "failed" {
                        callback(false, msg)
                    } else {
                        callback(true, msg)
                    }
                }
                
            } catch {
                callback(false, "Error: Trying to convert JSON data to string")
                return
            }
        }
    }
    
    func resetPassword(_ email: String, _ callback: @escaping (Bool, String?) -> Void) {
        let urlString = urlMain + EndPoint.resetPassword.rawValue
        let params = [
            "email": email,
        ]
                        
        AF.request(urlString, method: .post, parameters: params, encoding: JSONEncoding.default, headers: nil).validate(statusCode: 200 ..< 299).responseJSON { AFdata in
            do {
                if AFdata.data == nil {
                    callback(false, "Error: Unable to connect to server")
                    return
                }
                
                guard let jsonObject = try JSONSerialization.jsonObject(with: AFdata.data!) as? [String: Any] else {
                    callback(false, "Error: Cannot convert data to JSON object")
                    return
                }
                
                guard let prettyJsonData = try? JSONSerialization.data(withJSONObject: jsonObject, options: .prettyPrinted) else {
                    callback(false, "Error: Cannot convert JSON object to Pretty JSON data")
                    return
                }
                
                guard let prettyPrintedJson = String(data: prettyJsonData, encoding: .utf8), !prettyPrintedJson.isEmpty else {
                    callback(false, "Error: Could print JSON in String")
                    return
                }
                
                if let status = jsonObject["status"] as? String {
                    let msg = jsonObject["msg"] as? String
                    if status == "failed" {
                        callback(false, msg)
                    } else {
                        callback(true, msg)
                    }
                }
            } catch {
                callback(false, "Error: Trying to convert JSON data to string")
                return
            }
        }
    }
    
    func updateAccount(_ email: String, username: String?, birthday: String?, address: String?, image: UIImage?, _ callback: @escaping (Bool, User?, String?) -> Void) {
        let urlString = urlMain + EndPoint.updateAccount.rawValue
        
        var params = [String:String]()
        params["email"] = email

        if username != nil && username!.count > 0 {
            params["username"] = username
        }
        
        if birthday != nil && birthday!.count > 0 {
            params["birthday"] = birthday
        }
        
        if address != nil && address!.count > 0 {
            params["address"] = address
        }
        
        let url = URL(string: urlString)
        
        if image != nil {
            let imageData = image!.jpegData(compressionQuality: 0.8)
            
            AF.upload(multipartFormData: { multipartFormData in
                for (key, value) in params {
                    multipartFormData.append(value.data(using: .utf8)!, withName: key)
                }
                
                if let data = imageData {
                    multipartFormData.append(data, withName: "photo", fileName: "\(Date.init().timeIntervalSince1970).png", mimeType: "image/png")
                }
            },
            to: url!, method: .post , headers: nil)
            .responseJSON(completionHandler: { (response) in
                
                print("response is",response)
                
                if let err = response.error{
                    print(err)
                    callback(false, nil, "failed")
                    return
                }
                
                print("Succesfully uploaded")
                
                if let jsonObject = response.value as? [String:Any]{
                    if let status = jsonObject["status"] as? String {
                        let msg = jsonObject["msg"] as? String
                        if status == "failed" {
                            callback(false, nil, msg)
                        } else {
                            if let data = jsonObject["data"] as? [String: Any] {
                                guard let user = Mapper<User>().map(JSON: data) else {
                                    callback(false, nil, "No objects found")
                                    return
                                }
                                callback(true, user, msg)
                            } else {
                                callback(false, nil, msg)
                            }
                        }
                        
                    }
                }
            })
        } else {
            AF.request(urlString, method: .post, parameters: params, encoding: JSONEncoding.default, headers: nil).validate(statusCode: 200 ..< 299).responseJSON { AFdata in
                do {
                    if AFdata.data == nil {
                        callback(false, nil, "Error: Unable to connect to server")
                        return
                    }
                    
                    guard let jsonObject = try JSONSerialization.jsonObject(with: AFdata.data!) as? [String: Any] else {
                        callback(false, nil, "Error: Cannot convert data to JSON object")
                        return
                    }
                    
                    if let status = jsonObject["status"] as? String {
                        let msg = jsonObject["msg"] as? String
                        if status == "failed" {
                            callback(false, nil, msg)
                        } else {
                            if let data = jsonObject["data"] as? [String: Any] {
                                guard let user = Mapper<User>().map(JSON: data) else {
                                    callback(false, nil, "No objects found")
                                    return
                                }
                                callback(true, user, msg)
                            } else {
                                callback(false, nil, msg)
                            }
                        }
                    }
                } catch {
                    callback(false, nil, "Error: Trying to convert JSON data to string")
                    return
                }
            }
        }
    }

    func updateAppleAccount(_ appleID: String, username: String?, birthday: String?, address: String?, image: UIImage?, _ callback: @escaping (Bool, User?, String?) -> Void) {
        let urlString = urlMain + EndPoint.updateAppleAccount.rawValue
        
        var params = [String:String]()
        params["apple_id"] = appleID

        if username != nil && username!.count > 0 {
            params["username"] = username
        }
        
        if birthday != nil && birthday!.count > 0 {
            params["birthday"] = birthday
        }
        
        if address != nil && address!.count > 0 {
            params["address"] = address
        }
        
        let url = URL(string: urlString)
        
        if image != nil {
            let imageData = image!.jpegData(compressionQuality: 0.8)
            
            AF.upload(multipartFormData: { multipartFormData in
                for (key, value) in params {
                    multipartFormData.append(value.data(using: .utf8)!, withName: key)
                }
                
                if let data = imageData {
                    multipartFormData.append(data, withName: "photo", fileName: "\(Date.init().timeIntervalSince1970).png", mimeType: "image/png")
                }
            },
            to: url!, method: .post , headers: nil)
            .responseJSON(completionHandler: { (response) in
                
                print("response is",response)
                
                if let err = response.error{
                    print(err)
                    callback(false, nil, "failed")
                    return
                }
                
                print("Succesfully uploaded")
                
                if let jsonObject = response.value as? [String:Any]{
                    if let status = jsonObject["status"] as? String {
                        let msg = jsonObject["msg"] as? String
                        if status == "failed" {
                            callback(false, nil, msg)
                        } else {
                            if let data = jsonObject["data"] as? [String: Any] {
                                guard let user = Mapper<User>().map(JSON: data) else {
                                    callback(false, nil, "No objects found")
                                    return
                                }
                                callback(true, user, msg)
                            } else {
                                callback(false, nil, msg)
                            }
                        }
                        
                    }
                }
            })
        } else {
            AF.request(urlString, method: .post, parameters: params, encoding: JSONEncoding.default, headers: nil).validate(statusCode: 200 ..< 299).responseJSON { AFdata in
                do {
                    if AFdata.data == nil {
                        callback(false, nil, "Error: Unable to connect to server")
                        return
                    }
                    
                    guard let jsonObject = try JSONSerialization.jsonObject(with: AFdata.data!) as? [String: Any] else {
                        callback(false, nil, "Error: Cannot convert data to JSON object")
                        return
                    }
                    
                    if let status = jsonObject["status"] as? String {
                        let msg = jsonObject["msg"] as? String
                        if status == "failed" {
                            callback(false, nil, msg)
                        } else {
                            if let data = jsonObject["data"] as? [String: Any] {
                                guard let user = Mapper<User>().map(JSON: data) else {
                                    callback(false, nil, "No objects found")
                                    return
                                }
                                callback(true, user, msg)
                            } else {
                                callback(false, nil, msg)
                            }
                        }
                    }
                } catch {
                    callback(false, nil, "Error: Trying to convert JSON data to string")
                    return
                }
            }
        }
    }
}

extension Int {
    var boolValue: Bool { return self != 0 }
}
