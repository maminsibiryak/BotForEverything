package classes;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Weather {
    private List<String> linesEng;
    private List<String> linesRus;


    public  String getLines(String cRu) throws IOException {
        String cEng = null;

        linesEng = Files.readAllLines(Paths.get("dictionaryEng.txt"), StandardCharsets.UTF_8);
        linesRus = Files.readAllLines(Paths.get("dictionaryRu.txt"), StandardCharsets.UTF_8);

        HashMap<String, String> map = new HashMap<>();
        for (int i = 0; i < linesRus.size(); i++) {
            map.put(linesEng.get(i), linesRus.get(i));
        }

        for (HashMap.Entry<String, String> m : map.entrySet()
                ) {

            String key = m.getKey();
            String value = m.getValue();
            if (cRu.toLowerCase().equals(value.toLowerCase())){
                cEng = key.toLowerCase();
            }

        }


        return cEng;
    }

    public  ArrayList<String> getWeather (String city) throws IOException {
        Weather weather = new Weather();
        String cityEng = weather.getLines(city) ;
        String url = "https://yandex.ru/pogoda/" + cityEng;
        Document page = Jsoup.parse(new URL(url), 3000);
        Elements main = page.select("div[class=content__row]");
        Elements divfact = main.select("div[class=fact]");
        Elements timeNow = divfact.select("time[class=time fact__time]");//время
        Elements spanNow = divfact.select("div[class=temp fact__temp]");//температура сейчас
        Elements divSunny = divfact.select("div[class=fact__condition day-anchor i-bem]");//ясно ли?
        Elements dlNow = divfact.select("dl[class=term term_orient_h fact__feels-like]");//ощущается
        Elements dlYesterday = divfact.select("dl[class=term term_orient_h fact__yesterday]");//вчера в это время
        Elements divAnother = divfact.select("div[class=fact__props]");//ветер давление влажность
        Elements divDay = main.select("div[class=content__brief]");
        Elements divDayFirst = divDay.select("div[class=day-parts-next i-bem]");
        Elements divDaySecond = divDay.select("div[class=details-celestial-bodies]");

        Elements divNow = divfact.select("div[class=nowcast-alert__text nowcast-alert__text_small]");//на данный момент

        ArrayList<String> result = new ArrayList<>();
        result.add(timeNow.text()+" "+spanNow.text());
        result.add(divSunny.text());
        result.add(dlNow.text());
        result.add(dlYesterday.text());
        result.add(divAnother.text());
        result.add(divNow.text());
        result.add(divDayFirst.text());
        result.add(divDaySecond.text());
        return result;

    }



    /* public static void main(String[] args) throws IOException {
        Weather weather = new Weather();
        System.out.println(weather.getWeather("москва"));


       Weather weather = new Weather();
        System.out.println(weather.getLines("Москва"));
        HashMap<String, String> map = weather.getLines();
        for (HashMap.Entry<String, String> m : map.entrySet()
                ) {

            String key = m.getKey();
            String value = m.getValue();
            System.out.println(key + " rus->" + value);

        }
    }*/
}
