package com.alex10011.example.service.command;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.alex10011.example.vo.RspBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.alex10011.example.vo.RspBean;

//辅助解决返回结果嵌套对象的获取 
//前提是服务提供方采用我们通用的包裹类RspBean的时候可使用，否则自行拆解返回包
public class RestTemplateUtil {

	// get方式调用接口，得到返回包裹中的data部分
	protected static <T> T getRestData(RestTemplate restTemplate, String url, Map<String, String> param,
			TypeReference<?> type) {
		RspBean<T> res = getRest(restTemplate, url, param, type);
		return res.getData();
	}

	// get方式调用接口，得到返回包裹
	protected static <T> T getRest(RestTemplate restTemplate, String url, Map<String, String> param,
			TypeReference<?> type) {
		return restTemplateGet(restTemplate, url, param, type);
	}

	// post方式调用接口，得到返回包裹中的data部分
	protected static <T> T postRestData(RestTemplate restTemplate, String url, Map<String, String> param,
			TypeReference<?> type, MediaType media) {
		RspBean<T> res = postRest(restTemplate, url, param, type, media);
		return res.getData();
	}

	// post方式调用接口，得到返回包裹
	protected static <T> T postRest(RestTemplate restTemplate, String url, Map<String, String> param,
			TypeReference<?> type, MediaType media) {
		return restTemplatePost(restTemplate, url, param, type, media);
	}

	private static <T> T restTemplateGet(RestTemplate restTemplate, String url, Map<String, String> param,
			TypeReference<?> type) {
		if (param != null && param.size() > 0 && !url.contains("{")) {
			// get方式url需要补全为 xxx/xxxx?name1={name1} 形式
			StringBuilder stb = new StringBuilder();
			Set<Entry<String, String>> set = param.entrySet();
			for (Entry<String, String> entry : set) {
				if (stb.length() > 0) {
					stb.append("&");
				}
				stb.append(entry.getKey() + "={" + entry.getKey() + "}");
			}
			url += "?" + stb.toString();
		}

		ResponseEntity<String> results = restTemplate.exchange(url, HttpMethod.GET, null, String.class, param);
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		try {
			return mapper.readValue(results.getBody(), type);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	private static <T> T restTemplatePost(RestTemplate restTemplate, String url, Map<String, String> param,
			TypeReference<?> type, MediaType media) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		HttpHeaders headers = new HttpHeaders();
		// 表单 MediaType.APPLICATION_FORM_URLENCODED
		// body中传输寄送 MediaType.APPLICATION_JSON
		headers.setContentType(media);

		HttpEntity requestEntity = null;
		if (media == MediaType.APPLICATION_JSON || media == MediaType.APPLICATION_JSON_UTF8) {
			String requestJson = "";
			try {
				requestJson = mapper.writeValueAsString(param);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			requestEntity = new HttpEntity<String>(requestJson, headers);
		} else {
			// 封装参数，不要替换为Map与HashMap，否则参数无法传递
			MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
			if (param != null && param.size() > 0) {
				Set<Entry<String, String>> set = param.entrySet();
				for (Entry<String, String> entry : set) {
					params.add(entry.getKey(), entry.getValue());
				}
			}
			requestEntity = new HttpEntity<MultiValueMap<String, String>>(params, headers);
		}

		ResponseEntity<String> results = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
		if (results.getBody() != null) {
			try {
				return mapper.readValue(results.getBody(), type);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

}
