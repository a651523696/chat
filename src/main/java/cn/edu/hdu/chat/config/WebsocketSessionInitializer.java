package cn.edu.hdu.chat.config;

import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer;
/**
 * The first step is to extend AbstractHttpSessionApplicationInitializer. 
 * This ensures that the Spring Bean by the name springSessionRepositoryFilter 
 * is registered with our Servlet Container for every request.
 * AbstractHttpSessionApplicationInitializer also provides a mechanism 
 * to easily ensure Spring loads our Config
 * @author hasee
 *
 */
public class WebsocketSessionInitializer extends AbstractHttpSessionApplicationInitializer {

	public WebsocketSessionInitializer(Class<?>... configurationClasses) {
		super(WebSocketConfig.class);
	}
			
}
