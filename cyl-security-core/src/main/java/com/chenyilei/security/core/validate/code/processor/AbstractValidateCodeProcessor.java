/**
 * 
 */
package com.chenyilei.security.core.validate.code.processor;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import com.chenyilei.security.core.support.SecurityConstants;
import com.chenyilei.security.core.validate.code.*;

import com.chenyilei.security.core.validate.code.image.ImageCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * 抽象的图片验证码处理器
 * 
 * @author zhailiang
 *
 */
public abstract class AbstractValidateCodeProcessor<C extends ValidateCode> implements ValidateCodeProcessor {

	/**
	 * 收集系统中所有的 {@link ValidateCodeGenerator} 接口的实现。
	 * 
	 * 这是Spring开发的常见技巧，叫做定向查找（定向搜索）
	 * 
	 * Spring启动时，会查找容器中所有的ValidateCodeGenerator接口的实现，并把Bean的名字作为key，放到map中
	 * 在我们这个系统中，ValidateCodeGenerator接口有两个实现，一个是ImageCodeGenerator，一个是SmsCodeGenerator，系统启动完成后，这个map中就会有2个bean，key分别是bean的名字
	 * 
	 * 生成验证码的时候，会根据请求的不同（有一个type值区分是获取短信验证码还是图片验证码），来获取短信验证码的生成器或者图形验证码的生成器
	 */
	@Autowired
	private Map<String, ValidateCodeGenerator> validateCodeGenerators;

	@Autowired(required = false)
	ValidateCodeRepository validateCodeRepository;

	public AbstractValidateCodeProcessor( ) {

	}
	public AbstractValidateCodeProcessor(ValidateCodeRepository validateCodeRepository) {
		this.validateCodeRepository = validateCodeRepository;
	}

	//	@Autowired
//	private ValidateCodeRepository validateCodeRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.imooc.security.core.validate.code.ValidateCodeProcessor#create(org.
	 * springframework.web.context.request.ServletWebRequest)
	 */
	@Override
	public void create(ServletWebRequest request) throws Exception {
		// 生成
		C validateCode = generate(request);
		// 放到session
		save(request, validateCode);
		// 发送
		send(request, validateCode);
	}

	/**
	 * 生成校验码
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private C generate(ServletWebRequest request) {
		//  /code/image  ;  /code/sms
		String requestURI = request.getRequest().getRequestURI();
		String prefix = StringUtils.substringAfter( requestURI,"/code/");
		String generatorName = prefix + "CodeGenerator";
		ValidateCodeGenerator validateCodeGenerator = validateCodeGenerators.get(generatorName);

		Assert.notNull(validateCodeGenerator,"找不到对应的验证码生成器!");

		return (C)validateCodeGenerator.generator();
	}

	/**
	 * 保存校验码
	 * 
	 * @param request
	 * @param validateCode
	 */
	private void save(ServletWebRequest request, C validateCode) {
		String requestURI = request.getRequest().getRequestURI();
		String prefix = StringUtils.substringAfter( requestURI,"/code/");
		if(validateCodeRepository == null) {
			//往session放入数据
			SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();
			sessionStrategy.setAttribute(request, ValidateCodeController.CODE_SESSION_KEY + prefix, validateCode);
		}else{
			validateCodeRepository.save(request,validateCode);
		}
	}

	/**
	 * 发送校验码，由子类实现
	 * 
	 * @param request
	 * @param validateCode
	 * @throws Exception
	 */
	protected abstract void send(ServletWebRequest request, C validateCode) throws Exception;


	private ValidateCodeType getType (HttpServletRequest request){
		ValidateCodeType validateCodeType = ValidateCodeType.valueOf(StringUtils.substringAfterLast(request.getRequestURI(), "/").toUpperCase());
		Assert.notNull(validateCodeType,"请求有错!");
		return validateCodeType;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void validate(ServletWebRequest arequest) {
		System.err.println("--abstract校验验证码!--");
		HttpServletRequest request = arequest.getRequest();
		SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();
//		ValidateCodeType type = getType(request);
//		type.getParamNameOnValidate()

		//用户传入验证码
		String checkCode = request.getParameter("imageCode");
		//Session
		ImageCode sessionImageCode = (ImageCode)sessionStrategy.getAttribute(new ServletWebRequest(request), ValidateCodeController.CODE_SESSION_KEY);
		if(StringUtils.isBlank(checkCode)){
			throw new ValidateCodeException("验证码不能为空!");
		}
		if(ObjectUtil.isNull(sessionImageCode)){
			throw new ValidateCodeException("Session中没有验证码!");
		}

		if(sessionImageCode.getExpireTime().isBefore(LocalDateTime.now())){
			throw new ValidateCodeException("验证码已经过期!");
		}
		if(!StringUtils.equalsIgnoreCase(sessionImageCode.getCode(),checkCode)){
			throw new ValidateCodeException("验证码输入错误!");
		}

		if(validateCodeRepository == null)
			sessionStrategy.removeAttribute(new ServletWebRequest(request),ValidateCodeController.CODE_SESSION_KEY);
		else{
			validateCodeRepository.remove(new ServletWebRequest(request));
		}
	}

}
