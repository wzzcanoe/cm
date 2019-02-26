package com.ma.cm.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.ma.cm.entity.API;

@RestController
@RequestMapping("/v1/apis")
public class APIController {

	@Autowired
	private RequestMappingHandlerMapping handlerMapping;

	@RequestMapping(method = RequestMethod.GET)
	public List<API> getAllApi() {
		List<API> apis = new ArrayList<>();
		Map<RequestMappingInfo, HandlerMethod> map = this.handlerMapping.getHandlerMethods();
		Set<RequestMappingInfo> set = map.keySet();
		for (RequestMappingInfo info : set) {
			PatternsRequestCondition patternsCondition = info.getPatternsCondition();
			RequestMethodsRequestCondition methodsCondition = info.getMethodsCondition();
			apis.add(new API(patternsCondition.toString(), methodsCondition.toString()));
		}
		return apis;
	}
}
