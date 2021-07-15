package com.example.base;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Constants {

	public static final String HOT_PRODUCT = "hot:product";
	public static final String HOT_PRODUCT_A = "hot:product:a";
	public static final String HOT_PRODUCT_B = "hot:product:b";
	
	public static final String CACHE_PV_LIST = "pv:list";
	public static final String CACHE_PV_ARTICLE = "pv:article:";
	public static final Map<Long, Map<Integer, Integer>> PV_MAP = new ConcurrentHashMap<Long, Map<Integer, Integer>>();
}
