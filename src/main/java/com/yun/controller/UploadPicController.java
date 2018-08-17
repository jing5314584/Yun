package com.yun.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.yun.config.OSSUploadUtil;

@RestController
@RequestMapping(value = "/Yun")
public class UploadPicController {

	private Logger logger = LoggerFactory.getLogger(UploadPicController.class);

	/**
	 * 图片上传
	 * 
	 * @param file
	 * @return
	 * 
	 * @author ZHANGJL
	 * @dateTime 2018-02-24 15:49:48
	 */
	@RequestMapping(value = "/upload")
	public String uploadPic(MultipartFile file) {
		String imgUrl = "";
		OSSUploadUtil ossClient = new OSSUploadUtil();
		try {
			ossClient.init();
			String name = ossClient.uploadImg2Oss(file);
			logger.info(name);
			imgUrl = ossClient.getUrl(name);
			ossClient.destory();
			logger.info(imgUrl);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JSONObject.toJSONString(imgUrl);
	}

}