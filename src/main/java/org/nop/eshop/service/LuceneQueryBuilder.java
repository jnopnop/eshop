package org.nop.eshop.service;


import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.nop.eshop.search.SearchEntry;

import java.util.List;

public interface LuceneQueryBuilder {
    BooleanQuery buildQueryFor(List<SearchEntry<?>> searchEntries, QueryBuilder queryBuilder);

    Query buildHighBoundedQuery(SearchEntry<?> s, QueryBuilder queryBuilder);

    Query buildLowBoundedQuery(SearchEntry<?> s, QueryBuilder queryBuilder);

    Query buildStrictlyLimitedQuery(SearchEntry<?> s, QueryBuilder queryBuilder);

    Query buildKeywordQuery(SearchEntry<?> s, QueryBuilder queryBuilder);
}
