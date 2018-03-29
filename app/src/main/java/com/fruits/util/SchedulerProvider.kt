package com.fruits.util

import io.reactivex.Scheduler

/**
 * Provides schedulers for RX.
 */
open class SchedulerProvider
/**
 * Constructor.
 *
 * @param uiScheduler scheduler for andorid main thread.
 * @param ioScheduler scheduler for io threads.
 */
(val uiScheduler: Scheduler, val ioScheduler: Scheduler)