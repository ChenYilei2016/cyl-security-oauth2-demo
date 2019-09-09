package com.chenyilei.demo1.config;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * --添加相关注释--
 *
 * @author chenyilei
 * @email 705029004@qq.com
 * @date 2019/05/27- 19:49
 */
@Documented
@Constraint(
        validatedBy = {MyValid.myValidConstraintValidator.class}
)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
public @interface MyValid {

    String message() default "{javax.validation.constraints.NotBlank.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
    class myValidConstraintValidator implements ConstraintValidator<MyValid,Object> {

        @Override
        public boolean isValid(Object value, ConstraintValidatorContext context) {
            return !value.equals("1");
        }
    }

}
