package org.nop.eshop.service;


import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.nop.eshop.search.SearchEntry;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LuceneQueryBuilderImpl implements LuceneQueryBuilder {
    @Override
    public BooleanQuery buildQueryFor(List<SearchEntry<?>> searchEntries, QueryBuilder queryBuilder) {
        BooleanQuery booleanQuery = new BooleanQuery();
        for (SearchEntry<?> s: searchEntries) {
            if (s.isMultiValue()) {
                List<Query> queries = buildMultiValueQuery(s, queryBuilder);
                for (Query q: queries) {
                    booleanQuery.add(q, BooleanClause.Occur.MUST);
                }
            } else if (!s.isRange()) {
                booleanQuery.add(buildKeywordQuery(s, queryBuilder), BooleanClause.Occur.MUST);
            } else if (s.isStrictlyLimited()) {
                booleanQuery.add(buildStrictlyLimitedQuery(s, queryBuilder), BooleanClause.Occur.MUST);
            } else if (s.isOnlyLowBounded()) {
                booleanQuery.add(buildLowBoundedQuery(s, queryBuilder), BooleanClause.Occur.MUST);
            } else {
                booleanQuery.add(buildHighBoundedQuery(s, queryBuilder), BooleanClause.Occur.MUST);
            }
        }
        return booleanQuery;
    }

    private List<Query> buildMultiValueQuery(SearchEntry<?> s, QueryBuilder queryBuilder) {
        String fieldName = s.getFieldName();
        List<Query> result = new ArrayList<>();
        for (Object keyword: s.getValues()) {
            result.add(queryBuilder
                .keyword()
                .onField(fieldName)
                .matching(keyword).createQuery());
        }
        return result;
    }

    @Override
    public Query buildHighBoundedQuery(SearchEntry<?> s, QueryBuilder queryBuilder) {
        return queryBuilder
                .range()
                .onField(s.getFieldName())
                .below(s.getValue2())
                .createQuery();
    }

    @Override
    public Query buildLowBoundedQuery(SearchEntry<?> s, QueryBuilder queryBuilder) {
        return queryBuilder
                .range()
                .onField(s.getFieldName())
                .above(s.getValue())
                .createQuery();
    }

    @Override
    public Query buildStrictlyLimitedQuery(SearchEntry<?> s, QueryBuilder queryBuilder) {
        return queryBuilder
                .range()
                .onField(s.getFieldName())
                .from(s.getValue()).to(s.getValue2())
                .createQuery();
    }

    @Override
    public Query buildKeywordQuery(SearchEntry<?> s, QueryBuilder queryBuilder) {
        return queryBuilder
                .keyword()
                .onField(s.getFieldName())
                .matching(s.getValue())
                .createQuery();
    }
}
