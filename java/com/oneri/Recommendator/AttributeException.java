package com.oneri.Recommendator;

/**
 * Created by Achille on 01/11/2015.
 */
public class AttributeException extends Exception
{
    public AttributeException(int n)
    {
      switch(n)
      {
          case 1: System.out.println("ERROR : wrong value for n_sample or n_recommendator");
              break ;
          case 2 : System.out.println("ERROR : wrong type entered.");
              break ;
          default : System.out.println("ERROR : AttributeException detected.");
      }
    }
}
