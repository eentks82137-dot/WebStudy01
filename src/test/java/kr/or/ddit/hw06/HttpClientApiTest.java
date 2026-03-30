package kr.or.ddit.hw06;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;

import kr.or.ddit.hw05.service.GetExchangeRate;

public class HttpClientApiTest {

    @Test
    public void testHttpClient() throws IOException, InterruptedException {
        String url = "https://developer.mozilla.org/ko/docs/Web/HTTP/Reference/Headers";
        HttpClient httpClient = HttpClient.newHttpClient();
        URI uri = URI.create(url);
        HttpRequest httpRequest = HttpRequest.newBuilder().uri(uri).GET().header("accept", "text/html").build();
        HttpResponse<String> resp = httpClient.send(httpRequest, BodyHandlers.ofString());

        String respBody = resp.body();
        System.out.println(respBody);

    }

    // #content > div.section_calculator > table:nth-child(4) > tbody > tr >
    // td:nth-child(1)

    @Test
    public void testExchangeAPI() throws IOException, InterruptedException {
        String src = "https://finance.naver.com/marketindex/exchangeDetail.naver?marketindexCd=FX_USDKRW";
        String selector = "#content > div.section_calculator > table:nth-child(4) > tbody > tr > td:nth-child(1)";

        HttpClient httpClient = HttpClient.newHttpClient();
        URI uri = URI.create(src);
        HttpRequest httpRequest = HttpRequest.newBuilder().uri(uri).GET().header("accept", "text/html").build();
        HttpResponse<String> resp = httpClient.send(httpRequest, BodyHandlers.ofString());

        String respBody = resp.body();
        Document doc = Jsoup.parse(respBody);
        Elements temp = doc.select(selector);
        String rateString = temp.text().replaceAll(",", "");
        try {
            double rate = Double.parseDouble(rateString);
            System.out.println(rate);
        } catch (Exception e) {
            throw e;
        }

    }

    @Test
    public void testExchangeAPI2() throws IOException, InterruptedException {
        double rateJPY = GetExchangeRate.getRate("JPY", "KRW");
        System.out.println("100 JPY -> %.2f KRW".formatted(rateJPY));

        double rateCNY = GetExchangeRate.getRate("CNY", "KRW");
        System.out.println("1 CNY -> %.2f KRW".formatted(rateCNY));

    }
}
