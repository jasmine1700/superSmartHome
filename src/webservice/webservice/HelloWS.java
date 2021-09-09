package webservice;

/*
 * SEI
 */
@webService
public interface HelloWS {
	/*
	 * 客户端应用传来name，返回客户端一个String
	 */
	public String sayHello(String name);
}
