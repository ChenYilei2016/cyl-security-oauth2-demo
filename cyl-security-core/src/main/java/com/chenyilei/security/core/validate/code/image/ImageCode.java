/**
 * 
 */
package com.chenyilei.security.core.validate.code.image;

import com.chenyilei.security.core.validate.code.ValidateCode;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.time.LocalDateTime;


/**
 * 图片验证码
 * @author zhailiang
 *
 */
public class ImageCode extends ValidateCode {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6020470039852318468L;
	
	private Image image;
	
	public ImageCode(Image image, String code, int expireIn){
		super(code, expireIn);
		this.image = image;
	}
	
	public ImageCode(Image image, String code, LocalDateTime expireTime){
		super(code, expireTime);
		this.image = image;
	}
	
	public Image getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

}
