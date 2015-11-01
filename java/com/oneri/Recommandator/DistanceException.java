package com.oneri.Recommandator;

import sun.rmi.runtime.Log;

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
