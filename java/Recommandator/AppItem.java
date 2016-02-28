package Recommandator;

/**
 * Created by Achille on 24/10/2015.
 */
public class AppItem implements AppItemInterface
{
    private String title ;
    private String author ;
    private String kind ;
    private int year ;

    public AppItem(String title, String author, String kind, int year)
    {
        this.title = title ;
        this.author = author ;
        this.kind = kind ;
        this.year = year ;
    }

    public String getTitle()
    {
        return this.title ;
    }

    public String getAuthor()
    {
        return this.author ;
    }

    public  String getKind()
    {
        return this.kind ;
    }

    public int getYear()
    {
        return this.year ;
    }



}
