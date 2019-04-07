package com.hackathon.btp.btplus.service;

import com.hackathon.btp.btplus.model.Payload;
import com.hackathon.btp.btplus.utils.Constants;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;


public interface APIInterface {

    @POST(Constants.VOICE)
    Call<Payload> postVoice(@Body Payload payload);



}