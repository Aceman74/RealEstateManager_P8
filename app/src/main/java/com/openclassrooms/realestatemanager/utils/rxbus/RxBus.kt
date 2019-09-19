/*
 * *
 *  * Created by Lionel Joffray on 19/09/19 21:47
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 19/09/19 21:35
 *
 */

package com.openclassrooms.realestatemanager.utils.rxbus

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

/**
 * Created by Lionel JOFFRAY - on 27/08/2019.
 * Object for having a Singleton.
 * For event handler, callback
 */

object RxBus {

    private val publisher = PublishSubject.create<Any>()

    fun publish(event: Any) {
        publisher.onNext(event)
    }

    // Listen should return an Observable and not the publisher
    // Using ofType we filter only events that match that class type
    fun <T> listen(eventType: Class<T>): Observable<T> = publisher.ofType(eventType)
}