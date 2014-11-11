package org.nop.eshop.service;

import org.nop.eshop.model.Movie;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class MovieScraper implements Callable<Movie> {
    private final String mainPageURL;
    private final ExecutorService executorService;

    public MovieScraper(String _url) {
        this.mainPageURL = _url;
        executorService = Executors.newSingleThreadExecutor();
    }

    public Movie call() throws Exception {
//        Future<List<String>> imagesCrawler = executorService.submit(new NotebookPriceImagesCrawler(mainPageURL));
//        List<String> images = imagesCrawler.get();
//        Document notebook = ContentGrabber.getDocument(characteristicsURL);
//        String title = notebook.select(".detail-tab-characteristics-title").text();
        Movie n = new Movie();
//        n.setTitle(title.replace("Технические характеристики", ""));
//        Elements characteristics = notebook.select(".detail-tab-characteristics-i");
//        for (Element c: characteristics) {
//            String key = c.child(0).text().toLowerCase();
//            String v = c.child(1).text();
//
//            if (key.contains("характеристики")) {
//                n.setShortDescription(v);
//            } else if (key.contains("экран")) {
//                try {
//                    String[] tokens = v.split(" ");
//                    Float diagonal = Float.valueOf(tokens[0].replaceAll("[^\\d\\.]", ""));
//                    String res = tokens[1].replace("(", "").replace(")", "");
//                    n.setDisplay(new Display(diagonal, res));
//                } catch (Exception e) {
//                    //System.out.println("^^^^^^^^^^^^^^^^^^^Can't process display for " + n.getTitle());
//                }
//            }  else if (key.contains("процессор")) {
//                try {
//                    String[] tokens = v.split(" ");
//                    String brand = tokens[1];
//                    String series = tokens[2];
//                    String marker = tokens[3];
//                    if (brand.equalsIgnoreCase("intel") && series.equalsIgnoreCase("core")) {
//                        String[] sm = marker.split("-");
//                        series = series + " " + sm[0];
//                        if (sm.length > 1)
//                            marker = sm[1];
//                    } else if (brand.equalsIgnoreCase("amd")) {
//                        String[] sm = marker.split("-");
//                        series = sm[0];
//                        if (sm.length > 1)
//                            marker = sm[1];
//                    }
//                    Float freq = Float.valueOf(tokens[4].replace("(", ""));
//                    n.setProcessor(new Processor(brand, series, marker, freq));
//                } catch (Exception e) {
////                    System.out.println("^^^^^^^^^^^^^^^^^^^Can't process CPU for " + n.getTitle());
//                }
//            }  else if (key.contains("объем оперативной")) {
//                try {
//                    n.setRam(Integer.valueOf(v.replace("ГБ", "").trim()));
//                } catch (Exception e) {
////                    System.out.println("^^^^^^^^^^^^^^^^^^^Can't process RAM for " + n.getTitle());
//                }
//            }  else if (key.contains("графический")) {
//                try {
//                    String[] gt = v.split(",");
//                    boolean isDiscrete = gt[0].toLowerCase().contains("дискрет");
//                    int volume = !isDiscrete ?
//                            0 : Integer.valueOf(gt[2].trim().split(" ")[0]);
//                    gt = gt[1].trim().split(" ");
//                    String brand = gt[0];
//                    String series = gt[1];
//                    if (!isDiscrete && brand.equalsIgnoreCase("intel")) {
//                        series += " " + gt[2];
//                    }
//                    n.setGpu(new GPU(brand, isDiscrete, series, v, volume));
//                } catch (Exception e) {
////                    System.out.println("^^^^^^^^^^^^^^^^^^^Can't process GPU for " + n.getTitle());
//                }
//            }  else if (key.contains("накопителя")) {
//                try {
//                    String[] tokens = v.split(" ");
//                    Integer size = Integer.valueOf(tokens[0]);
//                    if (tokens[1].contains("Т"))
//                        size *= 1000;
//                    n.setStorageSize(size);
//                } catch (Exception e) {
////                    System.out.println("^^^^^^^^^^^^^^^^^^^Can't process storage for " + n.getTitle());
//                }
//            }  else if (key.contains("ввода-вывода")) {
//                n.setIo(v);
//            }  else if (key.contains("операционная")) {
//                n.setOs(new OS(v.trim()));
//            }  else if (key.contains("батарея")) {
//                n.setBattery(v);
//            }  else if (key.contains("вес")) {
//                try {
//                    n.setWeight(Float.valueOf(v.replace("кг", "").trim()));
//                } catch (NumberFormatException e) {
////                    System.out.println("^^^^^^^^^^^^^^^^^^^Can't process weight for " + n.getTitle());
//                }
//            }
//
//        }
//        executorService.shutdown();
//        n.setPrice(Float.valueOf(images.get(0))); //
//        n.setImage(images.get(1));
        return n;
    }
}
