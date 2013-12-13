package com.pier.application.dao.search;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pier.application.dao.MerchantDAO;
import com.pier.application.model.Merchant;
import com.pier.support.util.JsonSerDe;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Component
public class SimpleMerchantDbSearchDAO implements SearchDAO {

    private final MerchantDAO merchantDAO;

    @Autowired
    public SimpleMerchantDbSearchDAO(MerchantDAO merchantDAO) {
        this.merchantDAO = merchantDAO;
    }

    @Override
    public List<Document> getDocuments() throws JsonProcessingException {
        final List<Document> documents = new ArrayList<Document>();
        final List<Merchant> merchants = merchantDAO.findAllMerchants();
        for (final Merchant merchant : merchants) {
            final Document document = new Document();
            document.add(new TextField(MerchantIndexFields.MERCHANT_NAME.name(), merchant.getBusinessName(), Field.Store.YES));
            document.add(new TextField(MerchantIndexFields.MERCHANT_BUSINESS_TYPE.name(), merchant.getBusinessType().name(), Field.Store.YES));
            documents.add(document);
        }
        return documents;
    }

    @Override
    public Directory getIndexedDirectory() throws IOException {
        Directory directory = new RAMDirectory();
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(Version.LUCENE_46, new StandardAnalyzer(Version.LUCENE_46));
        IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);

        for (final Document doc : getDocuments()) {
            indexWriter.addDocument(doc);
        }
        indexWriter.close();
        return directory;
    }
}
