package com.demo.elasticsearch_demo.rest;

import static com.demo.elasticsearch_demo.action.LoggerUtils.emitErrorResponse;
import static org.elasticsearch.rest.RestRequest.Method.POST;

import java.io.IOException;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.node.NodeClient;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.ToXContent;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.BaseRestHandler;
import org.elasticsearch.rest.BytesRestResponse;
import org.elasticsearch.rest.RestController;
import org.elasticsearch.rest.RestRequest;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.rest.action.search.RestSearchAction;
import org.elasticsearch.search.SearchHits;

import com.demo.elasticsearch_demo.action.TasteEventAction;
import com.demo.elasticsearch_demo.common.TasteEventRequestBuilder;
import com.demo.elasticsearch_demo.common.TasteEventResponse;

import ding.util.DataProcess;

public class TasteEventRestAction extends BaseRestHandler{

    @Inject
    public TasteEventRestAction(final Settings settings,final RestController restController) {
        super(settings);
        restController.registerHandler(RestRequest.Method.GET, "/_dQuery/{action}", this);
        restController.registerHandler(RestRequest.Method.GET, "/_dQuery", this);
    }

    @Override
    protected RestChannelConsumer prepareRequest(RestRequest request, NodeClient client) throws IOException {
        if (request.method() == POST && !request.hasContent()) {
            return channel -> emitErrorResponse(channel, logger, new IllegalArgumentException("Request body was expected for a POST request."));
        }
        String action = request.param("action");
        if (action != null) {
            logger.info("action: {}; indexName:{}; query:{}; from:{}; size:{};", 
            		action, 
            		request.param("indexName"), 
            		request.param("query"), 
            		request.param("from"), 
            		request.param("size"));
            return createActionResponse(request, client);
        } else {
            logger.info("action: null");
            return createNoActionResponse(request);
        }
    }

    // 一、对应URL为 /_dQuery/{action}
    private RestChannelConsumer createActionResponse(RestRequest request, NodeClient client){
        String indexName = request.param("indexName");
        String queryString = request.param("query", "");
        int fromNum = request.paramAsInt("from", 0);
        int sizeNum = request.paramAsInt("size", 1);

        if(indexName==null || indexName.length()==0){
            logger.info("indexName is null or its length equal to zero");
            return channel -> {
                XContentBuilder builder = channel.newBuilder();
                builder.startObject();
                builder.field("response","indexName is null or its length equal to zero");
                builder.endObject();
                channel.sendResponse(new BytesRestResponse(RestStatus.OK, builder));
            };
        }
        else{
            // 1、通过argumentParser进行数据查询，构造自定义的request请求
            final TasteEventRequestBuilder actionBuilder=new TasteEventRequestBuilder(client);
            QueryBuilder query;
            if(queryString.length()!=0) {
                query = QueryBuilders.queryStringQuery(queryString);
            } else {
                query = QueryBuilders.matchAllQuery();
            }
            SearchRequestBuilder requestBuilder = client.prepareSearch(indexName).setQuery(query)
                    .setFrom(fromNum).setSize(sizeNum).setExplain(true);
            SearchRequest searchRequest=new SearchRequest();
            try {
                RestSearchAction.parseSearchRequest(searchRequest, request, null);
            } catch (IOException e1) {
                logger.debug("Failed to emit response.", e1);
                e1.printStackTrace();
            }
            actionBuilder.setSearchRequest(requestBuilder);
            return channel -> client.execute(TasteEventAction.INSTANSE, actionBuilder.request(),new ActionListener<TasteEventResponse>() {
                @Override
                public void onResponse(TasteEventResponse response) {
                    try{
                        // 2、获得查询数据
                        SearchResponse searchResponse = response.getSearchResponse();
                        SearchHits searchHits = searchResponse.getHits();
                        XContentBuilder builder = channel.newBuilder();
                        builder.startObject();
                        DataProcess.dataToJson(searchHits, fromNum, sizeNum, builder);
                        builder.endObject();
                        channel.sendResponse( new BytesRestResponse(RestStatus.OK, builder));
                    }catch(Exception e){
                        logger.debug("Failed to emit response.", e);
                        onFailure(e);
                    }
                }

                @Override
                public void onFailure(Exception e) {
                    emitErrorResponse(channel, logger, e);
                }

            });
        }
    }


    // 2、对应URL为 /_taste
    private RestChannelConsumer createNoActionResponse(RestRequest request) {

        return channel -> {
            Message message = new Message();
            XContentBuilder builder = channel.newBuilder();
            builder.startObject();
            message.toXContent(builder, request);
            builder.endObject();
            channel.sendResponse(new BytesRestResponse(RestStatus.OK, builder));
        };
    }

    class Message implements ToXContent {
        @Override
        public XContentBuilder toXContent(XContentBuilder builder, Params params) throws IOException {
            // http://127.0.0.1:9200/_dQuery
            return builder.field("response", "This is ES query plugin");
        }
    }
}