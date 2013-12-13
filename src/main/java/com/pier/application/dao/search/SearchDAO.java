package com.pier.application.dao.search;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.lucene.document.Document;
import org.apache.lucene.store.Directory;

import java.io.IOException;
import java.util.List;

public interface SearchDAO {
    public List<Document> getDocuments() throws JsonProcessingException;
    public Directory getIndexedDirectory() throws IOException;
}
