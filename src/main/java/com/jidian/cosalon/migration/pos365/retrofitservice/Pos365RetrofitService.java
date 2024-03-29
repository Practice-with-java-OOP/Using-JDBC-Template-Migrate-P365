package com.jidian.cosalon.migration.pos365.retrofitservice;

import com.jidian.cosalon.migration.pos365.domainpos365.Pos365Branch;
import com.jidian.cosalon.migration.pos365.domainpos365.Pos365Categories;
import com.jidian.cosalon.migration.pos365.domainpos365.Pos365Items;
import com.jidian.cosalon.migration.pos365.domainpos365.Pos365Order;
import com.jidian.cosalon.migration.pos365.domainpos365.Pos365OrderDetail;
import com.jidian.cosalon.migration.pos365.domainpos365.Pos365OrderStock;
import com.jidian.cosalon.migration.pos365.domainpos365.Pos365OrderStockDetail;
import com.jidian.cosalon.migration.pos365.domainpos365.Pos365Partner;
import com.jidian.cosalon.migration.pos365.domainpos365.Pos365Product;
import com.jidian.cosalon.migration.pos365.domainpos365.Pos365ProductHistory;
import com.jidian.cosalon.migration.pos365.domainpos365.Pos365ProductOnHandByBranch;
import com.jidian.cosalon.migration.pos365.domainpos365.Pos365Return;
import com.jidian.cosalon.migration.pos365.domainpos365.Pos365ReturnDetail;
import com.jidian.cosalon.migration.pos365.domainpos365.Pos365Transfer;
import com.jidian.cosalon.migration.pos365.domainpos365.Pos365TransfersDetail;
import com.jidian.cosalon.migration.pos365.domainpos365.Pos365User;
import com.jidian.cosalon.migration.pos365.dto.BaseResponse;
import com.jidian.cosalon.migration.pos365.dto.LoginRequest;
import com.jidian.cosalon.migration.pos365.dto.LoginResponse;

import java.sql.Timestamp;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Pos365RetrofitService {

    @POST("auth/credentials?format=json")
    Call<LoginResponse> login(@Body LoginRequest request);

    @GET("branchs?format=json")
    Call<BaseResponse<Pos365Branch>> listBranchs(@HeaderMap Map<String, String> headers);

    @GET("users?format=json")
    Call<BaseResponse<Pos365User>> listUsers(@HeaderMap Map<String, String> headers,
                                             @Query("top") Integer top, @Query("skip") Integer skip);

    @GET("products?Type=1&CategoryId=-1")
    Call<BaseResponse<Pos365Product>> listProducts(@HeaderMap Map<String, String> headers,
                                                   @Query("top") Integer top, @Query("skip") Integer skip);

    @GET("transfers")
    Call<BaseResponse<Pos365Transfer>> listTransfers(@HeaderMap Map<String, String> headers,
                                                     @Query("top") Integer top, @Query("skip") Integer skip);

    @GET("categories?format=json")
    Call<BaseResponse<Pos365Categories>> listCategories(@HeaderMap Map<String, String> headers);

    @GET("https://cosalon.pos365.vn/api/pricebooks/items")
    Call<BaseResponse<Pos365Items>> listItems(@HeaderMap Map<String, String> headers,
                                              @Query("top") Integer top, @Query("skip") Integer skip);

    @GET("orders")
    Call<BaseResponse<Pos365Order>> listOrders(@HeaderMap Map<String, String> headers,
                                               @Query("top") Integer top, @Query("skip") Integer skip);

    @GET("products/history?format=json")
    Call<BaseResponse<Pos365ProductHistory>> listProductsHistory(
            @HeaderMap Map<String, String> headers, @Query("top") Integer top,
            @Query("skip") Integer skip,
            @Query("ProductId") Long productId, @Query("BranchId") Long branchId);

    @GET("https://cosalon.pos365.vn/api/orderstock?")
    Call<BaseResponse<Pos365OrderStock>> listOrderStock(@HeaderMap Map<String, String> headers,
                                                        @Query("top") Integer top, @Query("skip") Integer skip);

    @GET("products/onhandbybranchs?format=json")
    Call<BaseResponse<Pos365ProductOnHandByBranch>> listProductOnHandByBranch(
            @HeaderMap Map<String, String> headers, @Query("top") Integer top,
            @Query("skip") Integer skip);

    @GET("returns?format=json&Includes=Partner")
    Call<BaseResponse<Pos365Return>> listReturn(@HeaderMap Map<String, String> headers,
                                                @Query("top") Integer top, @Query("skip") Integer skip);

    @GET("orderstock/detail?format=json")
    Call<BaseResponse<Pos365OrderStockDetail>> listOrderStockDetail(
            @HeaderMap Map<String, String> headers, @Query("top") Integer top,
            @Query("skip") Integer skip,
            @Query("PurchaseOrderId") Long orderStockId);

    @GET("partners?GroupId=0")
    Call<BaseResponse<Pos365Partner>> listPartner(@HeaderMap Map<String, String> headers,
                                                  @Query("top") Integer top, @Query("skip") Integer skip, @Query("Type") Integer type,
                                                  @Query("TransactionBeginDate") Timestamp startDate, @Query("TransactionEndDate") Timestamp endDate);

    @GET("transfers/detail?format=json")
    Call<BaseResponse<Pos365TransfersDetail>> listTransferDetails(
            @HeaderMap Map<String, String> headers, @Query("TransferId") Long transferId);

    @GET("returns/detail?format=json")
    Call<BaseResponse<Pos365ReturnDetail>> listReturnDetail(
            @HeaderMap Map<String, String> headers, @Query("top") Integer top,
            @Query("skip") Integer skip,
            @Query("ReturnId") Long returnId);

    @GET("orders/detail?format=json")
    Call<BaseResponse<Pos365OrderDetail>> listOrderDetail(
            @HeaderMap Map<String, String> headers, @Query("top") Integer top,
            @Query("skip") Integer skip,
            @Query("OrderId") Long orderId);
}
