package org.nop.eshop.service;


import org.jsoup.nodes.Element;
import org.nop.eshop.model.Person;

import java.io.IOException;
import java.text.ParseException;

public interface IMDBScraperService {
    public static final int DEFAULT_CONNECTION_TIMEOUT = 300000;

    void execute() throws IOException, ParseException;
    Person loadPerson(String personURL) throws IOException, ParseException;
    void scrapMovie(Element e);
}
