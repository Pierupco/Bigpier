package com.pier.application.service.search;

import com.pier.application.dao.MerchantDAO;
import com.pier.application.dao.search.SearchDAO;
import com.pier.application.dao.search.SimpleMerchantDbSearchDAO;
import com.pier.application.model.Merchant;
import com.pier.application.model.enums.BusinessType;
import com.pier.application.model.search.MerchantSearchResult;
import org.apache.lucene.queryparser.classic.ParseException;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestSearchService {

    @Test
    public void testSingleResultSearchByMerchantName() throws IOException, ParseException {
        final List<Merchant> merchants = new ArrayList<Merchant>();
        final Merchant merchant1 = new Merchant();
        merchant1.setId(1L);
        merchant1.setBusinessName("Tommy");
        merchant1.setBusinessType(BusinessType.APPAREL);
        merchants.add(merchant1);

        final Merchant merchant2 = new Merchant();
        merchant2.setId(2L);
        merchant2.setBusinessName("Versace");
        merchant2.setBusinessType(BusinessType.COSMETICS);
        merchants.add(merchant2);

        final MerchantDAO mockMerchantDao = mock(MerchantDAO.class);
        when(mockMerchantDao.findAllMerchants()).thenReturn(merchants);
        final SearchDAO searchDAO = new SimpleMerchantDbSearchDAO(mockMerchantDao);
        final SearchService searchService = new SearchService(searchDAO);

        final List<MerchantSearchResult> result = searchService.searchMerchantsByName("Tommy");

        Assert.assertEquals(result.size(), 1);
        final MerchantSearchResult merchantSearchResult = result.get(0);
        Assert.assertEquals(merchantSearchResult.getMerchantBusinessType(), BusinessType.APPAREL);
        Assert.assertEquals(merchantSearchResult.getMerchantName(), "Tommy");
    }

    @Test
    public void testAllResultsSearchByMerchantName() throws IOException, ParseException {
        final List<Merchant> merchants = new ArrayList<Merchant>();
        final Merchant merchant1 = new Merchant();
        merchant1.setId(1L);
        merchant1.setBusinessName("Tommy");
        merchant1.setBusinessType(BusinessType.APPAREL);
        merchants.add(merchant1);

        final Merchant merchant2 = new Merchant();
        merchant2.setId(2L);
        merchant2.setBusinessName("Versace");
        merchant2.setBusinessType(BusinessType.COSMETICS);
        merchants.add(merchant2);

        final MerchantDAO mockMerchantDao = mock(MerchantDAO.class);
        when(mockMerchantDao.findAllMerchants()).thenReturn(merchants);
        final SearchDAO searchDAO = new SimpleMerchantDbSearchDAO(mockMerchantDao);
        final SearchService searchService = new SearchService(searchDAO);

        final List<MerchantSearchResult> result = searchService.searchMerchantsByName("Tommy Versace");

        Assert.assertEquals(result.size(), 2);
        MerchantSearchResult merchantSearchResult = result.get(0);
        Assert.assertEquals(merchantSearchResult.getMerchantBusinessType(), BusinessType.APPAREL);
        Assert.assertEquals(merchantSearchResult.getMerchantName(), "Tommy");

        merchantSearchResult = result.get(1);
        Assert.assertEquals(merchantSearchResult.getMerchantBusinessType(), BusinessType.COSMETICS);
        Assert.assertEquals(merchantSearchResult.getMerchantName(), "Versace");
    }

    @Test
    public void testEmptyResultsSearchByMerchantName() throws IOException, ParseException {
        final List<Merchant> merchants = new ArrayList<Merchant>();
        final Merchant merchant1 = new Merchant();
        merchant1.setId(1L);
        merchant1.setBusinessName("Tommy");
        merchant1.setBusinessType(BusinessType.APPAREL);
        merchants.add(merchant1);

        final Merchant merchant2 = new Merchant();
        merchant2.setId(2L);
        merchant2.setBusinessName("Versace");
        merchant2.setBusinessType(BusinessType.COSMETICS);
        merchants.add(merchant2);

        final MerchantDAO mockMerchantDao = mock(MerchantDAO.class);
        when(mockMerchantDao.findAllMerchants()).thenReturn(merchants);
        final SearchDAO searchDAO = new SimpleMerchantDbSearchDAO(mockMerchantDao);
        final SearchService searchService = new SearchService(searchDAO);

        final List<MerchantSearchResult> result = searchService.searchMerchantsByName("Tommys");

        Assert.assertTrue(result.isEmpty());
    }

    @Test
    public void testSingleResultComplicatedQuerySearchByMerchantName() throws IOException, ParseException {
        final List<Merchant> merchants = new ArrayList<Merchant>();
        final Merchant merchant1 = new Merchant();
        merchant1.setId(1L);
        merchant1.setBusinessName("Tommy");
        merchant1.setBusinessType(BusinessType.APPAREL);
        merchants.add(merchant1);

        final Merchant merchant2 = new Merchant();
        merchant2.setId(2L);
        merchant2.setBusinessName("Versace");
        merchant2.setBusinessType(BusinessType.COSMETICS);
        merchants.add(merchant2);

        final MerchantDAO mockMerchantDao = mock(MerchantDAO.class);
        when(mockMerchantDao.findAllMerchants()).thenReturn(merchants);
        final SearchDAO searchDAO = new SimpleMerchantDbSearchDAO(mockMerchantDao);
        final SearchService searchService = new SearchService(searchDAO);

        final List<MerchantSearchResult> result = searchService.searchMerchantsByName("Tommy Versaces");

        Assert.assertEquals(result.size(), 1);
        MerchantSearchResult merchantSearchResult = result.get(0);
        Assert.assertEquals(merchantSearchResult.getMerchantBusinessType(), BusinessType.APPAREL);
        Assert.assertEquals(merchantSearchResult.getMerchantName(), "Tommy");
    }
}
