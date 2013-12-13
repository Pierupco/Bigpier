package com.pier.application.service.search;

import com.pier.application.dao.search.MerchantIndexFields;
import com.pier.application.dao.search.SearchDAO;
import com.pier.application.model.enums.BusinessType;
import com.pier.application.model.search.MerchantSearchResult;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.util.Version;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class SearchService {

    private static final Log log = LogFactory.getLog(SearchService.class);
    private static final int TOP_DOCS_NUM = 5;

    private SearchDAO searchDao;

    @Autowired
    public SearchService(final SearchDAO searchDao) throws IOException {
        this.searchDao = searchDao;
    }

    public List<MerchantSearchResult> searchMerchantsByName(final String queryString) throws IOException, ParseException {
        final IndexSearcher indexSearcher = IndexSearcherSingleton.getIndexSearcher(searchDao);
        final Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_46);
        final QueryParser parser = new QueryParser(Version.LUCENE_46, MerchantIndexFields.MERCHANT_NAME.name(), analyzer);
        final Query query = parser.parse(queryString);
        final TopDocs topDocs = indexSearcher.search(query, TOP_DOCS_NUM);
        final ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        final List<MerchantSearchResult> merchants = new ArrayList<MerchantSearchResult>();
        if (log.isDebugEnabled()) {
            log.debug(String.format("For query %s, %d results found", queryString, scoreDocs.length));
        }
        for (final ScoreDoc scoreDoc : scoreDocs) {
            int docIndex = scoreDoc.doc;
            final Document doc = indexSearcher.doc(docIndex);
            final String merchantName = doc.get(MerchantIndexFields.MERCHANT_NAME.name());
            final String merchantBusinesssType = doc.get(MerchantIndexFields.MERCHANT_BUSINESS_TYPE.name());
            final MerchantSearchResult merchantSearchResult = new MerchantSearchResult(merchantName, BusinessType.fromString(merchantBusinesssType));
            merchants.add(merchantSearchResult);
        }
        return merchants;
    }

}
