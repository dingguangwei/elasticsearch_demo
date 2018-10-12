package com.demo.elasticsearch_demo.common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.action.ActionResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.xcontent.StatusToXContentObject;
import org.elasticsearch.common.xcontent.ToXContent;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.rest.RestStatus;

import com.demo.elasticsearch_demo.action.Preconditions;

import static org.elasticsearch.rest.RestStatus.OK;

public class TasteEventResponse extends ActionResponse implements StatusToXContentObject{
    private SearchResponse searchResponse;
	public TasteEventResponse(SearchResponse searchResponse) {
		// TODO Auto-generated constructor stub
		this.searchResponse=Preconditions.checkNotNull(searchResponse);
	}
	public TasteEventResponse(){
		
	}
	public SearchResponse getSearchResponse() {
        return searchResponse;
    }
	

	@Override
	public XContentBuilder toXContent(XContentBuilder builder, Params params) throws IOException {
		// TODO Auto-generated method stub
		if (searchResponse != null) {
            searchResponse.innerToXContent(builder, ToXContent.EMPTY_PARAMS);
        }
		//builder.startObject();
		//builder.endObject();
		return builder;
	}

	@Override
	public RestStatus status() {
		// TODO Auto-generated method stub
		return OK;
	}	
}
