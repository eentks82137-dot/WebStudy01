package kr.or.ddit.hw06;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import org.apache.commons.lang3.function.Failable;
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
    public void testExchangeAPI1() throws IOException, InterruptedException {
        String src = "https://finance.naver.com/marketindex/exchangeList.naver";
        HttpClient httpClient = HttpClient.newHttpClient();
        URI uri = URI.create(src);
        HttpRequest httpRequest = HttpRequest.newBuilder().uri(uri).GET().header("accept", "text/html").build();
        HttpResponse<String> resp = httpClient.send(httpRequest, BodyHandlers.ofString());

        String respBody = resp.body();
        Document doc = Jsoup.parse(respBody);
        NumberFormat formatter = NumberFormat.getInstance(Locale.KOREA);

        double rateUSD = doc.select(".tit").stream().filter(el -> el.text().contains("USD")).findFirst()
                .map(el -> el.parent()).map(p -> p.selectFirst(".sale")).map(s -> s.text())
                .map(Failable.asFunction(t -> formatter.parse(t).doubleValue())).orElse(1500d);
        System.out.println("1 USD -> %.2f KRW".formatted(rateUSD));
    }

    @Test
    public void testExchangeAPI2() throws IOException, InterruptedException, ParseException {
        double rateJPY = GetExchangeRate.getRate("JPY", "KRW");
        System.out.println("100 JPY -> %.2f KRW".formatted(rateJPY));

        double rateCNY = GetExchangeRate.getRate("CNY", "KRW");
        System.out.println("1 CNY -> %.2f KRW".formatted(rateCNY));

    }
}
