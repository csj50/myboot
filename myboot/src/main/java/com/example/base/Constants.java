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
	
	public static final String BLACKLIST_KEY = "blacklist";
	
	public static final String PRIZE_KEY = "prize";
	public static final String TT_PRIZE_KEY = "tt_prize";
	
	public static final String QUN_RANDOM_KEY = "qun_random";
	
	public static final String RANKING_LIST = "ranking";
	
	public static final String LIKE_KEY = "like:";
	
	public static final String HOUR_KEY = "{rank}:hour:";
	public static final String DAY_KEY = "{rank}:day";
	public static final String WEEK_KEY = "{rank}:week";
	public static final String MONTH_KEY = "{rank}:month";
	
	public static final String SCENERY_KEY = "scenery";
	
}
