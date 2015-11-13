package com.oneri.Recommendator;

/**
 * Created by Achille on 01/11/2015.
 */
public class DistanceException extends Exception
{
    public DistanceException()
    {
        System.out.println("ERROR : distance method has been given objects whose type is wrong.");
    }
}
