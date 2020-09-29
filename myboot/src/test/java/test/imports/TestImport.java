package test.imports;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 这里只初始化spring ioc容器，并没有启动springboot
 * @author User
 *
 */
public class TestImport {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(UserService.class);
		UserService userService = context.getBean(UserService.class);
		UserBean userBean = context.getBean(UserBean.class);
		
		System.out.println(userService.toString());
		System.out.println(userBean.toString());
		
		context.close();
	}
}
