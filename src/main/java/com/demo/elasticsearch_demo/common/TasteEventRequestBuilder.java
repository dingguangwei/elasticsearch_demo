package com.demo.elasticsearch_demo.common;

import org.elasticsearch.action.Action;
import org.elasticsearch.action.ActionRequestBuilder;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.client.ElasticsearchClient;
import org.elasticsearch.common.bytes.BytesReference;
import org.elasticsearch.common.xcontent.NamedXContentRegistry;

import com.demo.elasticsearch_demo.action.TasteEventAction;

public class TasteEventRequestBuilder extends ActionRequestBuilder<TasteEventRequest, TasteEventResponse, TasteEventRequestBuilder> {

	public TasteEventRequestBuilder(ElasticsearchClient client){
		super(client, TasteEventAction.INSTANSE, new TasteEventRequest());
	}
    public TasteEventRequestBuilder setSearchRequest(SearchRequestBuilder builder){
    	 super.request.setSearchRequest(builder);
    	 return this;
    }
	public TasteEventRequestBuilder setSearchRequest(SearchRequest request){
		 super.request.setSearchRequest(request);
		 return this;
	}
	public TasteEventRequestBuilder setPrefix(String string){
		request.setPrefix(string);
		return this;
	}
	
}
