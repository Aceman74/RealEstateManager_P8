/*
 * *
 *  * Created by Lionel Joffray on 19/09/19 21:47
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 19/09/19 20:05
 *
 */

package com.openclassrooms.realestatemanager.api

import com.openclassrooms.realestatemanager.models.places.nearby_search.Nearby
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


/**
 * Created by Lionel JOFFRAY - on 12/05/2019.
 *
 * Places API for call to Google API with Retrofit.
 *
 * @see Retrofit
 */
class PlacesApi
/**
 * Private constructor for Retrofit
 */
private constructor() {
    internal var mRetrofit: Retrofit

    init {

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        val dispatcherMaxR = Dispatcher()
        dispatcherMaxR.maxRequests = 2

        mRetrofit = Retrofit.Builder()
                .baseUrl("https://maps.googleapis.com/maps/api/place/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build()
    }

    /**
     * Observable for MapsNearby
     *
     * @return List of places
     */
    fun getLocationInfo(location: String, type: String, radius: Int): Observable<Nearby> {
        val callInfo = mRetrofit.create(PlacesCall::class.java)
        return callInfo.getLocationInfo(location, type, radius)
                .delay(500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())   //  Run call on another thread
                .observeOn(AndroidSchedulers.mainThread())  //  Observe on the Main thread
                .timeout(10, TimeUnit.SECONDS)
    }

    companion object {

        val instance = PlacesApi()
    }
}
