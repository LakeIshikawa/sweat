package com.lksoft.yugen.stateful;

/**
 * Created by Stallman on 19/08/2016.
 */
public abstract class Resource {
    // Number of users
    private int users = 0;

    /**
     * Use this resource
     */
    public void use(){
        users++;
    }

    /**
     * Releases the resource
     */
    public void release(){
        users--;
        if( users == 0 ){
            dispose();
        }
    }

    /**
     * @return Whether the resource has been disposed
     */
    public boolean isDisposed(){
        return users <= 0;
    }

    /**
     * Disposes the resource
     */
    protected abstract void dispose();
}
