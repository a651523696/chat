package cn.edu.hdu.chat.shiro.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import cn.edu.hdu.chat.repository.UserRepository;

public class UserRealm extends AuthorizingRealm {
	@Autowired
	private UserRepository userRepository;
	/**
	 * 认证方法
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		String username = (String)token.getPrincipal();
		System.out.println(username);
		String password = new String((char[])token.getCredentials());
		
		User u = userRepository.findByUsername(username);
        if(!"123".equals(password)) {  
            throw new IncorrectCredentialsException(); //如果密码错误  
        }  
        return new SimpleAuthenticationInfo(username,password,getName());
	}
	//授权方法      在检查是否具有shiro权限的时候调用 如果没有配置缓存器的话，每次检查都会调用
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		if(principals == null){
			return null;
		}
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.addStringPermission("user:add");
		return info;
	}

	
	
	public void clearCachedAuthorizationInfo(PrincipalCollection principals){
		super.clearCachedAuthorizationInfo(principals);
	}
}
