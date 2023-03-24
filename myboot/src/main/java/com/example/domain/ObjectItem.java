package com.example.domain;

import lombok.Data;

/**
 * minio文件对象的实体类
 */
@Data
public class ObjectItem {

	private String objectName;
	
    private Long size;
}
