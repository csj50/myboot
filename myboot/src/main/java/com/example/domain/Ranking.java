package com.example.domain;

import java.util.List;

import lombok.Data;

/**
 * 榜单的实体类
 * @author user
 *
 */
@Data
public class Ranking {

	//榜单id
	private int id;
	
	//榜单名称
	private String name;
	
	//榜单内容
	private List<String> contents;
	
}
