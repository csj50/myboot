package com.example.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = PhoneValidator.class) //指定注解的实现类
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Phone {
	
	//校验失败的提示信息
	String message() default "请输入正确的手机号码";
	
	Class<?>[] groups() default {};
	
	Class <? extends Payload>[] payload() default {};
	
	//定义List，为了让Bean的一个属性上可以添加多套规则
	@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, 
		ElementType.CONSTRUCTOR, ElementType.PARAMETER})
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	@interface List{
		Phone[] value();
	}
}
