package com.example.annotation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PhoneValidator implements ConstraintValidator<Phone, String>{

	private static final Pattern PHONE_PATTERN = Pattern.compile(
			"^1([38][0-9]|4[579]|5[0-3,5-9]|6[6]|7[0135678]|9[89])\\d{8}$");
	
	@Override
	public void initialize(Phone constraintAnnotation) {
		
	}
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value == null || value.length() == 0) {
			return true;
		}
		Matcher m = PHONE_PATTERN.matcher(value);
		return m.matches();
	}
	

}
