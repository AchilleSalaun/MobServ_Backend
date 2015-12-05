package com.oneri.Recommendator;

/**
 * Created by Achille on 03/12/2015.
 */
public class AttributeException extends Exception
{
    public AttributeException(int i)
    {
        switch(i)
        {
            case 1 :  System.out.println("ERROR : you need n_recommendation <= n_sample ") ;
                break ;
            case 2 :  System.out.println("ERROR : Unknown type") ;
                break ;
            default : System.out.println("ERROR : an AttributeException has been detected") ;
        }
    }
}
