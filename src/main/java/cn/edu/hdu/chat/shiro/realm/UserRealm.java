package cn.edu.hdu.chat.shiro.realm;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import cn.edu.hdu.chat.model.User;
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
		User u = userRepository.findByUsername(username);
		if(u == null){
			return null;
		}
		//使用user的registTime作为盐  来加强密码的安全性,当然格式得与生成时一致 
		String salt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(u.getRegistTime());
		System.out.println(salt);
		//如果使用了credentialsMatcher,就返回数据库中存储的密码与salt,shiro会自动根据HashedCredentialsMatcher来匹配当前密码
        return new SimpleAuthenticationInfo(u,u.getPassword(),ByteSource.Util.bytes(salt),getName());
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
	
	
	public static void main(String [] args){
		Calendar c = Calendar.getInstance();
		c.set(Calendar.HOUR_OF_DAY, 10);
		c.set(Calendar.MINUTE, 42);
		c.set(Calendar.SECOND, 22);
		String salt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(c.getTime());
		System.out.println(salt);
		SimpleHash hash = new SimpleHash("md5", "123456", salt, 1);
		System.out.println(hash.toString());
	}
}
