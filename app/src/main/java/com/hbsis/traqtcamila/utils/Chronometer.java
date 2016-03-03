package com.hbsis.traqtcamila.utils;

import android.os.Handler;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * A Chronometer with call backs for time update and optional time limit.
 */
public class Chronometer {

    //
    // -- PRIVATE FIELDS

    private long startTime = 0;
    private long accumulatedTime = 0;
    private long elapsedSinceLastRefresh = 0;
    private long refreshInterval = 500;
    private Long timeLimit;
    private OnUpdateListener onUpdateListener;
    private OnCompleteListener onCompleteListener;

    final private Handler handler = new Handler();
    private Timer chronoTimer;

    //
    // -- CONSTRUCTORS

    public Chronometer() {
    }

    //
    // -- PROPERTIES

    /**
     * Get the current time limit in seconds.
     * @return The current time limit.
     */
    public Long getTimeLimit() {
        return TimeUnit.NANOSECONDS.toSeconds(timeLimit);
    }

    /**
     * Set the time limit for this chronometer.
     * @param timeLimit The time limit of this chronometer in seconds.
     */
    public void setTimeLimit(Long timeLimit) {
        this.timeLimit = TimeUnit.SECONDS.toNanos(timeLimit);
    }

    /**
     * Gets the current refresh interval.
     * @return The Refresh Interval in milliseconds.
     */
    public long getRefreshInterval() {
        return refreshInterval;
    }

    /**
     * Sets the refresh interval.
     * @param refreshInterval The refresh interval in milliseconds.
     */
    public void setRefreshInterval(long refreshInterval) {
        this.refreshInterval = refreshInterval;
    }

    /**
     * Get the current elapsed time in seconds.
     * @return The elapsed time in seconds.
     */
    public long getElapsedSeconds() {
        return TimeUnit.NANOSECONDS.toSeconds(getElapsedNano());
    }

    /**
     * Get the current elapsed time in milliseconds.
     * @return The elapsed time in milliseconds.
     */
    public long getElapsedMillis() {
        return TimeUnit.NANOSECONDS.toMillis(getElapsedNano());
    }

    /**
     * Get the current elapsed time in nanoseconds.
     * @return The elapsed time in nanos.
     */
    public long getElapsedNano() {
        return elapsedSinceLastRefresh + accumulatedTime;
    }

    public void setOnUpdateListener(OnUpdateListener onUpdateListener) {
        this.onUpdateListener = onUpdateListener;
    }

    public void setOnCompleteListener(OnCompleteListener onCompleteListener) {
        this.onCompleteListener = onCompleteListener;
    }

    //
    // -- PRIVATE API

    private void cancelTimer() {
        if (chronoTimer != null) {
            chronoTimer.cancel();
            chronoTimer = null;
        }
    }

    private void refreshTime() {
        // Calculates the new time
        long refreshTime = System.nanoTime();
        elapsedSinceLastRefresh = (refreshTime - startTime);

        // Calculates the remaining time if applicable
        Long remainingTime = null;
        if (timeLimit != null) {
            remainingTime = TimeUnit.NANOSECONDS.toSeconds(timeLimit - getElapsedNano());

            // If the chronometer is complete, then call the completed listener
            if (getElapsedNano() >= timeLimit) {
                stop();
                if (onCompleteListener != null)
                    onCompleteListener.onComplete();
            }
        }

        // If an update listener is specified then call it
        if (onUpdateListener != null)
            onUpdateListener.onUpdate(getElapsedSeconds(), remainingTime);
    }

    //
    // -- PUBLIC API

    /**
     * Start the execution of the Chronometer.
     * Start will take place using accumulated time from the last session.
     * If the Chronometer is running the call will be ignored.
     */
    public void start() {
        if (chronoTimer == null) {
            startTime = System.nanoTime();

            // Initialize the Chronometer Timer Task
            TimerTask chronoTimerTask = new TimerTask() {
                @Override
                public void run() {
                    // Ensures running on the main Thread
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            // Just call the refresh time method
                            refreshTime();
                        }
                    });
                }
            };

            // Creates and starts a new timer
            chronoTimer = new Timer();
            chronoTimer.schedule(chronoTimerTask, 0, refreshInterval);
        }
    }

    /**
     * Stops the execution of the Chronometer. Keeps the accumulated value, in order to allow pausing of the chronometer, and to keeep "elapsedTime" property value available for the user to keep track.
     */
    public void stop() {
        cancelTimer();

        accumulatedTime = getElapsedNano();
        elapsedSinceLastRefresh = 0;
    }

    /**
     * Resets the Chronometer.
     */
    public void reset() {
        cancelTimer();

        elapsedSinceLastRefresh = 0;
        accumulatedTime = 0;
    }

    //
    // -- INNER TYPES

    /**
     * Interface definition for a callback to be invoked when the chronometer updates it's values.
     */
    public interface OnUpdateListener {

        /**
         * Called when the chronometer values are updated.
         * @param elapsedTime The current elapsed time in seconds.
         * @param remainingTime The remaining time if a time limit is set for this instance.
         */
        void onUpdate(long elapsedTime, Long remainingTime);

    }

    /**
     * Interface definition for a callback to be invoked when the chronometer completes running.
     */
    public interface OnCompleteListener {

        /**
         * Called when the chronometer reaches it's limit time and completes running.
         */
        void onComplete();

    }

}
