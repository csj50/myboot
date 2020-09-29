package test.selector;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TestSelector {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(UserConfig.class);
		UserBean userBean = context.getBean(UserBean.class);
		RoleBean roleBean = context.getBean(RoleBean.class);
		
		System.out.println(userBean.toString());
		System.out.println(roleBean.toString());
		
		context.close();
	}
}
