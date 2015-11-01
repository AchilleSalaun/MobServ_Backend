package com.oneri.Recommandator;

import com.oneri.SuperClasses.User;
import com.oneri.contentOriented.ExtensiveContent;
import com.oneri.database.ObjectFromDB;
import com.oneri.userOriented.ExtensiveUser;
import com.oneri.userOriented.RelationToContent;

import java.util.ArrayList;
import java.util.Comparator;

import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;

/**
 * Created by Achille on 01/11/2015.
 */
public class Recommandator
{

    /** User Oriented **/
    public static double distanceUser(ExtensiveUser user1, ExtensiveUser user2)
    {
        // Let figure out the list of all the content suggested by the app, relatively to the user 1 :
        //
        // if a given content is liked by this user, it weight is 1 ;
        // if a given content is disliked by this user, it weight is -1 ;
        // else, its weight is 0 ;
        //
        // Thus, for each user, we can get a vector U containing 1,0 or -1 depending on the relation between the user and each content.
        // Then, v:=U1-U2 : we want to get its norm.

        int d = user2.getContentUserLikes().size()+user2.getContentUserDoesntLike().size() ;


        // We begin by looking for the content user1 likes in the several user2' lists...
        for(RelationToContent r1 : user1.getContentUserLikes())
        {
            if(user2.getContentUserDoesntLike().contains(r1))d=d+3 ;
            else if (user2.getContentUserLikes().contains(r1))d-- ;
            else d++ ;
        }

        // Then, we look for the content user1 dislikes in the several user2' lists
        for(RelationToContent r1 : user1.getContentUserDoesntLike())
        {
            if(user2.getContentUserLikes().contains(r1))d=d+3 ;
            else if (user2.getContentUserDoesntLike().contains(r1))d-- ;
            else d++;
        }

        // we return the Euclidian (relevance ot this norm ?) norm of v, which is  :
        return Math.sqrt(d) ;
    }


    /** Item Oriented **/
    // see the method distanceUser
    public static double distanceContent(ExtensiveContent content1, ExtensiveContent content2)
    {
        int d = 0 ;

        for(RelationToContent r1 : content1.getUsersWhoLikes())
        {
            if(content2.getUsersWhoDoesntLike().contains(r1))d=d+3 ;
            else if (content2.getUsersWhoLikes().contains(r1))d-- ;
            else d++ ;
        }

        for(RelationToContent r1 : content1.getUsersWhoDoesntLike())
        {
            if(content2.getUsersWhoDoesntLike().contains(r1))d=d+3 ;
            else if (content2.getUsersWhoDoesntLike().contains(r1))d-- ;
            else d++ ;
        }

        return Math.sqrt(d) ;
    }

    /** General point of view **/
    // distance allow to generalize general methods on Contents as well as on Users
    public static double distance(ObjectFromDB object1, ObjectFromDB object2) throws DistanceException
    {
        if(object1 instanceof ExtensiveUser && object2 instanceof ExtensiveUser)
        {
            return distanceUser((ExtensiveUser)object1,(ExtensiveUser)object2);
        }
        else if(object1 instanceof ExtensiveContent && object2 instanceof ExtensiveContent)
        {
            return distanceContent((ExtensiveContent) object1, (ExtensiveContent) object2);
        }
        //!\ see DistanceException() >> use of System.out.println instead of ??? /!\\
        else throw new DistanceException();
    }


    public static SortedList<ObjectFromDB> sortRecommandation(final ObjectFromDB object, ArrayList<ObjectFromDB> list)
    {
        Comparator<ObjectFromDB> comparator = new Comparator<ObjectFromDB>()
        {
            // what is the closest object "o1" or "o2" from the object "object" ?
            @Override
            public int compare(ObjectFromDB o1, ObjectFromDB o2)
            {
                double d1 = 0;
                try
                {
                    d1 = distance(o1, object);
                } catch (DistanceException e)
                {
                    e.printStackTrace();
                }

                double d2 = 0;
                try
                {
                    d2 = distance(o2,object);
                } catch (DistanceException e)
                {
                    e.printStackTrace();
                }

                return((int) Math.signum(d1-d2)) ;
            }
        };

        return new SortedList<ObjectFromDB>((ObservableList<? extends ObjectFromDB>) list, comparator);
    }


}
