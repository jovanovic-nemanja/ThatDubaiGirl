package com.thatdubaigirl.com.Model;

import java.io.Serializable;
import java.util.ArrayList;

public class Categori_Model implements Serializable {
    String status;
    String id,putter, category_name, parent, category_photo, slug, sign_date, updated_at, created_at, path;
    ArrayList<Categori_Model> data;
    ArrayList<Categori_Model> reviews;

    public ArrayList<Categori_Model> getReviews() {
        return reviews;
    }

    public String getPutter() {
        return putter;
    }

    public void setPutter(String putter) {
        this.putter = putter;
    }

    public void setReviews(ArrayList<Categori_Model> reviews) {
        this.reviews = reviews;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    String comments, mark;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getDiscount_photo() {
        return discount_photo;
    }

    public void setDiscount_photo(String discount_photo) {
        this.discount_photo = discount_photo;
    }

    public String getVendor_id() {
        return vendor_id;
    }

    public void setVendor_id(String vendor_id) {
        this.vendor_id = vendor_id;
    }

    public String getCoupon() {
        return coupon;
    }

    public void setCoupon(String coupon) {
        this.coupon = coupon;
    }

    public String getVendorname() {
        return vendorname;
    }

    public void setVendorname(String vendorname) {
        this.vendorname = vendorname;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getInstagram_id() {
        return instagram_id;
    }

    public void setInstagram_id(String instagram_id) {
        this.instagram_id = instagram_id;
    }

    public String getWebsite_link() {
        return website_link;
    }

    public void setWebsite_link(String website_link) {
        this.website_link = website_link;
    }

    public String getDiscounts_date() {
        return discounts_date;
    }

    public void setDiscounts_date(String discounts_date) {
        this.discounts_date = discounts_date;
    }

    public String getAvg_marks() {
        return avg_marks;
    }

    public void setAvg_marks(String avg_marks) {
        this.avg_marks = avg_marks;
    }

    public String getCount_reviews() {
        return count_reviews;
    }

    public void setCount_reviews(String count_reviews) {
        this.count_reviews = count_reviews;
    }

    String title, description, category_id, discount_photo, vendor_id, coupon, vendorname, photo, location, email, phone, instagram_id, website_link, discounts_date, avg_marks, count_reviews;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getCategory_photo() {
        return category_photo;
    }

    public void setCategory_photo(String category_photo) {
        this.category_photo = category_photo;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getSign_date() {
        return sign_date;
    }

    public void setSign_date(String sign_date) {
        this.sign_date = sign_date;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public ArrayList<Categori_Model> getData() {
        return data;
    }

    public void setData(ArrayList<Categori_Model> data) {
        this.data = data;
    }


}
