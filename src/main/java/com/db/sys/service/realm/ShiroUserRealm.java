package com.db.sys.service.realm;

import com.db.sys.dao.SysMenuDao;
import com.db.sys.dao.SysRoleMenuDao;
import com.db.sys.dao.SysUserDao;
import com.db.sys.dao.SysUserRoleDao;
import com.db.sys.entity.SysMenu;
import com.db.sys.entity.SysUser;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ShiroUserRealm extends AuthorizingRealm {

    @Autowired
    private SysUserDao sysUserDao;
    @Autowired
    private SysUserRoleDao sysUserRoleDao;
    @Autowired
    private SysMenuDao sysMenuDao;
    @Autowired
    private SysRoleMenuDao sysRoleMenuDao;
    /**
     * 设置凭证匹配器(与用户添加操作使用相同的加密算法)
     */
    @Override
    public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
            //构建凭证匹配对象
            HashedCredentialsMatcher cMatcher=
                    new HashedCredentialsMatcher();
            //设置加密算法
            cMatcher.setHashAlgorithmName("MD5");
            //设置加密次数
            cMatcher.setHashIterations(1);
            super.setCredentialsMatcher(cMatcher);
    }

    /**
     * 通过此方法完成认证数据的获取及封装,系统
     * 底层会将认证数据传递认证管理器，由认证
     * 管理器完成认证操作。
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(
            AuthenticationToken token)
            throws AuthenticationException {
        //1.获取用户名(用户页面输入)
        UsernamePasswordToken upToken=
                (UsernamePasswordToken)token;
        String username=upToken.getUsername();
        //2.基于用户名查询用户信息
        SysUser user= sysUserDao.findUserByUserName(username);
        //3.判定用户是否存在
        if(user==null){
            throw new UnknownAccountException();
        }
        //4.判定用户是否已被禁用。
        if(user.getValid()==0){
            throw new LockedAccountException();
        }
        //5.封装用户信息
        ByteSource credentialsSalt= ByteSource.Util.bytes(user.getSalt());
        //记住：构建什么对象要看方法的返回值
        SimpleAuthenticationInfo info= new SimpleAuthenticationInfo(user,user.getPassword(),credentialsSalt,getName());
        //6.返回封装结果
        return info;//返回值会传递给认证管理器(后续
        //认证管理器会通过此信息完成认证操作)
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("doGetAuthorizationInfo");
        SysUser user = (SysUser) principals.getPrimaryPrincipal();
        Integer userId = user.getId();
        List<Integer> roleIds = sysUserRoleDao.findRoleIdsByUserId(userId);
        if (roleIds==null||roleIds.size()==0){
            throw new AuthorizationException();
        }
        Integer[] arry={};
        List<Integer> menuIds = sysRoleMenuDao.findMenuIdsByRoleIds(roleIds.toArray(arry));
        if (menuIds==null||menuIds.size()==0){
            throw new AuthorizationException();
        }
        List<String> permissions = sysMenuDao.findPermissions(menuIds.toArray(arry));
        Set<String> set=new HashSet<>();
        for (String per:permissions){
            if (!StringUtils.isEmpty(per)){
                set.add(per);
            }
        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setStringPermissions(set);
        return info;
    }
}
