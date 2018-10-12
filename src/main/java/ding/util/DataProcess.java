package ding.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.search.SearchHits;

public class DataProcess {
	public static void dataToJson(SearchHits searchHits, int fromNum, int sizeNum, XContentBuilder builder) throws IOException {
		builder.startArray("response");
		List<String> attributeNameList = new ArrayList<>();
		Map<String, Object> firstLineMap = searchHits.getHits()[fromNum].getSource();
		attributeNameList.addAll(firstLineMap.keySet());
		
		for(int i=fromNum; i<fromNum+sizeNum; i++) {
			builder.startObject();
			Map<String, Object> map = searchHits.getHits()[i].getSource();
			for(int j=0; j<attributeNameList.size(); j++){
				String attributeName = attributeNameList.get(j);
				try{
					// 如果数据是数字类型，json中就填写数字类型
					double value = Double.parseDouble(map.get(attributeName).toString());
					builder.field(attributeName, value);
				}
				catch(NumberFormatException e){
					builder.field(attributeName, map.get(attributeName).toString());
				}
			}
			builder.endObject();
        }
		builder.endArray();
		
	}
}
