package com.example.SearchService;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.*;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;
import java.util.Collections;

public class SearchService {
    private final RestClient restClient;

    public SearchService(String elasticsearchEndpoint) {
        restClient = RestClient.builder(HttpHost.create(elasticsearchEndpoint)).build();
    }

    public SearchResponse search(String indexName, String query) throws IOException {
        SearchRequest searchRequest = new SearchRequest(indexName);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery("field_name", query));
        searchRequest.source(searchSourceBuilder);

        try (Response response = restClient.performRequest("POST", "/_search", Collections.emptyMap(), EntityUtil.getEntity(searchRequest))) {
            return EntityUtil.getEntity(response, SearchResponse.class);
        }
    }
//    public class Main {
//        public static void main(String[] args) throws IOException {
//            String elasticsearchEndpoint = "https://your-aws-es-endpoint.amazonaws.com"; // Replace with your Elasticsearch endpoint
//            String indexName = "your-index-name"; // Replace with your Elasticsearch index name
//
//            SearchService searchService = new SearchService(elasticsearchEndpoint);
//
//            // Perform a search
//            String query = "search query";
//            SearchResponse response = searchService.search(indexName, query);
//
//            // Process the search results
//            // You can access the hits and other search information from the response
//        }
//    }

}

