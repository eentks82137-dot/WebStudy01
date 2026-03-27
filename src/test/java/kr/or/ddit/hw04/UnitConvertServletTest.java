package kr.or.ddit.hw04;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Locale;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.google.gson.Gson;

import kr.or.ddit.hw04.dto.ConversionResponse;
import tools.jackson.databind.ObjectMapper;

public class UnitConvertServletTest {

    // 단위 변환 페이지가 제대로 나오는지 테스트
    @Test
    void testDoGet() throws Exception {
        URI uri = URI.create("http://localhost:8080/hw04/convert");
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri).GET().build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        // System.out.println(response.body());
        assertEquals(200, response.statusCode());
    }

    // 단위 변환이 제대로 되는지 테스트
    @Test
    void testDoPost1() throws Exception {
        String from = "KM";
        String to = "MILE";
        String value = "3";

        URI uri = URI.create("http://localhost:8080/hw04/convert?from=%s&to=%s&value=%s".formatted(from, to, value));
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri).headers("Content-Type", "application/json", "Accept",
                        "application/json")
                .POST(HttpRequest.BodyPublishers.ofString("")).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        ConversionResponse result = new Gson().fromJson(response.body(), ConversionResponse.class);

        ObjectMapper objectMapper = new ObjectMapper();

        @SuppressWarnings("unchecked")
        Map<String, Object> map = objectMapper.convertValue(result, Map.class);

        map.forEach((k, v) -> System.out.println(k + " " + v));
        assertEquals(200, response.statusCode());
    }

    // Locale에 따른 결과값이 다르게 나오는지 테스트
    @Test
    void testDoPostLocaleGerman() throws Exception {
        String from = "KM";
        String to = "MILE";
        String value = "10";
        Locale locale = Locale.GERMAN;
        String formatted = "6,214";

        URI uri = URI.create("http://localhost:8080/hw04/convert?from=%s&to=%s&value=%s".formatted(from, to, value));
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri).headers("Content-Type", "application/json", "Accept",
                        "application/json", "Accept-Language", locale.toLanguageTag())
                .POST(HttpRequest.BodyPublishers.ofString("")).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        ConversionResponse result = new Gson().fromJson(response.body(), ConversionResponse.class);

        ObjectMapper objectMapper = new ObjectMapper();

        @SuppressWarnings("unchecked")
        Map<String, Object> map = objectMapper.convertValue(result, Map.class);

        map.forEach((k, v) -> {
            System.out.println(k + " " + v);
            if (k.equals("formattedResult")) {
                assertEquals(v, formatted);
                System.out.println("Equal!");
            }
        });
        assertEquals(200, response.statusCode());
    }

    // 존재하지 않는 Locale로 요청했을 때 기본 Locale로 처리되는지 테스트
    @Test
    void testDoPostLocaleNonExistent() throws Exception {
        String from = "KM";
        String to = "MILE";
        String value = "10";
        String nonExistentLocale = "xx-XX";
        String formatted = "6.214";

        URI uri = URI.create("http://localhost:8080/hw04/convert?from=%s&to=%s&value=%s".formatted(from, to, value));
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri).headers("Content-Type", "application/json", "Accept",
                        "application/json", "Accept-Language", nonExistentLocale)
                .POST(HttpRequest.BodyPublishers.ofString("")).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        ConversionResponse result = new Gson().fromJson(response.body(), ConversionResponse.class);

        ObjectMapper objectMapper = new ObjectMapper();
        @SuppressWarnings("unchecked")
        Map<String, Object> map = objectMapper.convertValue(result, Map.class);

        map.forEach((k, v) -> {
            System.out.println(k + " " + v);
            if (k.equals("formattedResult")) {
                assertEquals(v, formatted);
                System.out.println("Equal!");
            }
        });
        assertEquals(200, response.statusCode());
    }

    // 잘못된 단위로 요청했을 때 400 Bad Request가 나오는지 테스트 (Accept: application/json)
    @Test
    void testDoPostInvalidUnit() throws Exception {
        String from = "INVALID";
        String to = "MILE";
        String value = "10";

        URI uri = URI.create("http://localhost:8080/hw04/convert?from=%s&to=%s&value=%s".formatted(from, to, value));
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri).headers("Content-Type", "application/json", "Accept",
                        "application/json")
                .POST(HttpRequest.BodyPublishers.ofString("")).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
        assertEquals(400, response.statusCode());
    }

    // TODO: 존재하지 않는 단위로 요청했을 때 400 Bad Request가 나오는지 테스트 (Accept: text/html)
    // 아 근데 accept가 text/html이면 400이 아니라 302로 리다이렉트 됨
    // redirect 된 페이지를 불러와야 되는데 session에 에러 메세지가 담겨 있어서
    // 이 부분은 추후 세션을 활용한 테스트 코드 작성 시 구현 예정
    @Test
    void testDoPostInvalidUnitHtml() throws Exception {
        String from = "INVALID";
        String to = "MILE";
        String value = "10";

        URI uri = URI.create("http://localhost:8080/hw04/convert?from=%s&to=%s&value=%s".formatted(from, to, value));
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri).headers("Content-Type", "application/json", "Accept",
                        "text/html")
                .POST(HttpRequest.BodyPublishers.ofString("")).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
        assertEquals(302, response.statusCode());
        String location = response.headers().firstValue("Location").orElse("");
        assertEquals("/hw04/convert", location);

    }
}
