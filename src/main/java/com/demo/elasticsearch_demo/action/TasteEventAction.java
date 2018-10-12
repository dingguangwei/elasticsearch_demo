package com.demo.elasticsearch_demo.action;

import org.elasticsearch.action.Action;
import org.elasticsearch.client.ElasticsearchClient;

import com.demo.elasticsearch_demo.common.TasteEventRequest;
import com.demo.elasticsearch_demo.common.TasteEventRequestBuilder;
import com.demo.elasticsearch_demo.common.TasteEventResponse;

public class TasteEventAction extends Action<TasteEventRequest, TasteEventResponse, TasteEventRequestBuilder>{
	public static final String NAME="tastevent";
	public static final TasteEventAction INSTANSE = new TasteEventAction();

	protected TasteEventAction() {
		super(NAME);
		// TODO Auto-generated constructor stub
	}

	
	@Override
	public TasteEventRequestBuilder newRequestBuilder(ElasticsearchClient client) {
		// TODO Auto-generated method stub
		return new TasteEventRequestBuilder(client);
	}

	@Override
	public TasteEventResponse newResponse() {
		// TODO Auto-generated method stub
		return new TasteEventResponse();
	}
}
