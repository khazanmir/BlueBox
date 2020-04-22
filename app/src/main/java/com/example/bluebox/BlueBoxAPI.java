package com.example.bluebox;

import android.media.Image;
import android.media.ImageReader;
import android.net.Uri;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.sql.CommonDataSource;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

public interface BlueBoxAPI {

    @GET("get/user-profile")
    Call<UserProfile> getProfile(@Header("Authorization") String token);

//    @POST("auth/login")
//    Call<LoginResponse> postLogin(@Query("username") String user,
//                                  @Query("password") String pass);
//    @Multipart
//    @POST("auth/login")
//    Call<LoginResponse> postLogin(@PartMap Map<String, String> param);
//
//    @POST("auth/login")
//    Call<LoginResponse> postLogin(@Body Map<String, String> param);

    @FormUrlEncoded
    @POST("auth/login")
    Call<LoginResponse> postLogin(@FieldMap Map<String, String> param);
    @FormUrlEncoded
    @POST("auth/register")
    Call<LoginResponse> postRegiterMap(@FieldMap Map<String, String> param);

    @Multipart
    @POST("auth/register/image")
    Call<LoginResponse> postImage(@Part("username") RequestBody username, @Part MultipartBody.Part id_image);
//                                  @Part("id_image") RequestBody id_image);
//                                  @PartMap Map<String, String> param, @Part("id_image\"; filename=\"id_image\" ") RequestBody id_image);
//                                  @Part MultipartBody.Part  id_image);

//    @Multipart
//    @POST("auth/register")
//    Call<LoginResponse> postRegiterMap(@Part("dob") String dob,
//                                       @Part("address") String addr,
//                                       @Part("username") String user,
//                                       @Part("password") String pw,
//                                       @Part("phone") String phone,
//                                       @Part("alt_Phone") String alt,
//                                       @Part("id_type") String id,
//                                       @Part("legal_name") String idName,
//                                       @Part("id_number") String idNum,
//                                       @Part("id_address") String idAddr,
//                                       @Part("sex") String sex,
//                                       @Part("issue_date_id") String issue,
//                                       @Part("expiry_date_id") String expiry,
//                                       @Part("file\"; filename=\"id_image.png\" ") RequestBody id_image);

//    @Multipart
//    @POST("auth/register")
//    Call<LoginResponse> postRegiterMap(@PartMap Map<String, String> param, @PartMap Map<String, Date> paramd, @Part MultipartBody.Part id_image);
////                                       @FieldMap Map<String, Uri>params2);

    @FormUrlEncoded
    @POST("order/accept-single-item")
    Call<LoginResponse> postAccept(@Header("Authorization") String token, @FieldMap Map<String, Integer> param, @FieldMap Map<String, String> param2);

    @FormUrlEncoded
    @POST("order/delete-single-item")
    Call<LoginResponse> postRemove(@Header("Authorization") String token, @FieldMap Map<String, String> param2);

    @FormUrlEncoded
    @POST("querry-shipment-status")
    Call<LoginResponse> getTrackStatus(@Header("Authorization") String token, @FieldMap Map<String, String> param);


    @GET("order/all-orders")
    Call<GetAllOrders> getOrder(@Header("Authorization") String token);

