package com.lelyuh.testcarapp.domain.rx

import io.reactivex.rxjava3.core.Scheduler

/**
 * Interface for different Rx-schedulers for async work
 *
 * @author Leliukh Aleksandr
 */
interface RxSchedulers {

    fun ui(): Scheduler

    fun io(): Scheduler
}