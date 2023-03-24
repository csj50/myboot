package com.example.web;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.domain.ObjectItem;
import com.example.utils.MinioUtil;

import io.minio.Result;
import io.minio.messages.DeleteError;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(description = "minio测试接口")
@RestController
@RequestMapping("/minio")
public class MinioController {

	@Autowired
	private MinioUtil minioUtil;

	@Value("${minio.url}")
	private String address;

//	@Value("${minio.bucket-name}")
//	private String bucketName;
	
	@ApiOperation("判断bucket是否存在")
	@PostMapping(value="/existBucket")
	public boolean existBucket(String bucketName) {
		return minioUtil.existBucket(bucketName);
	}
	
	@ApiOperation("创建存储bucket")
	@PostMapping(value="/makeBucket")
	public boolean makeBucket(String bucketName) {
		return minioUtil.makeBucket(bucketName);
	}
	
	@ApiOperation("删除存储bucket")
	@PostMapping(value="/removeBucket")
	public boolean removeBucket(String bucketName) {
		return minioUtil.removeBucket(bucketName);
	}

	@ApiOperation("上传文件（浏览器上传）")
	@PostMapping(value="/upload", headers="content-type=multipart/form-data")
	public List<String> upload(@ApiParam(name="attach", value="attach", required=true) @RequestPart("attach") MultipartFile file, String bucketName) {

		//在swagger测试，只能传单个文件
		List<String> names = minioUtil.upload(new MultipartFile[] {file}, bucketName);
		//List<String> names = minioUtil.upload(file, bucketName);
		
		return names;
	}
	
	@ApiOperation("下载文件（适用于浏览器下载）")
	@PostMapping(value="/download")
	public ResponseEntity<byte[]> download(String fileName, String bucketName) {
		return minioUtil.download(fileName, bucketName);
	}
	
	@ApiOperation("下载文件（适用于浏览器下载，方式二）")
	@PostMapping(value="/download2")
	public void download2(String fileName, String bucketName, HttpServletResponse res) {
		minioUtil.download(fileName, bucketName, res);
	}
	
	@ApiOperation("查看bucket文件对象")
	@PostMapping(value="/listObjects")
	public List<ObjectItem> listObjects(String bucketName) {
		return minioUtil.listObjects(bucketName);
	}
	
	@ApiOperation("批量删除文件对象")
	@PostMapping(value="/removeObjects")
	public Iterable<Result<DeleteError>> removeObjects(String bucketName, @RequestParam("fileNames") List<String> fileNames) {
		return minioUtil.removeObjects(bucketName, fileNames);
	}

}