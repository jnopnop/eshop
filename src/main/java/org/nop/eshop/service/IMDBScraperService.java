package org.nop.eshop.service;


import org.jsoup.nodes.Element;
import org.nop.eshop.model.Person;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;

public interface IMDBScraperService {
    void execute() throws IOException, ParseException;
    Person loadPerson(String personURL) throws IOException, ParseException;
    void scrapMovie(Element e);
    String saveImage(String imgUrl, String path, String filename) throws MalformedURLException, InterruptedException;
}
