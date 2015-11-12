package com.oneri.client;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

public class getParse
{
    public static void main(String args[])
    {

        Document doc;
        try{
            //String html = "<p> <a href='https://fr.wikipedia.org/wiki/Mario_Bros./'><b>mario</b></a> link.</p>";
            //doc = Jsoup.parse("https://fr.wikipedia.org/wiki/Mario_Bros.");

            doc = Jsoup.connect("https://fr.wikipedia.org/wiki/Mario_Bros.").get();
            String titre = doc.title();
            Element link = doc.select("tbody").first(); // selectionne le contenu wikipedia pour l'app

            //String text = doc.body().text(); // donne le contenu des premieres lignes

            String linkText = link.text(); // version texte du contenu html
            String linkOuterH = link.outerHtml(); // version balise du contenu html


            System.out.println("le titre de la page" +titre);
            //System.out.println("le texte"+text);

            System.out.println("le texte entre les balises :"+linkText);
            System.out.println("la balise entière"+linkOuterH);

        }catch(IOException e){


        }
    }
}