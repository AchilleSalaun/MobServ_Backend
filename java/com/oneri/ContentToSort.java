package com.oneri;

/**
 * Created by Gaby on 09/11/2015.
 */
public class ContentToSort implements Comparable<ContentToSort>{

    private String id;
    private int ressemblance;

    public ContentToSort(String id, int ressemblance) {
        this.id = id;
        this.ressemblance = ressemblance;
    }

    @Override
    public int compareTo(ContentToSort otherContent) {
        return otherContent.ressemblance - this.ressemblance;
    }

    public String getId() {
        return id;
    }
}
