package com.liangzhi.mercury.elastic;

import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;

import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.FilterBuilder;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.SortOrder;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.liangzhi.mercury.elastic.model.SensorDataPointDocument;

public class ElasticSearchClient {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ElasticSearchClient.class);
    
    private Client client;
    private ObjectMapper MAPPER = new ObjectMapper();
    
    public ElasticSearchClient() {
       // on startup
       this.client = new TransportClient()
                .addTransportAddress(new InetSocketTransportAddress("localhost", 9300));
    }

    public void indexSensorDataDocument(SensorDataPointDocument doc) throws Exception {
        System.out.println(doc);
        String json = MAPPER.writeValueAsString(doc);
        IndexResponse response = client
                .prepareIndex("stem", "sensordata", doc.getId())
                .setSource(json).execute().actionGet();
        boolean created = response.isCreated();
        System.out.println("created:" + created);
    }
    
    public void indexMultipleSensorDataDocuments(List<SensorDataPointDocument> docs) throws Exception {
        BulkRequestBuilder bulkRequest = client.prepareBulk();
        for (SensorDataPointDocument doc : docs) {
            String json = MAPPER.writeValueAsString(doc);
            bulkRequest.add(client.prepareIndex("stem", "sensordata", doc.getId())
                    .setSource(json));
        }
        BulkResponse response = bulkRequest.execute().actionGet();
        if (response.hasFailures()) {
            Iterator<BulkItemResponse> iter = response.iterator();
            while (iter.hasNext()) {
                BulkItemResponse r = iter.next();
                System.out.println("failure: " + r.getFailureMessage());
            }
        }
    }
    
    public void queryDataByDateRange(long start, long end) throws Exception {
        FilterBuilder filter = FilterBuilders.andFilter(
                FilterBuilders.rangeFilter("sensorDataTimestamp").from(start).to(end));
        SearchResponse response = client.prepareSearch("stem")
                .setTypes("sensordata")
                .addSort("sensorDataTimestamp", SortOrder.ASC)
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setFrom(0)
                .setSize(10000)
                //.setPostFilter(filter)
                //.setFrom(0).setSize(50).setExplain(true)
                .execute()
                .actionGet();
        SearchHits hits = response.getHits();
        int i = 1;
        if (hits.getTotalHits() > 0) {
            for (SearchHit h : hits) {
                //h.getInnerHits()
                System.out.println(h.getSourceAsString());
                SensorDataPointDocument doc = MAPPER.readValue(h.getSourceAsString(), SensorDataPointDocument.class);
                DateTime dt = new DateTime(doc.getSensorDataTimestamp(), DateTimeZone.forTimeZone(TimeZone.getTimeZone("PST")));
                System.out.println(dt.toString());
                System.out.println(i);
                i++;
            }
        }
    }

}
