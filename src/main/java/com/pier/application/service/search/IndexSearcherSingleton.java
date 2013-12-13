package com.pier.application.service.search;

import com.pier.application.dao.search.SearchDAO;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.IndexSearcher;

import java.io.IOException;

public class IndexSearcherSingleton {
    private static IndexSearcher indexSearcher;

    public static synchronized IndexSearcher getIndexSearcher(final SearchDAO searchDao) throws IOException {
        if (indexSearcher == null) {
            IndexReader indexReader = DirectoryReader.open(searchDao.getIndexedDirectory());
            indexSearcher = new IndexSearcher(indexReader);
        }
        return indexSearcher;
    }
}
