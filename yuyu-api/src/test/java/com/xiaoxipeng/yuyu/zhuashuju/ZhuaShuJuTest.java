package com.xiaoxipeng.yuyu.zhuashuju;

import com.xiaoxipeng.api.ApiApplication;
import com.xiaoxipeng.api.pojo.Article;
import com.xiaoxipeng.api.service.IArticleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
        headers.put("Cookie", Collections.singletonList("_ga=GA1.1.1423301417.1744165744; bh=%7B%22path%22%3A%22book%22%2C%22bid%22%3A%228580%22%2C%22bname%22%3A%22%E5%BC%80%E5%B1%80%EF%BC%9A%E4%BB%8E%E7%BB%A7%E6%89%BF%E5%8D%83%E4%B8%87%E8%B1%AA%E5%AE%85%E5%BC%80%E5%A7%8B%22%2C%22sid%22%3A%226128295%22%2C%22sname%22%3A%22%E7%AC%AC1%E7%AB%A0%20%E5%B7%A8%E9%A2%9D%E9%81%97%E4%BA%A7%22%7D%2C%7B%22path%22%3A%22book%22%2C%22bid%22%3A%228547%22%2C%22bname%22%3A%22%E9%87%8D%E7%94%9F%E4%B9%8B%E6%8B%BC%E6%90%8F%E6%97%B6%E4%BB%A3%22%2C%22sid%22%3A%226104214%22%2C%22sname%22%3A%22%E7%AC%AC1%E7%AB%A0%20%E6%88%91%E5%8F%AB%E9%99%86%E9%98%B3%22%7D%2C%7B%22path%22%3A%22book%22%2C%22bid%22%3A%227417%22%2C%22bname%22%3A%22%E5%AF%8C%E8%B4%B5%E9%80%BC%E4%BA%BA%22%2C%22sid%22%3A%225135281%22%2C%22sname%22%3A%22%E7%AC%AC%E4%B8%80%E7%AB%A0%20%E4%B8%87%E5%8E%86%E5%8D%81%E4%BA%94%E5%B9%B4%22%7D; cf_clearance=xxrzuP56_6.rnyajKT1g_peuWJOyeku4VZb9ra90hHw-1744777341-1.2.1.1-mLQnvd6MRKYIqkGLfgui2Fror.mk_AzGRqqODPiSgun34tdBiY3AOvQJaqTvcXs3WnF8PVGP7BgQQ.2MbmN0IS8IRdl1lVhRTmf_cj.ozC2DgVZjvXIOxRCF_cvvxgpRVEQ69wxXdmdqAnfNfCc8j4yTUQ50AVDMLP_UzNkmskIaanU96DCRxWspOo6O85ArWsfqrUKzqxPF5BrDGwalV5Dk6LEn917GGYvhSoyoUSF0usVU0OtUOperKn2UAAfGzf065slx7T_h3T7wipFWp7uI5LE7Vh4.04kU.H4lYbC2VW3MnZwch_B46INLe.Ft0ER5Mfp35oJ9zG8L8Vl3LRaeFZADi1n9aGFhhCI2feRzdfwVPf4Em26KMhFgZup.; _ga_333KCL0K1B=GS1.1.1744777338.6.1.1744778538.0.0.0"));
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

    private String[] type = {"网游小说", "玄幻小说", "穿越小说", "科幻小说", "仙侠小说", "都市小说", "武侠小说"};


    @Test
    public void test2222() {
        String url = "http://www.hetushu.com/book/8822/6338789.html";
        String requestUrl = String.format(url, type[0]);

        HttpHeaders headers = new HttpHeaders();

        headers.add("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36");
        headers.add("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        headers.add("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8");
        headers.add("Connection", "keep-alive");
        headers.add("Upgrade-Insecure-Requests", "1");
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(requestUrl,HttpMethod.GET, entity ,String.class);
        String body = response.getBody();

    }
}
