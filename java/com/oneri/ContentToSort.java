package com.oneri;

import com.oneri.SuperClasses.Content;

/**
 * Created by Gaby on 09/11/2015.
 */
public class ContentToSort extends Content implements Comparable<ContentToSort>{

    private int ressemblance;

    public ContentToSort(String title, String contentType, int ressemblance) {
        super(title, contentType);
        this.ressemblance = ressemblance;
    }

    @Override
    public int compareTo(ContentToSort otherContent) {
        return otherContent.ressemblance - this.ressemblance;
    }
}
