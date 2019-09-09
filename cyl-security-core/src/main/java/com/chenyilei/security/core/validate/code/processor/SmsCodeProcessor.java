/**
 *
 */
package com.chenyilei.security.core.validate.code.processor;
import com.chenyilei.security.core.support.SecurityConstants;
import com.chenyilei.security.core.validate.code.ValidateCode;
import com.chenyilei.security.core.validate.code.sms.SmsSenderInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 短信验证码处理器
 *
 * @author zhailiang
 *
 */
public class SmsCodeProcessor extends AbstractValidateCodeProcessor<ValidateCode> {

	/**
	 * 短信验证码发送器
	 */
	@Autowired
	private SmsSenderInterface smsSenderInterface;

    @Override
	protected void send(ServletWebRequest request, ValidateCode validateCode) throws Exception {
		String paramName = SecurityConstants.DEFAULT_PARAMETER_NAME_MOBILE;
		String mobile = ServletRequestUtils.getRequiredStringParameter(request.getRequest(), paramName);
		//mobile = "13362199093";
        smsSenderInterface.send(mobile, validateCode);
	}

}
