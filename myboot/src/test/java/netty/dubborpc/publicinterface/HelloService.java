package netty.dubborpc.publicinterface;

/**
 * 接口，服务提供方和服务消费方都需要
 * @author user
 *
 */
public interface HelloService {

	public String hello(String msg);
}
