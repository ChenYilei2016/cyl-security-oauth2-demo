/**
 * 
 */
package com.chenyilei.security.core.validate.code.processor;

import cn.hutool.core.img.ImgUtil;
import com.chenyilei.security.core.validate.code.image.ImageCode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 图片验证码处理器
 * 
 * @author zhailiang
 *
 */
public class ImageCodeProcessor extends AbstractValidateCodeProcessor<ImageCode> {

	/**
	 * 发送图形验证码，将其写到响应中
	 */
	@Override
	protected void send(ServletWebRequest request, ImageCode imageCode) throws Exception {
		ImgUtil.writeJpg(imageCode.getImage(), request.getResponse().getOutputStream());
	}

}
