package com.demo.elasticsearch_demo;

import java.net.InetSocketAddress;

import org.apache.http.HttpResponse;

import org.apache.http.client.methods.HttpGet;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.common.network.NetworkAddress;

public class TasteEventRestTest extends TastePluginTest {
	public void test_recommended_items_from_user() throws Exception {
		try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
			InetSocketAddress[] endpoint = cluster().httpAddresses();
			this.restBaseUrl = "http://" + NetworkAddress.format(endpoint[0]);
			String s1 = "/_dQuery/para?indexName=iris-2&from=0&size=5&query=";

			HttpGet get=new HttpGet(restBaseUrl + s1);
			System.out.println("post请求已发送——1");
			HttpResponse response = httpClient.execute(get);
			System.out.println("post请求已发送——2");

			if(response.getStatusLine().getStatusCode()==200){//如果状态码为200,就是正常返回
				System.out.println("\n返回 JSON ：");
				//得到返回的字符串
				String result=EntityUtils.toString(response.getEntity());
				System.out.println(result);
			}
			else{
				System.out.println("\n返回JSON出错！");
			}

		}
       
   }
}


