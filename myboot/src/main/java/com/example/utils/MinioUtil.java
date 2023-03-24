package com.example.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.web.multipart.MultipartFile;

import com.example.domain.ObjectItem;

import io.minio.BucketExistsArgs;
import io.minio.GetObjectArgs;
import io.minio.GetObjectResponse;
import io.minio.ListObjectsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveBucketArgs;
import io.minio.RemoveObjectsArgs;
import io.minio.Result;
import io.minio.messages.DeleteError;
import io.minio.messages.DeleteObject;
import io.minio.messages.Item;

@Component
public class MinioUtil {

	@Autowired
	private MinioClient minioClient;

	/**
	 * 判断bucket是否存在
	 */
	public boolean existBucket(String bucketName) {
		try {
			boolean exists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());

			return exists;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * 创建存储bucket
	 * 
	 * @param bucketName 存储bucket名称
	 * @return Boolean
	 */
	public Boolean makeBucket(String bucketName) {
		try {
			minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	/**
	 * 删除存储bucket
	 * 
	 * @param bucketName 存储bucket名称
	 * @return Boolean
	 */
	public Boolean removeBucket(String bucketName) {
		try {
			minioClient.removeBucket(RemoveBucketArgs.builder().bucket(bucketName).build());
		} catch (Exception e) {
			e.printStackTrace();

			return false;
		}

		return true;
	}

	/**
	 * description: 上传文件（浏览器上传）
	 *
	 * @param multipartFile
	 * @return : java.lang.String
	 * 
	 */
	public List<String> upload(MultipartFile[] multipartFile, String bucketName) {
		List<String> names = new ArrayList<>(multipartFile.length);

		for (MultipartFile file : multipartFile) {
			String fileName = file.getOriginalFilename();
			String[] split = fileName.split("\\.");

			if (split.length > 1) {
				fileName = split[0] + "_" + System.currentTimeMillis() + "." + split[1];
			} else {
				fileName = fileName + System.currentTimeMillis();
			}
			InputStream in = null;
			try {

				in = file.getInputStream();
				minioClient.putObject(PutObjectArgs.builder().bucket(bucketName).object(fileName)
						.stream(in, in.available(), -1).contentType(file.getContentType()).build());
			} catch (Exception e) {
				e.printStackTrace();
			} finally {

				if (in != null) {
					try {
						in.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			names.add(fileName);
		}

		return names;
	}

	/**
	 * 下载文件（适用于浏览器下载）
	 *
	 * @param fileName
	 * @return : org.springframework.http.ResponseEntity<byte []>
	 */
	public ResponseEntity<byte[]> download(String fileName, String bucketName) {
		ResponseEntity<byte[]> responseEntity = null;
		InputStream in = null;
		ByteArrayOutputStream out = null;
		try {

			in = minioClient.getObject(GetObjectArgs.builder().bucket(bucketName).object(fileName).build());
			out = new ByteArrayOutputStream();
			IOUtils.copy(in, out);
			// 封装返回值
			byte[] bytes = out.toByteArray();
			HttpHeaders headers = new HttpHeaders();
			try {
				//告示浏览器文件的打开方式是下载
				headers.add("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "utf-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			headers.setContentLength(bytes.length);
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			headers.setAccessControlExposeHeaders(Arrays.asList("*"));
			responseEntity = new ResponseEntity<byte[]>(bytes, headers, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {

				if (in != null) {
					try {
						in.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return responseEntity;
	}

	/**
	 * 查看文件对象
	 * 
	 * @param bucketName 存储bucket名称
	 * @return 存储bucket内文件对象信息
	 */
	public List<ObjectItem> listObjects(String bucketName) {
		Iterable<Result<Item>> results = minioClient.listObjects(ListObjectsArgs.builder().bucket(bucketName).build());
		List<ObjectItem> objectItems = new ArrayList<>();
		try {

			for (Result<Item> result : results) {
				Item item = result.get();
				ObjectItem objectItem = new ObjectItem();
				objectItem.setObjectName(item.objectName());
				objectItem.setSize(item.size());
				objectItems.add(objectItem);
			}
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}

		return objectItems;
	}

	/**
	 * 批量删除文件对象
	 * 
	 * @param bucketName 存储bucket名称
	 * @param objects    对象名称集合
	 */
	public Iterable<Result<DeleteError>> removeObjects(String bucketName, List<String> objects) {
		List<DeleteObject> dos = objects.stream().map(e -> new DeleteObject(e)).collect(Collectors.toList());
		Iterable<Result<DeleteError>> results = minioClient
				.removeObjects(RemoveObjectsArgs.builder().bucket(bucketName).objects(dos).build());

		return results;
	}
	
	/**
     * 文件下载（适用于浏览器下载，方式二）
     * @param fileName 文件名称
     * @param bucketName 桶名称
     * @param res response
     * @return 
     */
    public void download(String fileName, String bucketName, HttpServletResponse res) {
        GetObjectArgs objectArgs = GetObjectArgs.builder().bucket(bucketName)
                .object(fileName).build();
        try (GetObjectResponse response = minioClient.getObject(objectArgs)){
            byte[] buf = new byte[1024];
            int len;
            try (FastByteArrayOutputStream os = new FastByteArrayOutputStream()){
                while ((len=response.read(buf))!=-1){
                    os.write(buf,0,len);
                }
                os.flush();
                byte[] bytes = os.toByteArray();
                res.setCharacterEncoding("utf-8");
                // 设置强制下载不打开
                // res.setContentType("application/force-download");
                res.setContentLength(bytes.length);
                res.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
                //告示浏览器文件的打开方式是下载
                res.addHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(fileName, "utf-8"));
                try (ServletOutputStream stream = res.getOutputStream()) {
                    stream.write(bytes);
                    stream.flush();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
