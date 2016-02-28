package Recommandator;

import java.util.ArrayList;

/**
 * Created by Achille on 24/10/2015.
 */
public interface RecommandatorInterface
{
    public ArrayList<AppItemInterface> matrixItem(ArrayList<AppItemInterface> itemList, ArrayList<AppUserInterface> userList);

    public ArrayList<AppUserInterface> matrixUser(ArrayList<AppItemInterface> itemList, ArrayList<AppUserInterface> userList);

    public ArrayList<AppItemInterface> clusterItem(ArrayList<AppItemInterface> itemList, ArrayList<AppUserInterface> userList);

    // public double markovRankingItem(AppItemInterface item, GraphUserInterface graph) ;

}
