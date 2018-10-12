package com.demo.elasticsearch_demo.plugin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

import org.elasticsearch.action.ActionRequest;
import org.elasticsearch.action.ActionResponse;
import org.elasticsearch.cluster.metadata.IndexNameExpressionResolver;
import org.elasticsearch.cluster.node.DiscoveryNodes;
import org.elasticsearch.common.settings.ClusterSettings;
import org.elasticsearch.common.settings.IndexScopedSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.settings.SettingsFilter;
import org.elasticsearch.plugins.ActionPlugin;
import org.elasticsearch.plugins.Plugin;
import org.elasticsearch.rest.RestController;
import org.elasticsearch.rest.RestHandler;

import com.demo.elasticsearch_demo.action.TasteEventAction;
import com.demo.elasticsearch_demo.action.TransportTasteEventAction;
import com.demo.elasticsearch_demo.rest.TasteEventRestAction;

public class TastePlugin extends Plugin implements ActionPlugin{
public TastePlugin(){	
	}
	
	public String name(){
		return "demo";
	}
	
	public String description(){
		return "recommondation by the demo";
	}
	
	@Override
		public List<ActionHandler<? extends ActionRequest, ? extends ActionResponse>> getActions() {
			// TODO Auto-generated method stub
		    List<ActionHandler<? extends ActionRequest, ? extends ActionResponse>> extra=new ArrayList<>();
			extra.add(new ActionHandler<>(TasteEventAction.INSTANSE, TransportTasteEventAction.class));
		    return extra;
		}
	
	@Override
	public List<RestHandler> getRestHandlers(Settings settings, RestController restController, ClusterSettings clusterSettings, IndexScopedSettings indexScopedSettings, SettingsFilter settingsFilter, IndexNameExpressionResolver indexNameExpressionResolver, Supplier<DiscoveryNodes> nodesInCluster) {
		return Collections.singletonList(new TasteEventRestAction(settings, restController));
	}
	
}
