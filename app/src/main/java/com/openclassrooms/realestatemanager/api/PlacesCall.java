/*
 * *
 *  * Created by Lionel Joffray on 17/09/19 23:02
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 17/09/19 11:17
 *
 */

package com.openclassrooms.realestatemanager.api;

import com.openclassrooms.realestatemanager.BuildConfig;
import com.openclassrooms.realestatemanager.models.places.nearby_search.Nearby;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;


/**
 * Created by Lionel JOFFRAY - on 12/05/2019.
 * <p>
 * API Call with hidden API Key.
 */
public interface PlacesCall {
    String API_KEY = BuildConfig.google_maps_key;

    @GET("nearbysearch/json?&key=" + API_KEY)
    Observable<Nearby> getLocationInfo(@Query("location") String location, @Query("type") String type, @Query("radius") int radius);
}

