package com.oneri.Recommendator;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Query;
import com.oneri.MyUtil;
import com.oneri.contentOriented.ExtensiveContent;
import com.oneri.userOriented.ExtensiveUser;
import com.oneri.userOriented.RelationToContent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;

/**
 * Created by Achille on 01/11/2015.
 */
public class Recommendator
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

        // our first hypothesis is : user1 doesn't know the content liked or disliked of user2
        int d = user2.getContentUserLikes().size()+user2.getContentUserDoesntLike().size() ;

        // We begin by looking for the content user1 likes in the several user2' lists...
        for(RelationToContent r1 : user1.getContentUserLikes())
        {
            // if user1 and user2 disagree about a content :
            // there is at least one content that both user1 and user2 know >> d--
            // and it adds [1-(-1)]^2=4 to the norm square
            if(user2.getContentUserDoesntLike().contains(r1)) d=d+3 ;

            // if user1 and user2 agree about a content :
            // there is at least one content that both user1 and user2 know >> d--
            else if (user2.getContentUserLikes().contains(r1)) d-- ;

            // user2 doesn't know at least one content that user1 likes or dislikes >> it adds [1-0]^2
            else d++ ;
        }

        // Then, we look for the content user1 dislikes in the several user2' lists
        for(RelationToContent r1 : user1.getContentUserDoesntLike())
        {
            if(user2.getContentUserLikes().contains(r1)) d=d+3 ;
            else if (user2.getContentUserDoesntLike().contains(r1)) d-- ;
            else d++;
        }

        // we return the Euclidian (relevance ot this norm ?) norm of v, which is  :
        return Math.sqrt(d) ;
    }

    public static ArrayList<ExtensiveUser> getSimilarUserTo(ExtensiveUser user)
    {
        ArrayList<ExtensiveUser> users = MyUtil.userFromDB(50);
        ArrayList<ExtensiveUser> similarUser = new ArrayList<ExtensiveUser>() ;
        for(ExtensiveUser u : users)
        {

        }
        return similarUser ;
    }


    /** Item Oriented **/
    // see the method distanceUser
    public static double distanceContent(ExtensiveContent content1, ExtensiveContent content2)
    {
        int d = 0 ;

        for(RelationToContent r1 : content1.getUsersWhoLikes())
        {
            if(content2.getUsersWhoDoesntLike().contains(r1)) d=d+3 ;
            else if (content2.getUsersWhoLikes().contains(r1)) d-- ;
            else d++ ;
        }

        for(RelationToContent r1 : content1.getUsersWhoDoesntLike())
        {
            if(content2.getUsersWhoDoesntLike().contains(r1)) d=d+3 ;
            else if (content2.getUsersWhoDoesntLike().contains(r1)) d-- ;
            else d++ ;
        }

        return Math.sqrt(d) ;
    }

    public static ArrayList<ExtensiveContent> getSimilarContentTo(ExtensiveContent content)
    {

        ArrayList<ExtensiveContent> contents = MyUtil.contentFromDB(50);
        ArrayList<ExtensiveContent> similarContent = new ArrayList<ExtensiveContent>() ;
        for(ExtensiveContent c : contents)
        {

        }
        return similarContent ;
    }

    // give the distance with the closest element of the list
    public static double distanceObjectToList(ExtensiveContent object, ArrayList<ExtensiveContent> list)
    {
        double d = 0 ;
        for(ExtensiveContent o : list)
            if(d>distanceContent(object, o)) d=distanceContent(object, o) ;

        return d ;
    }

    public static SortedList<ExtensiveContent> sortList(final ArrayList<ExtensiveContent> reference, ArrayList<ExtensiveContent> list)
    {
        Comparator<ExtensiveContent> comparator = new Comparator<ExtensiveContent>()
        {
            // what is the closest object "o1" or "o2" from the list "reference" ?
            @Override
            public int compare(ExtensiveContent o1, ExtensiveContent o2)
            {
                double d1 = 0;
                d1 = distanceObjectToList(o1, reference);

                double d2 = 0;
                d2 = distanceObjectToList(o2,reference);

                return((int) Math.signum(d1-d2)) ;
            }
        };

        return new SortedList<ExtensiveContent>((ObservableList<? extends ExtensiveContent>) list, comparator);
    }

    // remove redundant items in the SortedList
    public static void killPairs(SortedList<ExtensiveContent> list)
    {
        int size = list.size() ;
        for(int i = 0 ; i<size-1 ; i++)
        {
            while(list.get(i)==list.get(i+1))
            {
                list.remove(i+1);
            }
        }
    }

    /** General point of view **/
    // The ultimate goal !!!
    public static SortedList<ExtensiveContent> recommend(ExtensiveUser user)
    {
        // First, we look for similar users :
        ArrayList<ExtensiveUser> similarUsers = getSimilarUserTo(user) ; /** A implementer **/

        // We get all the content they like in the same list
        ArrayList<ExtensiveContent> recommendedContent = new ArrayList<ExtensiveContent>() ;
        for(ExtensiveUser u : similarUsers)
        {recommendedContent.addAll((Collection<? extends ExtensiveContent>) MyUtil.toArray(u));}

        // We add similar content to each content liked by the similar users :
        ArrayList<ExtensiveContent> aux = new ArrayList<ExtensiveContent>() ;
        for(ExtensiveContent c : recommendedContent)
        {
            aux.addAll(getSimilarContentTo(c)) ; /** A implementer **/
        }
        recommendedContent = aux ;

        // Finally, we sort this new list of content, with user's tastes (MyUtil.toArray(user)) as reference :
        SortedList<ExtensiveContent> recommendation = sortList(MyUtil.toArray(user), recommendedContent) ;

        /** remove any redundancy **/
        killPairs(recommendation) ;

        return recommendation ;
    }

    // The two following methods (under comment) have been first designed to function with Extensive Content as well as ExtensiveUser.
    // It's bonus methods, I let here to have model if needed.

    // give the distance between two lists
    //public static double distanceListToList(ArrayList<ObjectFromDB> list1, ArrayList<ObjectFromDB> list2) throws DistanceException
    //{
    //    double d = 0 ;
    //    for(ObjectFromDB o : list1)
    //        if(d>distanceObjectToList(o, list2)) d=distanceObjectToList(o, list2) ;
    //
    //    return d ;
    //}

    //public static SortedList<ObjectFromDB> sortObject(final ObjectFromDB object, ArrayList<ObjectFromDB> list)
    //{
    //    Comparator<ObjectFromDB> comparator = new Comparator<ObjectFromDB>()
    //    {
    //        // what is the closest object "o1" or "o2" from the object "object" ?
    //        @Override
    //        public int compare(ObjectFromDB o1, ObjectFromDB o2)
    //       {
    //            double d1 = 0;
    //            try
    //            {d1 = distance(o1, object);}
    //            catch (DistanceException e)
    //            {e.printStackTrace();}
    //
    //            double d2 = 0;
    //            try
    //            {d2 = distance(o2, object);}
    //            catch (DistanceException e)
    //            {e.printStackTrace();}
    //
    //            return((int) Math.signum(d1-d2)) ;
    //        }
    //    };
    //    return new SortedList<ObjectFromDB>((ObservableList<ObjectFromDB>) list, comparator);
    //}
}
