package com.example.web;

import java.util.HashMap;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.example.annotation.MyCrossOrigin;

@RestController
public class CORSController {

	@RequestMapping("/hello2")
	@MyCrossOrigin
	public HashMap<String, String> hello2() {
		HashMap<String, String> result = new HashMap<>();
		result.put("code", "200");
		result.put("msg", "ok");
		
//		HttpServletResponse response = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getResponse();
//		response.setHeader("Access-Control-Allow-Origin", "*");
		
		return result;
	}
}
