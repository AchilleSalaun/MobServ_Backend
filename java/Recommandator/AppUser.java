package Recommandator;

import java.util.ArrayList;

/**
 * Created by Achille on 24/10/2015.
 */
public class AppUser implements AppUserInterface
{
    private String name ;
    private int age ;
    private boolean gender ;
    private String email ;

    private ArrayList<AppItemInterface> likeList ;
    private ArrayList<AppItemInterface> dislikeList ;
    private ArrayList<AppItemInterface> waitList ;

    private ArrayList<AppUserInterface> followList ;

    public AppUser(String name, int age, boolean gender, String email)
    {
        this.name = name ;
        this.age = age ;
        this.gender = gender ;
        this.email = email ;

        this.likeList = new ArrayList<AppItemInterface>() ;
        this.dislikeList = new ArrayList<AppItemInterface>() ;
        this.waitList = new ArrayList<AppItemInterface>() ;

        this.followList = new ArrayList<AppUserInterface>() ;

    }
}
