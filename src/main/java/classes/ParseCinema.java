package classes;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class ParseCinema {
    public static Document getPage() throws IOException {
        String url = "http://www.peoplescinema.ru/cgi-bin/i10.cgi";
        Document page = Jsoup.parse(new URL(url),3000);
        return page;
    }
    public static ArrayList<String> printCinema(Document page) {

        Element table = page.select("table").get(6);
        Elements spanTable = table.select("span");
        System.out.print(spanTable.text()+"\n");
        Elements pTable = table.select("p[class=rs1]");
        ArrayList<String> stringArrayList = new ArrayList<>();
        stringArrayList.add(String.valueOf(spanTable.text()+"\n"));
        for (Element cinema: pTable
                ) {

            stringArrayList.add(cinema.text()+"\n");
          //  System.out.println(cinema.text()+"\n");

    }
    return stringArrayList;

    }
    /*public static void main(String[] args) throws IOException {
        Document page = getPage();
        printCinema(page);


    *//*    SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JOptionPane.showMessageDialog(null,new JList<>((ListModel<ArrayList<String>>) stringArrayList));
            }
        });*//*



    }*/
}
