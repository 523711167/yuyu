package com.xiaoxipeng.yuyu.zhuashuju;

import com.xiaoxipeng.api.ApiApplication;
import com.xiaoxipeng.api.pojo.Article;
import com.xiaoxipeng.api.service.IArticleService;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

@SpringBootTest(classes = ApiApplication.class)
@Slf4j
public class ZhuaShuJuTest {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    IArticleService articleServiceImpl;

    @Autowired
    RetryTemplate retryTemplate;

    @Test
    public void test() throws Exception {
        String url = "https://www.hetushu.com/book/17/";
        String begin = "13606.html";

        save(url, begin, 1L);

    }

    @Test
    public void test1() throws Exception {
        String url = "https://www.hetushu.com/book/8967/";
        String begin = "6452418.html";

        save(url, begin, 2L);

    }

    @Test
    public void test2() throws Exception {
        String url = "https://www.hetushu.com/book/952/";
        String begin = "641310.html";

        save(url, begin, 3L);
    }

    @Test
    public void test3() throws Exception {
        String url = "https://www.hetushu.com/book/970/";
        String begin = "653522.html";

        save(url, begin, 5L);
    }

    @Test
    public void test5() throws Exception {
        String url = "https://www.hetushu.com/book/5763/";
        String begin = "432863.html";

        save(url, begin, 6L);
    }

    @Test
    public void test7() throws Exception {
        String url = "https://www.hetushu.com/book/1311/";
        String begin = "874057.html";

        save(url, begin, 7L);
    }

    @Test
    public void test8() throws Exception {
        String url = "https://www.hetushu.com/book/230/";
        String begin = "160044.html";

        save(url, begin, 7L);
    }

    @Test
    public void test9() throws Exception {
        String url = "https://www.hetushu.com/book/8931/";
        String begin = "6423904.html";

        save(url, begin, 7L);
    }

    @Test
    public void test10() throws Exception {
        String url = "https://www.hetushu.com/book/8797/";
        String begin = "6320455.html";

        save(url, begin, 10L);
    }

    @Test
    public void test11() throws Exception {
        String url = "https://www.hetushu.com/book/8693/";
        String begin = "6215029.html";

        save(url, begin, 11L);
    }

    @Test
    public void test12() throws Exception {
        String url = "https://www.hetushu.com/book/8668/";
        String begin = "6191258.html";

        save(url, begin, 12L);
    }

    @Test
    public void test13() throws Exception {
        String url = "https://www.hetushu.com/book/8609/";
        String begin = "6153521.html";

        save(url, begin, 13L);
    }

    @Test
    public void test14() throws Exception {
        String url = "https://www.hetushu.com/book/8580/";
        String begin = "6128295.html";

        save(url, begin, 14L);
    }

    @Test
    public void test15() throws Exception {
        String url = "https://www.hetushu.com/book/8547/";
        String begin = "6104214.html";

        save(url, begin, 15L);
    }

    private void save(String url, String begin, Long bookId) throws Exception {
//        TimeUnit.SECONDS.sleep(3);
        HttpHeaders headers = new HttpHeaders();
        headers.put("Cookie", Collections.singletonList("_ga=GA1.1.1423301417.1744165744; bh=%7B%22path%22%3A%22book%22%2C%22bid%22%3A%2217%22%2C%22bname%22%3A%22%E6%96%97%E7%A0%B4%E8%8B%8D%E7%A9%B9%22%2C%22sid%22%3A%2212689%22%2C%22sname%22%3A%22%E7%AC%AC33%E7%AB%A0%20%E8%AF%81%E5%AE%9E%22%7D; _ga_333KCL0K1B=GS1.1.1744172062.3.1.1744172997.0.0.0"));
        HttpEntity<String> entity = new HttpEntity<>(headers);

        AtomicReference<String> html = new AtomicReference<>();
        retryTemplate.execute(context -> {
            ResponseEntity<String> response = restTemplate.exchange(
                    url + begin,
                    HttpMethod.GET,
                    entity,
                    String.class
            );
            html.set(response.getBody());
            if (html.get() == null) {
                throw new Exception("111");
            }
            return "over";
        });


        // 解析HTML
        Document document = Jsoup.parse(html.get());
        // 获取id为content的div
        Element next = document.getElementById("next");
        String href = next.attr("href");
        String index = href.substring(href.lastIndexOf("/") + 1);
        String nextUrl = url + index;

        String title = document.getElementById("ctitle").getElementsByClass("title").get(0).text();
        String[] split = title.split(" ");

        Element contentDiv = document.getElementById("content");
        String content = contentDiv.text().replaceAll("htｔｐs://www.hｅｔｕsｈｕ•com.cｏｍ ", "");

        Article article = new Article();
        article.setSection(split[0]);
        article.setTitle(split.length == 1 ? "" : split[1]);
        article.setContent(content);
        article.setBookId(bookId);
        article.setCreatedBy("1");
        articleServiceImpl.save(article);
        log.info("调用的地址 ===> {}", url + begin);
        save(url, index, bookId);
    }
}
