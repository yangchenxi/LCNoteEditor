package net.chenxiy.lcnote.net;

import net.chenxiy.lcnote.net.pojo.ProblemData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface ApiEndpointInterface {

//    //get basic user Info
    @GET("https://leetcode.com/api/problems/all")
    Call<ProblemData>getProblemData(@Header("Cookie") String cookie);
}