    @FormUrlEncoded
    @POST("order/post-new")
    Call<LoginResponse> postLink(@Header("Authorization") String token,
                                 @FieldMap Map<String, String> params1,
                                 @FieldMap Map<String, String> params, @FieldMap Map<String, String> coupon);
    @FormUrlEncoded
    @POST("order/post-new")
    Call<LoginResponse> postLink(@Header("Authorization") String token,
                                 @FieldMap Map<String, String> params1,
                                 @FieldMap Map<String, String> params2,
                                 @FieldMap Map<String, String> params, @FieldMap Map<String, String> coupon);
    @FormUrlEncoded
    @POST("order/post-new")
    Call<LoginResponse> postLink(@Header("Authorization") String token,
                                 @FieldMap Map<String, String> params1,
                                 @FieldMap Map<String, String> params2,
                                 @FieldMap Map<String, String> params3,
                                 @FieldMap Map<String, String> params, @FieldMap Map<String, String> coupon);
    @FormUrlEncoded
    @POST("order/post-new")
    Call<LoginResponse> postLink(@Header("Authorization") String token,
                                 @FieldMap Map<String, String> params1,
                                 @FieldMap Map<String, String> params2,
                                 @FieldMap Map<String, String> params3,
                                 @FieldMap Map<String, String> params4,
                                 @FieldMap Map<String, String> params, @FieldMap Map<String, String> coupon);
    @FormUrlEncoded
    @POST("order/post-new")
    Call<LoginResponse> postLink(@Header("Authorization") String token,
                                 @FieldMap Map<String, String> params1,
                                 @FieldMap Map<String, String> params2,
                                 @FieldMap Map<String, String> params3,
                                 @FieldMap Map<String, String> params4,
                                 @FieldMap Map<String, String> params5,
                                 @FieldMap Map<String, String> params, @FieldMap Map<String, String> coupon);
    @FormUrlEncoded
    @POST("order/post-new")
    Call<LoginResponse> postLink(@Header("Authorization") String token,
                                 @FieldMap Map<String, String> params1,
                                 @FieldMap Map<String, String> params2,
                                 @FieldMap Map<String, String> params3,
                                 @FieldMap Map<String, String> params4,
                                 @FieldMap Map<String, String> params5,
                                 @FieldMap Map<String, String> params6,
                                 @FieldMap Map<String, String> params, @FieldMap Map<String, String> coupon);
    @FormUrlEncoded
    @POST("order/post-new")
    Call<LoginResponse> postLink(@Header("Authorization") String token,
                                 @FieldMap Map<String, String> params1,
                                 @FieldMap Map<String, String> params2,
                                 @FieldMap Map<String, String> params3,
                                 @FieldMap Map<String, String> params4,
                                 @FieldMap Map<String, String> params5,
                                 @FieldMap Map<String, String> params6,
                                 @FieldMap Map<String, String> params7,
                                 @FieldMap Map<String, String> params, @FieldMap Map<String, String> coupon);
    @FormUrlEncoded
    @POST("order/post-new")
    Call<LoginResponse> postLink(@Header("Authorization") String token,
                                 @FieldMap Map<String, String> params1,
                                 @FieldMap Map<String, String> params2,
                                 @FieldMap Map<String, String> params3,
                                 @FieldMap Map<String, String> params4,
                                 @FieldMap Map<String, String> params5,
                                 @FieldMap Map<String, String> params6,
                                 @FieldMap Map<String, String> params7,
                                 @FieldMap Map<String, String> params8,
                                 @FieldMap Map<String, String> params, @FieldMap Map<String, String> coupon);
    @FormUrlEncoded
    @POST("order/post-new")
    Call<LoginResponse> postLink(@Header("Authorization") String token,
                                 @FieldMap Map<String, String> params1,
                                 @FieldMap Map<String, String> params2,
                                 @FieldMap Map<String, String> params3,
                                 @FieldMap Map<String, String> params4,
                                 @FieldMap Map<String, String> params5,
                                 @FieldMap Map<String, String> params6,
                                 @FieldMap Map<String, String> params7,
                                 @FieldMap Map<String, String> params8,
                                 @FieldMap Map<String, String> params9,
                                 @FieldMap Map<String, String> params, @FieldMap Map<String, String> coupon);
    @FormUrlEncoded
    @POST("order/post-new")
    Call<LoginResponse> postLink(@Header("Authorization") String token,
                                 @FieldMap Map<String, String> params1,
                                 @FieldMap Map<String, String> params2,
                                 @FieldMap Map<String, String> params3,
                                 @FieldMap Map<String, String> params4,
                                 @FieldMap Map<String, String> params5,
                                 @FieldMap Map<String, String> params6,
                                 @FieldMap Map<String, String> params7,
                                 @FieldMap Map<String, String> params8,
                                 @FieldMap Map<String, String> params9,
                                 @FieldMap Map<String, String> params10,
                                 @FieldMap Map<String, String> params, @FieldMap Map<String, String> coupon);
    @FormUrlEncoded
    @POST("order/post-new")
    Call<LoginResponse> postLink(@Header("Authorization") String token,
                                 @FieldMap Map<String, String> params1,
                                 @FieldMap Map<String, String> params2,
                                 @FieldMap Map<String, String> params3,
                                 @FieldMap Map<String, String> params4,
                                 @FieldMap Map<String, String> params5,
                                 @FieldMap Map<String, String> params6,
                                 @FieldMap Map<String, String> params7,
                                 @FieldMap Map<String, String> params8,
                                 @FieldMap Map<String, String> params9,
                                 @FieldMap Map<String, String> params10,
                                 @FieldMap Map<String, String> params11,
                                 @FieldMap Map<String, String> params, @FieldMap Map<String, String> coupon);
    @FormUrlEncoded
    @POST("order/post-new")
    Call<LoginResponse> postLink(@Header("Authorization") String token,
                                 @FieldMap Map<String, String> params1,
                                 @FieldMap Map<String, String> params2,
                                 @FieldMap Map<String, String> params3,
                                 @FieldMap Map<String, String> params4,
                                 @FieldMap Map<String, String> params5,
                                 @FieldMap Map<String, String> params6,
                                 @FieldMap Map<String, String> params7,
                                 @FieldMap Map<String, String> params8,
                                 @FieldMap Map<String, String> params9,
                                 @FieldMap Map<String, String> params10,
                                 @FieldMap Map<String, String> params11,
                                 @FieldMap Map<String, String> params12,
                                 @FieldMap Map<String, String> params, @FieldMap Map<String, String> coupon);
    @FormUrlEncoded
    @POST("order/post-new")
    Call<LoginResponse> postLink(@Header("Authorization") String token,
                                 @FieldMap Map<String, String> params1,
                                 @FieldMap Map<String, String> params2,
                                 @FieldMap Map<String, String> params3,
                                 @FieldMap Map<String, String> params4,
                                 @FieldMap Map<String, String> params5,
                                 @FieldMap Map<String, String> params6,
                                 @FieldMap Map<String, String> params7,
                                 @FieldMap Map<String, String> params8,
                                 @FieldMap Map<String, String> params9,
                                 @FieldMap Map<String, String> params10,
                                 @FieldMap Map<String, String> params11,
                                 @FieldMap Map<String, String> params12,
                                 @FieldMap Map<String, String> params13,
                                 @FieldMap Map<String, String> params, @FieldMap Map<String, String> coupon);
    @FormUrlEncoded
    @POST("order/post-new")
    Call<LoginResponse> postLink(@Header("Authorization") String token,
                                 @FieldMap Map<String, String> params1,
                                 @FieldMap Map<String, String> params2,
                                 @FieldMap Map<String, String> params3,
                                 @FieldMap Map<String, String> params4,
                                 @FieldMap Map<String, String> params5,
                                 @FieldMap Map<String, String> params6,
                                 @FieldMap Map<String, String> params7,
                                 @FieldMap Map<String, String> params8,
                                 @FieldMap Map<String, String> params9,
                                 @FieldMap Map<String, String> params10,
                                 @FieldMap Map<String, String> params11,
                                 @FieldMap Map<String, String> params12,
                                 @FieldMap Map<String, String> params13,
                                 @FieldMap Map<String, String> params14,
                                 @FieldMap Map<String, String> params, @FieldMap Map<String, String> coupon);
    @FormUrlEncoded
    @POST("order/post-new")
    Call<LoginResponse> postLink(@Header("Authorization") String token,
                                 @FieldMap Map<String, String> params1,
                                 @FieldMap Map<String, String> params2,
                                 @FieldMap Map<String, String> params3,
                                 @FieldMap Map<String, String> params4,
                                 @FieldMap Map<String, String> params5,
                                 @FieldMap Map<String, String> params6,
                                 @FieldMap Map<String, String> params7,
                                 @FieldMap Map<String, String> params8,
                                 @FieldMap Map<String, String> params9,
                                 @FieldMap Map<String, String> params10,
                                 @FieldMap Map<String, String> params11,
                                 @FieldMap Map<String, String> params12,
                                 @FieldMap Map<String, String> params13,
                                 @FieldMap Map<String, String> params14,
                                 @FieldMap Map<String, String> params15,
                                 @FieldMap Map<String, String> params, @FieldMap Map<String, String> coupon);
    @FormUrlEncoded
    @POST("order/post-new")
    Call<LoginResponse> postLink(@Header("Authorization") String token,
                                 @FieldMap Map<String, String> params1,
                                 @FieldMap Map<String, String> params2,
                                 @FieldMap Map<String, String> params3,
                                 @FieldMap Map<String, String> params4,
                                 @FieldMap Map<String, String> params5,
                                 @FieldMap Map<String, String> params6,
                                 @FieldMap Map<String, String> params7,
                                 @FieldMap Map<String, String> params8,
                                 @FieldMap Map<String, String> params9,
                                 @FieldMap Map<String, String> params10,
                                 @FieldMap Map<String, String> params11,
                                 @FieldMap Map<String, String> params12,
                                 @FieldMap Map<String, String> params13,
                                 @FieldMap Map<String, String> params14,
                                 @FieldMap Map<String, String> params15,
                                 @FieldMap Map<String, String> params16,
                                 @FieldMap Map<String, String> params, @FieldMap Map<String, String> coupon);
    @FormUrlEncoded
    @POST("order/post-new")
    Call<LoginResponse> postLink(@Header("Authorization") String token,
                                 @FieldMap Map<String, String> params1,
                                 @FieldMap Map<String, String> params2,
                                 @FieldMap Map<String, String> params3,
                                 @FieldMap Map<String, String> params4,
                                 @FieldMap Map<String, String> params5,
                                 @FieldMap Map<String, String> params6,
                                 @FieldMap Map<String, String> params7,
                                 @FieldMap Map<String, String> params8,
                                 @FieldMap Map<String, String> params9,
                                 @FieldMap Map<String, String> params10,
                                 @FieldMap Map<String, String> params11,
                                 @FieldMap Map<String, String> params12,
                                 @FieldMap Map<String, String> params13,
                                 @FieldMap Map<String, String> params14,
                                 @FieldMap Map<String, String> params15,
                                 @FieldMap Map<String, String> params16,
                                 @FieldMap Map<String, String> params17,
                                 @FieldMap Map<String, String> params, @FieldMap Map<String, String> coupon);
    @FormUrlEncoded
    @POST("order/post-new")
    Call<LoginResponse> postLink(@Header("Authorization") String token,
                                 @FieldMap Map<String, String> params1,
                                 @FieldMap Map<String, String> params2,
                                 @FieldMap Map<String, String> params3,
                                 @FieldMap Map<String, String> params4,
                                 @FieldMap Map<String, String> params5,
                                 @FieldMap Map<String, String> params6,
                                 @FieldMap Map<String, String> params7,
                                 @FieldMap Map<String, String> params8,
                                 @FieldMap Map<String, String> params9,
                                 @FieldMap Map<String, String> params10,
                                 @FieldMap Map<String, String> params11,
                                 @FieldMap Map<String, String> params12,
                                 @FieldMap Map<String, String> params13,
                                 @FieldMap Map<String, String> params14,
                                 @FieldMap Map<String, String> params15,
                                 @FieldMap Map<String, String> params16,
                                 @FieldMap Map<String, String> params17,
                                 @FieldMap Map<String, String> params18,
                                 @FieldMap Map<String, String> params, @FieldMap Map<String, String> coupon);
    @FormUrlEncoded
    @POST("order/post-new")
    Call<LoginResponse> postLink(@Header("Authorization") String token,
                                 @FieldMap Map<String, String> params1,
                                 @FieldMap Map<String, String> params2,
                                 @FieldMap Map<String, String> params3,
                                 @FieldMap Map<String, String> params4,
                                 @FieldMap Map<String, String> params5,
                                 @FieldMap Map<String, String> params6,
                                 @FieldMap Map<String, String> params7,
                                 @FieldMap Map<String, String> params8,
                                 @FieldMap Map<String, String> params9,
                                 @FieldMap Map<String, String> params10,
                                 @FieldMap Map<String, String> params11,
                                 @FieldMap Map<String, String> params12,
                                 @FieldMap Map<String, String> params13,
                                 @FieldMap Map<String, String> params14,
                                 @FieldMap Map<String, String> params15,
                                 @FieldMap Map<String, String> params16,
                                 @FieldMap Map<String, String> params17,
                                 @FieldMap Map<String, String> params18,
                                 @FieldMap Map<String, String> params19,
                                 @FieldMap Map<String, String> params, @FieldMap Map<String, String> coupon);
    @FormUrlEncoded
    @POST("order/post-new")
    Call<LoginResponse> postLink(@Header("Authorization") String token,
                                 @FieldMap Map<String, String> params1,
                                 @FieldMap Map<String, String> params2,
                                 @FieldMap Map<String, String> params3,
                                 @FieldMap Map<String, String> params4,
                                 @FieldMap Map<String, String> params5,
                                 @FieldMap Map<String, String> params6,
                                 @FieldMap Map<String, String> params7,
                                 @FieldMap Map<String, String> params8,
                                 @FieldMap Map<String, String> params9,
                                 @FieldMap Map<String, String> params10,
                                 @FieldMap Map<String, String> params11,
                                 @FieldMap Map<String, String> params12,
                                 @FieldMap Map<String, String> params13,
                                 @FieldMap Map<String, String> params14,
                                 @FieldMap Map<String, String> params15,
                                 @FieldMap Map<String, String> params16,
                                 @FieldMap Map<String, String> params17,
                                 @FieldMap Map<String, String> params18,
                                 @FieldMap Map<String, String> params19,
                                 @FieldMap Map<String, String> params20,
                                 @FieldMap Map<String, String> params, @FieldMap Map<String, String> coupon);



//    @FormUrlEncoded
//    @POST("order/")
//    Call<LoginResponse> postShipLink(@Header("Authorization") String token,
//                                     @FieldMap Map<String, String> params1,
//                                     @FieldMap Map<String, String> params1o,
//                                     @FieldMap Map<String, String> params2,
//                                     @FieldMap Map<String, String> params2o,
//                                     @FieldMap Map<String, String> params);
//
//    @FormUrlEncoded
//    @POST("order/")
//    Call<LoginResponse> postShipLink(@Header("Authorization") String token,
//                                     @FieldMap Map<String, String> params1,
//                                     @FieldMap Map<String, String> params1o,
//                                     @FieldMap Map<String, String> params2,
//                                     @FieldMap Map<String, String> params2o,
//                                     @FieldMap Map<String, String> params3,
//                                     @FieldMap Map<String, String> params3o,
//                                     @FieldMap Map<String, String> params);
//
//    @FormUrlEncoded
//    @POST("order/")
//    Call<LoginResponse> postShipLink(@Header("Authorization") String token,
//                                     @FieldMap Map<String, String> params1,
//                                     @FieldMap Map<String, String> params1o,
//                                     @FieldMap Map<String, String> params2,
//                                     @FieldMap Map<String, String> params2o,
//                                     @FieldMap Map<String, String> params3,
//                                     @FieldMap Map<String, String> params3o,
//                                     @FieldMap Map<String, String> params4,
//                                     @FieldMap Map<String, String> params4o,
//                                     @FieldMap Map<String, String> params);
//
//    @FormUrlEncoded
//    @POST("order/")
//    Call<LoginResponse> postShipLink(@Header("Authorization") String token,
//                                     @FieldMap Map<String, String> params1,
//                                     @FieldMap Map<String, String> params1o,
//                                     @FieldMap Map<String, String> params2,
//                                     @FieldMap Map<String, String> params2o,
//                                     @FieldMap Map<String, String> params3,
//                                     @FieldMap Map<String, String> params3o,
//                                     @FieldMap Map<String, String> params4,
//                                     @FieldMap Map<String, String> params4o,
//                                     @FieldMap Map<String, String> params5,
//                                     @FieldMap Map<String, String> params5o,
//                                     @FieldMap Map<String, String> params);
//
//    @FormUrlEncoded
//    @POST("order/")
//    Call<LoginResponse> postShipLink(@Header("Authorization") String token,
//                                     @FieldMap Map<String, String> params1,
//                                     @FieldMap Map<String, String> params1o,
//                                     @FieldMap Map<String, String> params2,
//                                     @FieldMap Map<String, String> params2o,
//                                     @FieldMap Map<String, String> params3,
//                                     @FieldMap Map<String, String> params3o,
//                                     @FieldMap Map<String, String> params4,
//                                     @FieldMap Map<String, String> params4o,
//                                     @FieldMap Map<String, String> params5,
//                                     @FieldMap Map<String, String> params5o,
//                                     @FieldMap Map<String, String> params6,
//                                     @FieldMap Map<String, String> params6o,
//                                     @FieldMap Map<String, String> params);
//
//    @FormUrlEncoded
//    @POST("order/")
//    Call<LoginResponse> postShipLink(@Header("Authorization") String token,
//                                     @FieldMap Map<String, String> params1,
//                                     @FieldMap Map<String, String> params1o,
//                                     @FieldMap Map<String, String> params2,
//                                     @FieldMap Map<String, String> params2o,
//                                     @FieldMap Map<String, String> params3,
//                                     @FieldMap Map<String, String> params3o,
//                                     @FieldMap Map<String, String> params4,
//                                     @FieldMap Map<String, String> params4o,
//                                     @FieldMap Map<String, String> params5,
//                                     @FieldMap Map<String, String> params5o,
//                                     @FieldMap Map<String, String> params6,
//                                     @FieldMap Map<String, String> params6o,
//                                     @FieldMap Map<String, String> params7,
//                                     @FieldMap Map<String, String> params7o,
//                                     @FieldMap Map<String, String> params);
//
//    @FormUrlEncoded
//    @POST("order/")
//    Call<LoginResponse> postShipLink(@Header("Authorization") String token,
//                                     @FieldMap Map<String, String> params1,
//                                     @FieldMap Map<String, String> params1o,
//                                     @FieldMap Map<String, String> params2,
//                                     @FieldMap Map<String, String> params2o,
//                                     @FieldMap Map<String, String> params3,
//                                     @FieldMap Map<String, String> params3o,
//                                     @FieldMap Map<String, String> params4,
//                                     @FieldMap Map<String, String> params4o,
//                                     @FieldMap Map<String, String> params5,
//                                     @FieldMap Map<String, String> params5o,
//                                     @FieldMap Map<String, String> params6,
//                                     @FieldMap Map<String, String> params6o,
//                                     @FieldMap Map<String, String> params7,
//                                     @FieldMap Map<String, String> params7o,
//                                     @FieldMap Map<String, String> params8,
//                                     @FieldMap Map<String, String> params8o,
//                                     @FieldMap Map<String, String> params);
//
//    @FormUrlEncoded
//    @POST("order/")
//    Call<LoginResponse> postShipLink(@Header("Authorization") String token,
//                                     @FieldMap Map<String, String> params1,
//                                     @FieldMap Map<String, String> params1o,
//                                     @FieldMap Map<String, String> params2,
//                                     @FieldMap Map<String, String> params2o,
//                                     @FieldMap Map<String, String> params3,
//                                     @FieldMap Map<String, String> params3o,
//                                     @FieldMap Map<String, String> params4,
//                                     @FieldMap Map<String, String> params4o,
//                                     @FieldMap Map<String, String> params5,
//                                     @FieldMap Map<String, String> params5o,
//                                     @FieldMap Map<String, String> params6,
//                                     @FieldMap Map<String, String> params6o,
//                                     @FieldMap Map<String, String> params7,
//                                     @FieldMap Map<String, String> params7o,
//                                     @FieldMap Map<String, String> params8,
//                                     @FieldMap Map<String, String> params8o,
//                                     @FieldMap Map<String, String> params9,
//                                     @FieldMap Map<String, String> params9o,
//                                     @FieldMap Map<String, String> params);
//
//    @FormUrlEncoded
//    @POST("order/")
//    Call<LoginResponse> postShipLink(@Header("Authorization") String token,
//                                     @FieldMap Map<String, String> params1,
//                                     @FieldMap Map<String, String> params1o,
//                                     @FieldMap Map<String, String> params2,
//                                     @FieldMap Map<String, String> params2o,
//                                     @FieldMap Map<String, String> params3,
//                                     @FieldMap Map<String, String> params3o,
//                                     @FieldMap Map<String, String> params4,
//                                     @FieldMap Map<String, String> params4o,
//                                     @FieldMap Map<String, String> params5,
//                                     @FieldMap Map<String, String> params5o,
//                                     @FieldMap Map<String, String> params6,
//                                     @FieldMap Map<String, String> params6o,
//                                     @FieldMap Map<String, String> params7,
//                                     @FieldMap Map<String, String> params7o,
//                                     @FieldMap Map<String, String> params8,
//                                     @FieldMap Map<String, String> params8o,
//                                     @FieldMap Map<String, String> params9,
//                                     @FieldMap Map<String, String> params9o,
//                                     @FieldMap Map<String, String> params10,
//                                     @FieldMap Map<String, String> params10o,
//                                     @FieldMap Map<String, String> params);
//    @FormUrlEncoded
//    @POST("order/")
//    Call<LoginResponse> postShipLink(@Header("Authorization") String token,
//                                     @FieldMap Map<String, String> params1,
//                                     @FieldMap Map<String, String> params1o,
//                                     @FieldMap Map<String, String> params2,
//                                     @FieldMap Map<String, String> params2o,
//                                     @FieldMap Map<String, String> params3,
//                                     @FieldMap Map<String, String> params3o,
//                                     @FieldMap Map<String, String> params4,
//                                     @FieldMap Map<String, String> params4o,
//                                     @FieldMap Map<String, String> params5,
//                                     @FieldMap Map<String, String> params5o,
//                                     @FieldMap Map<String, String> params6,
//                                     @FieldMap Map<String, String> params6o,
//                                     @FieldMap Map<String, String> params7,
//                                     @FieldMap Map<String, String> params7o,
//                                     @FieldMap Map<String, String> params8,
//                                     @FieldMap Map<String, String> params8o,
//                                     @FieldMap Map<String, String> params9,
//                                     @FieldMap Map<String, String> params9o,
//                                     @FieldMap Map<String, String> params10,
//                                     @FieldMap Map<String, String> params10o,
//                                     @FieldMap Map<String, String> params11,
//                                     @FieldMap Map<String, String> params11o,
//                                     @FieldMap Map<String, String> params);
//
//    @FormUrlEncoded
//    @POST("order/")
//    Call<LoginResponse> postShipLink(@Header("Authorization") String token,
//                                     @FieldMap Map<String, String> params1,
//                                     @FieldMap Map<String, String> params1o,
//                                     @FieldMap Map<String, String> params2,
//                                     @FieldMap Map<String, String> params2o,
//                                     @FieldMap Map<String, String> params3,
//                                     @FieldMap Map<String, String> params3o,
//                                     @FieldMap Map<String, String> params4,
//                                     @FieldMap Map<String, String> params4o,
//                                     @FieldMap Map<String, String> params5,
//                                     @FieldMap Map<String, String> params5o,
//                                     @FieldMap Map<String, String> params6,
//                                     @FieldMap Map<String, String> params6o,
//                                     @FieldMap Map<String, String> params7,
//                                     @FieldMap Map<String, String> params7o,
//                                     @FieldMap Map<String, String> params8,
//                                     @FieldMap Map<String, String> params8o,
//                                     @FieldMap Map<String, String> params9,
//                                     @FieldMap Map<String, String> params9o,
//                                     @FieldMap Map<String, String> params10,
//                                     @FieldMap Map<String, String> params10o,
//                                     @FieldMap Map<String, String> params11,
//                                     @FieldMap Map<String, String> params11o,
//                                     @FieldMap Map<String, String> params12,
//                                     @FieldMap Map<String, String> params12o,
//                                     @FieldMap Map<String, String> params);
//
//    @FormUrlEncoded
//    @POST("order/")
//    Call<LoginResponse> postShipLink(@Header("Authorization") String token,
//                                     @FieldMap Map<String, String> params1,
//                                     @FieldMap Map<String, String> params1o,
//                                     @FieldMap Map<String, String> params2,
//                                     @FieldMap Map<String, String> params2o,
//                                     @FieldMap Map<String, String> params3,
//                                     @FieldMap Map<String, String> params3o,
//                                     @FieldMap Map<String, String> params4,
//                                     @FieldMap Map<String, String> params4o,
//                                     @FieldMap Map<String, String> params5,
//                                     @FieldMap Map<String, String> params5o,
//                                     @FieldMap Map<String, String> params6,
//                                     @FieldMap Map<String, String> params6o,
//                                     @FieldMap Map<String, String> params7,
//                                     @FieldMap Map<String, String> params7o,
//                                     @FieldMap Map<String, String> params8,
//                                     @FieldMap Map<String, String> params8o,
//                                     @FieldMap Map<String, String> params9,
//                                     @FieldMap Map<String, String> params9o,
//                                     @FieldMap Map<String, String> params10,
//                                     @FieldMap Map<String, String> params10o,
//                                     @FieldMap Map<String, String> params11,
//                                     @FieldMap Map<String, String> params11o,
//                                     @FieldMap Map<String, String> params12,
//                                     @FieldMap Map<String, String> params12o,
//                                     @FieldMap Map<String, String> params13,
//                                     @FieldMap Map<String, String> params13o,
//                                     @FieldMap Map<String, String> params);
//
//    @FormUrlEncoded
//    @POST("order/")
//    Call<LoginResponse> postShipLink(@Header("Authorization") String token,
//                                     @FieldMap Map<String, String> params1,
//                                     @FieldMap Map<String, String> params1o,
//                                     @FieldMap Map<String, String> params2,
//                                     @FieldMap Map<String, String> params2o,
//                                     @FieldMap Map<String, String> params3,
//                                     @FieldMap Map<String, String> params3o,
//                                     @FieldMap Map<String, String> params4,
//                                     @FieldMap Map<String, String> params4o,
//                                     @FieldMap Map<String, String> params5,
//                                     @FieldMap Map<String, String> params5o,
//                                     @FieldMap Map<String, String> params6,
//                                     @FieldMap Map<String, String> params6o,
//                                     @FieldMap Map<String, String> params7,
//                                     @FieldMap Map<String, String> params7o,
//                                     @FieldMap Map<String, String> params8,
//                                     @FieldMap Map<String, String> params8o,
//                                     @FieldMap Map<String, String> params9,
//                                     @FieldMap Map<String, String> params9o,
//                                     @FieldMap Map<String, String> params10,
//                                     @FieldMap Map<String, String> params10o,
//                                     @FieldMap Map<String, String> params11,
//                                     @FieldMap Map<String, String> params11o,
//                                     @FieldMap Map<String, String> params12,
//                                     @FieldMap Map<String, String> params12o,
//                                     @FieldMap Map<String, String> params13,
//                                     @FieldMap Map<String, String> params13o,
//                                     @FieldMap Map<String, String> params14,
//                                     @FieldMap Map<String, String> params14o,
//                                     @FieldMap Map<String, String> params);
//
//    @FormUrlEncoded
//    @POST("order/")
//    Call<LoginResponse> postShipLink(@Header("Authorization") String token,
//                                     @FieldMap Map<String, String> params1,
//                                     @FieldMap Map<String, String> params1o,
//                                     @FieldMap Map<String, String> params2,
//                                     @FieldMap Map<String, String> params2o,
//                                     @FieldMap Map<String, String> params3,
//                                     @FieldMap Map<String, String> params3o,
//                                     @FieldMap Map<String, String> params4,
//                                     @FieldMap Map<String, String> params4o,
//                                     @FieldMap Map<String, String> params5,
//                                     @FieldMap Map<String, String> params5o,
//                                     @FieldMap Map<String, String> params6,
//                                     @FieldMap Map<String, String> params6o,
//                                     @FieldMap Map<String, String> params7,
//                                     @FieldMap Map<String, String> params7o,
//                                     @FieldMap Map<String, String> params8,
//                                     @FieldMap Map<String, String> params8o,
//                                     @FieldMap Map<String, String> params9,
//                                     @FieldMap Map<String, String> params9o,
//                                     @FieldMap Map<String, String> params10,
//                                     @FieldMap Map<String, String> params10o,
//                                     @FieldMap Map<String, String> params11,
//                                     @FieldMap Map<String, String> params11o,
//                                     @FieldMap Map<String, String> params12,
//                                     @FieldMap Map<String, String> params12o,
//                                     @FieldMap Map<String, String> params13,
//                                     @FieldMap Map<String, String> params13o,
//                                     @FieldMap Map<String, String> params14,
//                                     @FieldMap Map<String, String> params14o,
//                                     @FieldMap Map<String, String> params15,
//                                     @FieldMap Map<String, String> params15o,
//                                     @FieldMap Map<String, String> params);
//
//    @FormUrlEncoded
//    @POST("order/")
//    Call<LoginResponse> postShipLink(@Header("Authorization") String token,
//                                     @FieldMap Map<String, String> params1,
//                                     @FieldMap Map<String, String> params1o,
//                                     @FieldMap Map<String, String> params2,
//                                     @FieldMap Map<String, String> params2o,
//                                     @FieldMap Map<String, String> params3,
//                                     @FieldMap Map<String, String> params3o,
//                                     @FieldMap Map<String, String> params4,
//                                     @FieldMap Map<String, String> params4o,
//                                     @FieldMap Map<String, String> params5,
//                                     @FieldMap Map<String, String> params5o,
//                                     @FieldMap Map<String, String> params6,
//                                     @FieldMap Map<String, String> params6o,
//                                     @FieldMap Map<String, String> params7,
//                                     @FieldMap Map<String, String> params7o,
//                                     @FieldMap Map<String, String> params8,
//                                     @FieldMap Map<String, String> params8o,
//                                     @FieldMap Map<String, String> params9,
//                                     @FieldMap Map<String, String> params9o,
//                                     @FieldMap Map<String, String> params10,
//                                     @FieldMap Map<String, String> params10o,
//                                     @FieldMap Map<String, String> params11,
//                                     @FieldMap Map<String, String> params11o,
//                                     @FieldMap Map<String, String> params12,
//                                     @FieldMap Map<String, String> params12o,
//                                     @FieldMap Map<String, String> params13,
//                                     @FieldMap Map<String, String> params13o,
//                                     @FieldMap Map<String, String> params14,
//                                     @FieldMap Map<String, String> params14o,
//                                     @FieldMap Map<String, String> params15,
//                                     @FieldMap Map<String, String> params15o,
//                                     @FieldMap Map<String, String> params16,
//                                     @FieldMap Map<String, String> params16o,
//                                     @FieldMap Map<String, String> params);
//
//    @FormUrlEncoded
//    @POST("order/")
//    Call<LoginResponse> postShipLink(@Header("Authorization") String token,
//                                     @FieldMap Map<String, String> params1,
//                                     @FieldMap Map<String, String> params1o,
//                                     @FieldMap Map<String, String> params2,
//                                     @FieldMap Map<String, String> params2o,
//                                     @FieldMap Map<String, String> params3,
//                                     @FieldMap Map<String, String> params3o,
//                                     @FieldMap Map<String, String> params4,
//                                     @FieldMap Map<String, String> params4o,
//                                     @FieldMap Map<String, String> params5,
//                                     @FieldMap Map<String, String> params5o,
//                                     @FieldMap Map<String, String> params6,
//                                     @FieldMap Map<String, String> params6o,
//                                     @FieldMap Map<String, String> params7,
//                                     @FieldMap Map<String, String> params7o,
//                                     @FieldMap Map<String, String> params8,
//                                     @FieldMap Map<String, String> params8o,
//                                     @FieldMap Map<String, String> params9,
//                                     @FieldMap Map<String, String> params9o,
//                                     @FieldMap Map<String, String> params10,
//                                     @FieldMap Map<String, String> params10o,
//                                     @FieldMap Map<String, String> params11,
//                                     @FieldMap Map<String, String> params11o,
//                                     @FieldMap Map<String, String> params12,
//                                     @FieldMap Map<String, String> params12o,
//                                     @FieldMap Map<String, String> params13,
//                                     @FieldMap Map<String, String> params13o,
//                                     @FieldMap Map<String, String> params14,
//                                     @FieldMap Map<String, String> params14o,
//                                     @FieldMap Map<String, String> params15,
//                                     @FieldMap Map<String, String> params15o,
//                                     @FieldMap Map<String, String> params16,
//                                     @FieldMap Map<String, String> params16o,
//                                     @FieldMap Map<String, String> params17,
//                                     @FieldMap Map<String, String> params17o,
//                                     @FieldMap Map<String, String> params);
//
//    @FormUrlEncoded
//    @POST("order/")
//    Call<LoginResponse> postShipLink(@Header("Authorization") String token,
//                                     @FieldMap Map<String, String> params1,
//                                     @FieldMap Map<String, String> params1o,
//                                     @FieldMap Map<String, String> params2,
//                                     @FieldMap Map<String, String> params2o,
//                                     @FieldMap Map<String, String> params3,
//                                     @FieldMap Map<String, String> params3o,
//                                     @FieldMap Map<String, String> params4,
//                                     @FieldMap Map<String, String> params4o,
//                                     @FieldMap Map<String, String> params5,
//                                     @FieldMap Map<String, String> params5o,
//                                     @FieldMap Map<String, String> params6,
//                                     @FieldMap Map<String, String> params6o,
//                                     @FieldMap Map<String, String> params7,
//                                     @FieldMap Map<String, String> params7o,
//                                     @FieldMap Map<String, String> params8,
//                                     @FieldMap Map<String, String> params8o,
//                                     @FieldMap Map<String, String> params9,
//                                     @FieldMap Map<String, String> params9o,
//                                     @FieldMap Map<String, String> params10,
//                                     @FieldMap Map<String, String> params10o,
//                                     @FieldMap Map<String, String> params11,
//                                     @FieldMap Map<String, String> params11o,
//                                     @FieldMap Map<String, String> params12,
//                                     @FieldMap Map<String, String> params12o,
//                                     @FieldMap Map<String, String> params13,
//                                     @FieldMap Map<String, String> params13o,
//                                     @FieldMap Map<String, String> params14,
//                                     @FieldMap Map<String, String> params14o,
//                                     @FieldMap Map<String, String> params15,
//                                     @FieldMap Map<String, String> params15o,
//                                     @FieldMap Map<String, String> params16,
//                                     @FieldMap Map<String, String> params16o,
//                                     @FieldMap Map<String, String> params17,
//                                     @FieldMap Map<String, String> params17o,
//                                     @FieldMap Map<String, String> params18,
//                                     @FieldMap Map<String, String> params18o,
//                                     @FieldMap Map<String, String> params);
//
//    @FormUrlEncoded
//    @POST("order/")
//    Call<LoginResponse> postShipLink(@Header("Authorization") String token,
//                                     @FieldMap Map<String, String> params1,
//                                     @FieldMap Map<String, String> params1o,
//                                     @FieldMap Map<String, String> params2,
//                                     @FieldMap Map<String, String> params2o,
//                                     @FieldMap Map<String, String> params3,
//                                     @FieldMap Map<String, String> params3o,
//                                     @FieldMap Map<String, String> params4,
//                                     @FieldMap Map<String, String> params4o,
//                                     @FieldMap Map<String, String> params5,
//                                     @FieldMap Map<String, String> params5o,
//                                     @FieldMap Map<String, String> params6,
//                                     @FieldMap Map<String, String> params6o,
//                                     @FieldMap Map<String, String> params7,
//                                     @FieldMap Map<String, String> params7o,
//                                     @FieldMap Map<String, String> params8,
//                                     @FieldMap Map<String, String> params8o,
//                                     @FieldMap Map<String, String> params9,
//                                     @FieldMap Map<String, String> params9o,
//                                     @FieldMap Map<String, String> params10,
//                                     @FieldMap Map<String, String> params10o,
//                                     @FieldMap Map<String, String> params11,
//                                     @FieldMap Map<String, String> params11o,
//                                     @FieldMap Map<String, String> params12,
//                                     @FieldMap Map<String, String> params12o,
//                                     @FieldMap Map<String, String> params13,
//                                     @FieldMap Map<String, String> params13o,
//                                     @FieldMap Map<String, String> params14,
//                                     @FieldMap Map<String, String> params14o,
//                                     @FieldMap Map<String, String> params15,
//                                     @FieldMap Map<String, String> params15o,
//                                     @FieldMap Map<String, String> params16,
//                                     @FieldMap Map<String, String> params16o,
//                                     @FieldMap Map<String, String> params17,
//                                     @FieldMap Map<String, String> params17o,
//                                     @FieldMap Map<String, String> params18,
//                                     @FieldMap Map<String, String> params18o,
//                                     @FieldMap Map<String, String> params19,
//                                     @FieldMap Map<String, String> params19o,
//                                     @FieldMap Map<String, String> params);
//
//    @FormUrlEncoded
//    @POST("order/")
//    Call<LoginResponse> postShipLink(@Header("Authorization") String token,
//                                     @FieldMap Map<String, String> params1,
//                                     @FieldMap Map<String, String> params1o,
//                                     @FieldMap Map<String, String> params2,
//                                     @FieldMap Map<String, String> params2o,
//                                     @FieldMap Map<String, String> params3,
//                                     @FieldMap Map<String, String> params3o,
//                                     @FieldMap Map<String, String> params4,
//                                     @FieldMap Map<String, String> params4o,
//                                     @FieldMap Map<String, String> params5,
//                                     @FieldMap Map<String, String> params5o,
//                                     @FieldMap Map<String, String> params6,
//                                     @FieldMap Map<String, String> params6o,
//                                     @FieldMap Map<String, String> params7,
//                                     @FieldMap Map<String, String> params7o,
//                                     @FieldMap Map<String, String> params8,
//                                     @FieldMap Map<String, String> params8o,
//                                     @FieldMap Map<String, String> params9,
//                                     @FieldMap Map<String, String> params9o,
//                                     @FieldMap Map<String, String> params10,
//                                     @FieldMap Map<String, String> params10o,
//                                     @FieldMap Map<String, String> params11,
//                                     @FieldMap Map<String, String> params11o,
//                                     @FieldMap Map<String, String> params12,
//                                     @FieldMap Map<String, String> params12o,
//                                     @FieldMap Map<String, String> params13,
//                                     @FieldMap Map<String, String> params13o,
//                                     @FieldMap Map<String, String> params14,
//                                     @FieldMap Map<String, String> params14o,
//                                     @FieldMap Map<String, String> params15,
//                                     @FieldMap Map<String, String> params15o,
//                                     @FieldMap Map<String, String> params16,
//                                     @FieldMap Map<String, String> params16o,
//                                     @FieldMap Map<String, String> params17,
//                                     @FieldMap Map<String, String> params17o,
//                                     @FieldMap Map<String, String> params18,
//                                     @FieldMap Map<String, String> params18o,
//                                     @FieldMap Map<String, String> params19,
//                                     @FieldMap Map<String, String> params19o,
//                                     @FieldMap Map<String, String> params20,
//                                     @FieldMap Map<String, String> params20o,
//                                     @FieldMap Map<String, String> params);



}
