package com.example.testimageupload;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface DownLoadService_Image {

    @GET
    Call<ResponseBody> downloadFileFromUrl(@Url String fileUrl);


}
