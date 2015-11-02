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

    /** General point of view **/
    // distance allow to apply general methods on Contents as well as on Users
    public static double distance(ObjectFromDB object1, ObjectFromDB object2) throws DistanceException
    {
        if(object1 instanceof ExtensiveUser && object2 instanceof ExtensiveUser)
            return distanceUser((ExtensiveUser)object1,(ExtensiveUser)object2);

        else if(object1 instanceof ExtensiveContent && object2 instanceof ExtensiveContent)
            return distanceContent((ExtensiveContent) object1, (ExtensiveContent) object2);

        //!\ see DistanceException() >> use of System.out.println instead of ??? /!\\
        else throw new DistanceException();
    }

    // give the distance with the closest element of the list
    public static double distanceObjectToList(ObjectFromDB object, ArrayList<ObjectFromDB> list) throws DistanceException
    {
        double d = 0 ;
        for(ObjectFromDB o : list)
            if(d>distance(object,o)) d=distance(object,o) ;

        return d ;
    }

    // give the distance between two lists
    public static double distanceListToList(ArrayList<ObjectFromDB> list1, ArrayList<ObjectFromDB> list2) throws DistanceException {
        double d = 0 ;
        for(ObjectFromDB o : list1)
            if(d>distanceObjectToList(o, list2)) d=distanceObjectToList(o, list2) ;

        return d ;
    }

    public static SortedList<ObjectFromDB> sortObject(final ObjectFromDB object, ArrayList<ObjectFromDB> list)
    {
        Comparator<ObjectFromDB> comparator = new Comparator<ObjectFromDB>()
        {
            // what is the closest object "o1" or "o2" from the object "object" ?
            @Override
            public int compare(ObjectFromDB o1, ObjectFromDB o2)
            {
                double d1 = 0;
                try
                {d1 = distance(o1, object);}
                catch (DistanceException e)
                {e.printStackTrace();}

                double d2 = 0;
                try
                {d2 = distance(o2,object);}
                catch (DistanceException e)
                {e.printStackTrace();}

                return((int) Math.signum(d1-d2)) ;
            }
        };

        return new SortedList<ObjectFromDB>((ObservableList<ObjectFromDB>) list, comparator);
    }

    public static SortedList<ObjectFromDB> sortList(final ArrayList<ObjectFromDB> reference, ArrayList<ObjectFromDB> list)
    {
        Comparator<ObjectFromDB> comparator = new Comparator<ObjectFromDB>()
        {
            // what is the closest object "o1" or "o2" from the list "reference" ?
            @Override
            public int compare(ObjectFromDB o1, ObjectFromDB o2)
            {
                double d1 = 0;
                try
                {d1 = distanceObjectToList(o1, reference);}
                catch (DistanceException e)
                {e.printStackTrace();}

                double d2 = 0;
                try
                {d2 = distanceObjectToList(o2,reference);}
                catch (DistanceException e)
                {e.printStackTrace();}

                return((int) Math.signum(d1-d2)) ;
            }
        };

        return new SortedList<ObjectFromDB>((ObservableList<ObjectFromDB>) list, comparator);
    }

    public static SortedList<ExtensiveContent> recommand(ExtensiveUser user)
    {
        // First, we look for similar users :
        /** getSimilarUsersTo(ExtensiveUser user) returning SortedList<ExtensiveUser> similarUsers **/

        // We get all the content they like in the same list
        ArrayList<ExtensiveContent> recommandedContent = new ArrayList<ExtensiveContent>() ;
        /** for(ExtensiveUser u : similarUsers){recommandedContent.add(...)} **/

        // We add similar content :
        /** for(ExtensiveContent c : recommandedContent)
         * {getSimilarContentTo(ExtensiveContent content) returning SortedList<ExtensiveContent> similarContent} **/

        // Finally, we sort this :
        SortedList<ExtensiveContent> recommandation = new SortedList<ExtensiveContent>((ObservableList<ExtensiveContent>) recommandedContent/** or/and similarContent **/) ;
        /** sortList(L1,L2) **/
        recommandation = sortList(user.getContentUserLikes(), recommandedContent) ;
        return recommandation ;
    }
}
