package com.cbs.ghgroup.retrofit;

import com.cbs.ghgroup.model.DownlodLedgerRequest;
import com.cbs.ghgroup.model.ageingpiechart.AgeingDetails;
import com.cbs.ghgroup.model.billregister.BillRegister;
import com.cbs.ghgroup.model.branchdeatils.BranchList;
import com.cbs.ghgroup.model.commgenledger.CommGenLedger;
import com.cbs.ghgroup.model.creditregister.CreditRegister;
import com.cbs.ghgroup.model.customerledger.CustomerLedger2;
import com.cbs.ghgroup.model.debitregister.DebitNoteRegister;
import com.cbs.ghgroup.model.directpending.DirectPending;
import com.cbs.ghgroup.model.getotp.GetOtp;
import com.cbs.ghgroup.model.ghcustomerdashboard.GhCustomerDashboard;
import com.cbs.ghgroup.model.ghlogin.GhLogin;
import com.cbs.ghgroup.model.grcommunication.GRCommunication;
import com.cbs.ghgroup.model.paymentcommunication.PaymentCommunication;
import com.cbs.ghgroup.model.paymentregister.PaymentRegister;
import com.cbs.ghgroup.model.pendingbill.PendingBills;
import com.cbs.ghgroup.model.profile.UserProfile;
import com.cbs.ghgroup.model.purchesregister.PurchaseRegister;
import com.cbs.ghgroup.model.receiptregister.ReceiptRegister;
import com.cbs.ghgroup.model.saleregister.SaleRegister;
import com.cbs.ghgroup.model.userlist.ExcelData;
import com.cbs.ghgroup.model.userlist.UsersList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("LoginService.svc/otpVarify")
    Call<GetOtp> loginWithOtp(
            @Query("CODE") String CODE);

    @GET("LoginService.svc/GHLogin")
    Call<GhLogin> login(
            @Query("CODE") String CODE,
            @Query("OTP") String OTP
            // @Query("mobileNo") String mobileNo,
            // @Query("ISCUSTOMER_OR_VENDOR") String ISCUSTOMER_OR_VENDOR
    );


    @GET("LoginService.svc/branchmanagement")
    Call<BranchList> getBranchList(@Query("Code") String code,
                                   @Query("CVCode") String cVCode,
                                   @Query("Role") String role
    );


    @GET("LoginService.svc/UserProfile")
    Call<UserProfile> getUserProfile(@Query("CardCode") String CardCode,
                                     @Query("Branch") String Branch,
                                     @Query("IsCV") String IsCV);

    @GET("LoginService.svc/GetUsersList")
    Call<UsersList> getUsersList(@Query("Code") String Code,
                                 @Query("IsCustomerVendor") String IsCustomerVendor,
                                 @Query("Type") String Type);


    @GET("LoginService.svc/CustomerLedger2")
    Call<CustomerLedger2> getCustomerLedger(@Query("FromDate") String fromdate,
                                            @Query("ToDate") String todate,
                                            @Query("BpCode") String bpcode,
                                            @Query("Type") String type,
                                            @Query("Branches") String branches,
                                            @Query("IsCV") String IsCV

    );

    @GET("LoginService.svc/CreditRegister")
    Call<CreditRegister> getCreditRegister(@Query("FromDate") String FromDate,
                                           @Query("ToDate") String ToDate,
                                           @Query("CardCode") String CardCode,
                                           @Query("Branch") String Branch,
                                           @Query("IsCV") String IsCV
    );


    @GET("LoginService.svc/PendingBills")
    Call<PendingBills> getPendingBills(@Query("ToDate") String ToDate,
                                       @Query("BpCode") String BpCode,
                                       @Query("Type") String Type,
                                       @Query("Branches") String Branches,
                                       @Query("IsCV") String IsCV
    );

    @GET("LoginService.svc/PurchaseRegister")
    Call<PurchaseRegister> getPurchaseRegister(@Query("FromDate") String FromDate,
                                               @Query("ToDate") String ToDate,
                                               @Query("CardCode") String CardCode,
                                               @Query("Branch") String Branch,
                                               @Query("IsCV") String IsCV
    );

    @GET("LoginService.svc/ReceiptRegister?")
    Call<ReceiptRegister> getReceiptRegister(@Query("FromDate") String FromDate,
                                             @Query("ToDate") String ToDate,
                                             @Query("CardCode") String CardCode,
                                             @Query("Branch") String Branch,
                                             @Query("IsCV") String IsCV
    );

    @GET("LoginService.svc/SaleRegister")
    Call<SaleRegister> getSaleRegister(@Query("FromDate") String FromDate,
                                       @Query("ToDate") String ToDate,
                                       @Query("CardCode") String CardCode,
                                       @Query("Branch") String Branch,
                                       @Query("IsCV") String IsCV
    );

    @GET("LoginService.svc/DebitNoteRegister")
    Call<DebitNoteRegister> getDebitRegister(@Query("FromDate") String FromDate,
                                             @Query("ToDate") String ToDate,
                                             @Query("CardCode") String CardCode,
                                             @Query("Branch") String Branch,
                                             @Query("IsCV") String IsCV
    );

    @GET("LoginService.svc/PaymentRegister")
    Call<PaymentRegister> getPaymentRegister(@Query("FromDate") String FromDate,
                                             @Query("ToDate") String ToDate,
                                             @Query("CardCode") String CardCode,
                                             @Query("Branch") String Branch,
                                             @Query("IsCV") String IsCV
    );

    @POST("api/Values/PostPDF")
    Call<String> downloadPdf(@Body DownlodLedgerRequest downlodLedgerRequest);

    @POST("api/Values/ExcelData")
    Call<String> downloadExcel(@Body ExcelData excelData);

    @GET("LoginService.svc/GeneralReport")
    Call<CommGenLedger> getCommGenLedger(@Query("FromDate") String fromdate,
                                         @Query("ToDate") String todate,
                                         @Query("CardCode") String cardCode,
                                         @Query("Type") String type,
                                         @Query("Branch") String Branch,
                                         @Query("IsCV") String IsCV
    );

    @GET("LoginService.svc/BillRegister")
    Call<BillRegister> getBillRegister(@Query("FromDate") String fromdate,
                                       @Query("ToDate") String todate,
                                       @Query("CardCode") String cardcode,
                                       @Query("Branch") String Branch,
                                       @Query("IsCV") String IsCV
    );

    @GET("LoginService.svc/CommunicationRegister")
    Call<GRCommunication> getGRCommunication(@Query("FromDate") String fromdate,
                                             @Query("ToDate") String todate,
                                             @Query("CardCode") String cardcode,
                                             @Query("Branch") String Branch,
                                             @Query("IsCV") String IsCV
    );


    @GET("LoginService.svc/PaymentCommunication")
    Call<PaymentCommunication> getPaymentCommunication(@Query("FromDate") String fromdate,
                                                       @Query("ToDate") String todate,
                                                       @Query("CardCode") String cardcode,
                                                       @Query("Branch") String Branch,
                                                       @Query("IsCV") String IsCV
    );

    @GET("LoginService.svc/DirectPending")
    Call<DirectPending> getDirectPending(@Query("FromDate") String fromdate,
                                         @Query("ToDate") String todate,
                                         @Query("CardCode") String cardcode,
                                         @Query("Branch") String Branch,
                                         @Query("Type") String type,
                                         @Query("IsCV") String IsCV
    );
    @GET("LoginService.svc/CustomerDashBoard?")
    Call<GhCustomerDashboard> getGhBillingGraphDashboard(
                                                    @Query("CusCode") String CusCode,
                                                    @Query("FromDate") String FromDate,
                                                    @Query("ToDate") String ToDate,
                                                    @Query("Branches") String Branches);
    @GET("LoginService.svc/AgeingDetails?")
    Call<AgeingDetails> getAgeingPieChart(@Query("ToDate")String ToDate,
                                          @Query("CardCode")String CardCode,
                                          @Query("Type")String Type,
                                          @Query("Branches")String Branches,
                                          @Query("Flag")String Flag);
}
