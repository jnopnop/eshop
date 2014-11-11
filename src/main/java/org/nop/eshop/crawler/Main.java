package org.nop.eshop.crawler;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.nop.eshop.model.Movie;
import org.nop.eshop.service.MovieScraper;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {

    public static void main(String[] args) throws Exception {
        Set<Future<Movie>> movieScrapers = new HashSet<>();
        ExecutorService executorService = Executors.newFixedThreadPool(16);
        long before = System.nanoTime();
        for (int i = 1; i <= 38; i++) {
            Document notebooksHome = ContentGrabber.getDocument(
                    "http://rozetka.com.ua/notebooks/c80004/filter/page=" + i + "/");
            Elements itemsOnPage = notebooksHome.select(".gtile-i-title>a[href]");
            for (Element n: itemsOnPage) {
                movieScrapers.add(
                        executorService.submit(new MovieScraper(n.attr("href"))));
            }
        }

        executorService.shutdown();
        System.out.print("Downloading...");
        for (Future<Movie> future: movieScrapers) {
            System.out.print('.');
            future.get();
        }
        long after = System.nanoTime();
        System.out.println("-------------------------------------Was downloaded " + movieScrapers.size() +
                " notebooks\n-------------------------------------Elapsed ~" + ((after - before) / 1000000000) + " sec");

    }

    private static void foo() {
        long before = System.nanoTime();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long after = System.nanoTime();
        System.out.println("Elapsed ~" + (after - before));
    }
}
