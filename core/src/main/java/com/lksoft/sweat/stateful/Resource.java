package com.lksoft.sweat.stateful;

/**
 * Created by Stallman on 19/08/2016.
 */
public abstract class Resource {
    // Number of users
    private int users = 0;

    /**
     * Use this animation
     */
    public void use(){
        users++;
    }

    /**
     * Releases the animation
     */
    public void release(){
        users--;
        if( users == 0 ){
            dispose();
        }
    }

    /**
     * @return Whether the animation has been disposed
     */
    public boolean isDisposed(){
        return users <= 0;
    }

    /**
     * Disposes the animation
     */
    protected abstract void dispose();
}
