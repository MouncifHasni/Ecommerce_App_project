package com.example.ecommerceapp.retrofit;

import com.example.ecommerceapp.Admin.AdminModels.adminCatModel;
import com.example.ecommerceapp.Admin.AdminModels.adminInfoModel;
import com.example.ecommerceapp.Admin.AdminModels.adminItemModel;
import com.example.ecommerceapp.Admin.AdminModels.adminOrderModel;
import com.example.ecommerceapp.Models.ClientModel;
import com.squareup.okhttp.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;

public interface IMyService {

    //Admin***************************************
    @POST("admin_login")
    @FormUrlEncoded
    Observable<String> loginAdmin(@Field("email") String email
            ,@Field("password") String password);

    /**True work**/
    @Multipart
    @POST("modify_admin_image")
    Call<Void> modifyAdminImage(@Part MultipartBody.Part file,
            @Part("file") RequestBody description
    );
    @GET("/admin_info/{email}")
    Call<adminInfoModel> getAdminInfo(@Path("email") String email);

    @POST("modify_admin")
    Call<Void> modifyProfile(@Body HashMap<String, String> map);
    //Categories admin
    @Multipart
    @POST("add_cat")
    Call<Void> addCat(@Part MultipartBody.Part file,
                                @Part("file") RequestBody description
    );
    @Multipart
    @POST("modify_cat")
    Call<Void> modifyCat(@Part MultipartBody.Part file,
                      @Part("file") RequestBody description
    );
    @GET("show_categories")
    Call<List<adminCatModel>> fetchCategories();
    @POST("modify_cat_others")
    Call<Void> modifyCatOthers(@Body HashMap<String, String> map);
    @GET("/remove_cat/{url}")
    Call<Void> removeCategory(@Path("url") String url);
    //Items admin
    @Multipart
    @POST("add_item")
    Call<Void> addItem(@Part MultipartBody.Part file,
                          @PartMap Map<String, RequestBody> map);
    @GET("show_items/{cat}")
    Call<adminCatModel> fetchItems(@Path("cat") String cat);
    @Multipart
    @POST("modify_item")
    Call<Void> modifyItem(@Part MultipartBody.Part file,
                       @PartMap Map<String, RequestBody> map);
    @POST("modify_item_others")
    Call<Void> modifyItemtOthers(@Body HashMap<String, String> map);
    @GET("/remove_item/{cat}/{id}")
    Call<Void> removeItem(@Path("cat") String cat,@Path("id") String id);
    //Orders admin
    @GET("show_orders")
    Call<List<adminOrderModel>> fetchOrders();
    @POST("modify_order")
    Call<Void> modifyOrder(@Body HashMap<String, String> map);
    //***************************************
    //Client**********************************************
    @POST("register")
    @FormUrlEncoded
    Observable<String> registerUser(@Field("name") String name,
                                    @Field("email") String email,
                                    @Field("password") String password);

    @POST("login")
    @FormUrlEncoded
    Observable<String> loginUser(@Field("email") String email
            ,@Field("password") String password);
    @GET("/client_info/{email}")
    Call<ClientModel> getClientInfo(@Path("email") String email);
    @POST("add_toWishList")
    Call<String> addToWishList(@Body HashMap<String, String> map);
    @POST("add_adress")
    Call<String> addAdress(@Body HashMap<String, String> map);
    @POST("modify_adress")
    Call<String> modifyAdress(@Body HashMap<String, String> map);
}
