package net.chenxiy.lcnote.net;

import net.chenxiy.lcnote.net.pojo.NoteData;
import net.chenxiy.lcnote.net.pojo.ProblemData;
import net.chenxiy.lcnote.net.pojo.github.TokenInfo;
import net.chenxiy.lcnote.net.pojo.github.repo.RepoInfo;
import net.chenxiy.lcnote.net.pojo.updatenote.UpDateNoteData;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiEndpointInterface {

//    //get basic user Info
    @GET("https://leetcode.com/api/problems/all")
    Call<ProblemData>getProblemData(@Header("Cookie") String cookie);



    @POST("https://leetcode.com/graphql")
    Call<NoteData> getNoteData(@Header("Cookie") String cookie,
                               @Header("User-Agent") String userAgent,
                               @Header("x-csrftoken") String csrfToken,
                               @Header("content-type") String contentType,
                               @Body RequestBody body);

    @POST("https://leetcode.com/graphql")
    Call<UpDateNoteData> updateNoteData(@Header("Cookie") String cookie,
                                        @Header("Referer") String referer,
                                        @Header("User-Agent") String userAgent,
                                        @Header("x-csrftoken") String csrfToken,
                                        @Header("content-type") String contentType,
                                        @Body RequestBody body);

//Oauth2.0 get token:

    @POST("https://github.com/login/oauth/access_token")
    Call<TokenInfo> getToken(@Header("Accept") String acceptType, @Query("client_id") String clientId,
                             @Query("client_secret")String secret,@Query("code")String code);

    @GET("/user/repos")
    Call<List<RepoInfo>> getUserRepo(@Header("Authorization") String token);

}