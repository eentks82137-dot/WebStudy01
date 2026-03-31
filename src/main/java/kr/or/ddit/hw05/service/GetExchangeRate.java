package kr.or.ddit.hw05.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class GetExchangeRate {

    public static double getRate(String from, String to) throws IOException, InterruptedException, ParseException {
        // TODO from, to 문자열 말고 Currency 타입으로 변경하기
        String src = "https://finance.naver.com/marketindex/exchangeDetail.naver?marketindexCd=FX_%s%s".formatted(from,
                to);
        String selector = "#content > div.section_calculator > table:nth-child(4) > tbody > tr > td:nth-child(1)";

        HttpClient httpClient = HttpClient.newHttpClient();
        URI uri = URI.create(src);
        HttpRequest httpRequest = HttpRequest.newBuilder().uri(uri).GET().header("accept", "text/html").build();
        HttpResponse<String> resp = httpClient.send(httpRequest, BodyHandlers.ofString());

        String respBody = resp.body();
        Document doc = Jsoup.parse(respBody);
        Elements temp = doc.select(selector);
        NumberFormat numberFormat = NumberFormat.getInstance(Locale.KOREA);
        String rateString = temp.text();
        try {
            double rate = numberFormat.parse(rateString).doubleValue();
            return rate;
        } catch (Exception e) {
            throw e;
        }
    }
}
