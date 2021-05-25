package com.thatdubaigirl.com.Utils;

import com.thatdubaigirl.com.Model.Categori_Model;
import com.thatdubaigirl.com.Model.Common_Model;
import com.thatdubaigirl.com.Model.Common_Model_A;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface Api {

    @POST("loginUser")
    Call<Common_Model> loginUser(@Query("email") String email, @Query("password") String password);

    @POST("emailverify")
    Call<Common_Model_A> emailverify(@Query("email") String email);

    @POST("validateCode")
    Call<Common_Model_A> validateCode(@Query("email") String email, @Query("code") String code);

//    @POST("register")
//    Call<Common_Model> register(@Query("username") String username, @Query("email") String email, @Query("password") String password,
//                                @Query("password_confirmation") String password_confirmation, @Query("birthday") String birthday,
//                                @Query("address") String address, @Query("photo") String photo);
//
    @POST("register")
    Call<Common_Model> register(@Body RequestBody file);

    @POST("forgotpassword")
    Call<Common_Model> forgotpassword(@Query("email") String email);


//    @Multipart
//    @POST("updateAccount")
//    Call<Common_Model> updateAccount(@Part("email") RequestBody email, @Part("birthday") RequestBody birthday, @Part("username") RequestBody username,
//                                     @Part("address") RequestBody address, @Part MultipartBody.Part image);

    @POST("updateAccount")
    Call<Common_Model> updateAccount(@Body RequestBody file);


    @GET("getvideolink")
    Call<Common_Model_A> getvideolink();

    @GET("categories")
    Call<Categori_Model> categories();

    @GET("getDiscountlists")
    Call<Categori_Model> getDiscountlists(@Query("category_id") String category_id, @Query("vendor_name") String vendor_name);

    @GET("getdetaildiscountbyid")
    Call<Categori_Model> getdetaildiscountbyid(@Query("id") String id);

    @POST("putReviewsbyAPI")
    Call<Common_Model> putReviewsbyAPI(@Query("putter") String putter, @Query("discount_id") String discount_id, @Query("mark") String mark, @Query("comments") String comments);

  @POST("loginwithGoogle")
    Call<Common_Model> loginwithGoogle(@Query("google_id") String google_id, @Query("user_name") String user_name, @Query("user_mail") String user_mail);
}
