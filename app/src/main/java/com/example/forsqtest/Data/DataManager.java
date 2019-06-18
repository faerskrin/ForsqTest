package com.example.forsqtest.Data;

import com.example.forsqtest.Data.model.Meta;
import com.example.forsqtest.Data.model.ResponceInfo;
import com.example.forsqtest.Data.model.Response;

import java.util.ArrayList;
import java.util.List;

public class DataManager {

    private Response mResponce;


    public Response getmResponce() {
        return mResponce;
    }

    public void setResponceInfo(Response response) {
        mResponce = response;

    }
}
