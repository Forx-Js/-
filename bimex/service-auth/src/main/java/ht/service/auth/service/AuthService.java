package ht.service.auth.service;

import ht.framework.exception.LoginException;
import ht.framework.exception.ValidateException;
import ht.framework.util.TimeSource;
import ht.framework.util.Validator;
import ht.service.auth.dao.DepartmentDao;
import ht.service.auth.dao.MenuItemDao;
import ht.service.auth.dao.UserDao;
import ht.service.auth.dao.UserPassDao;
import ht.service.auth.dao.UserRoleDao;
import ht.service.auth.po.DbSession;
import ht.service.auth.po.Department;
import ht.service.auth.po.User;
import ht.service.auth.po.UserPassPo;
import ht.service.auth.po.UserPo;
import ht.service.auth.po.UserSecurityToken;
import ht.service.auth.view.MenuItemView;
import ht.service.common.service.SerialNoService;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zeng.ling on 2017/11/18.
 */
@Service
public class AuthService {
    private Logger logger = LoggerFactory.getLogger(AuthService.class);

    @Resource
    private MenuItemDao menuItemDao;
    @Resource
    private UserDao userDao;
    @Resource
    private DepartmentDao departmentDao;
    @Resource
    private PasswordRule passwordRule;
    @Resource
    private TimeSource timeSource;
    @Resource
    private UserPassDao userPassDao;
    @Resource
    private SerialNoService serialNoService;
    @Resource
    private UserRoleDao userRoleDao;

    public List<MenuItemView> getDepartmentAvailableMenuItems(List<String> departments) {
        List<MenuItemView> all = new ArrayList<>();
        for (String departmentId : departments) {
            List<MenuItemView> menuItems = getDepartmentAvailableMenuItems(departmentId);
            all.addAll(menuItems);
        }
        return all;
    }

    public List<MenuItemView> getDepartmentAvailableMenuItems(String departmentId) {
        List<MenuItemView> menuItems = menuItemDao.findDepartmentVisibleMenuItems(departmentId);
        List<MenuItemView> wellFormed = makeTree(menuItems);
        return wellFormed;
    }

    private List<MenuItemView> makeTree(List<MenuItemView> menuItems) {
        List<MenuItemView> wellFormed = new ArrayList<>();
        Map<String, MenuItemView> map = new HashMap<>(256);
        for (MenuItemView item : menuItems) {
            boolean isTopLevelMenu = StringUtils.equals(item.getParentMenuItemId(), "ROOT");
            if (isTopLevelMenu) {
                wellFormed.add(item);
                map.put(item.getMenuItemId(), item);
            } else {
                MenuItemView parent = map.get(item.getParentMenuItemId());
                if (parent != null) {
                    parent.sub().add(item);
                    map.put(item.getMenuItemId(), item);
                } else {
                    // 找不到 parent 说明 parent 可能被设置 visible = false
                }
            }
        }
        return wellFormed;
    }

//    public User findUser(String userId) {
//        return userDao.find(userId);
//    }

    public User findUserByLoginName(String loginName) {
        return userDao.findByName(loginName);
    }

    public void login(String loginName, String password, boolean isAppLogin) {
        User user = userDao.findByName(loginName);
        validateUser(user);
        UserSecurityToken token = userDao.findSecurityToken(user.getUserId());
        validateUserToken(password, token);
//        openLoginChannel(isAppLogin, user);
    }

//    @Transactional(rollbackFor = Throwable.class)
//    public void login2(String loginName, byte[] password, boolean isAppLogin) {
//        User user = userDao.findByName(loginName);
//        validateUser(user);
//        UserSecurityToken token = userDao.findSecurityToken(user.getUserId());
//        validateUserToken2(password, token);
//        openLoginChannel(isAppLogin, user);
//    }

    public void openLoginChannel(boolean isAppLogin, String loginName) {
        User user = userDao.findByName(loginName);
        openLoginChannel(isAppLogin, user);
    }

    private void openLoginChannel(boolean isAppLogin, User user) {
        if (isAppLogin && BooleanUtils.isNotTrue(user.getIfOpenAppLogin())) {
            userDao.openAppLogin(user.getUserId(), timeSource.getCurrentTime());
        }
        if (!isAppLogin && BooleanUtils.isNotTrue(user.getIfOpenWebLogin())) {
            userDao.openWebLogin(user.getUserId(), timeSource.getCurrentTime());
        }
    }

    public UserSecurityToken findSecurityToken(String userId) {
        return userDao.findSecurityToken(userId);
    }

    public UserSecurityToken findSecurityTokenNew(String userId) {
        return userDao.findSecurityTokenNew(userId);
    }

    public UserSecurityToken findSecurityTokenByName(String loginName) {
        User user = userDao.findByName(loginName);
        validateUser(user);
        return userDao.findSecurityTokenNew(user.getUserId());
//        return userDao.findSecurityToken(user.getUserId());
    }

    private void validateUser(User user) {
        if (user == null) {
            throw new LoginException("用户名或密码错误");
        }
    }

    private void validateUserToken(String password, UserSecurityToken token) {
        // 校验密码是否相等
        if (!StringUtils.equals(password, token.getPassword())) {
            throw new LoginException("用户名或密码错误");
        }
    }

//    private void validateUserToken2(byte[] passwordRaw, UserSecurityToken token) {
//        String pass = new Sha256Hash(passwordRaw, ByteSource.Util.bytes(token.getSalt())).toHex();
//        if (!StringUtils.equals(pass, token.getPassword())) {
//            logger.debug("密码不匹配，用户: " + token.getUserId());
//            throw new LoginException("用户名或密码错误");
//        }
//    }


    public List<Department> findDepartments(String userId) {
        return departmentDao.findDepartments(userId);
    }

    /**
     * 查询所有用户信息
     *
     * @return
     * @throws
     * @Title: findAll
     */
    public List<User> findAll() {
        return userDao.findAll();
    }

    public void createDbSession(DbSession session) {
        session.setIfActive(1);
        session.setRefreshTime(timeSource.getCurrentTime());
        userDao.createSession(session);
    }

    public void updateSession(DbSession param) {
        DbSession sessionInDB = userDao.findSession(param.getToken());
        sessionInDB.setSessionString(param.getSessionString());
        sessionInDB.setRefreshTime(timeSource.getCurrentTime());
        sessionInDB.setIfActive(param.getIfActive());
        userDao.updateSession(sessionInDB);
    }

    public void deleteSession(String token) {
        userDao.revokeSession(token);
    }

    public DbSession findSession(String token) {
        return userDao.findSession(token);
    }

    public List<String> findUserRoles(String userId) {
        return userRoleDao.findUserRoles(userId);
    }

    public List<String> findUserPermissions(String userId) {
        return userRoleDao.fileUserPermissions(userId);
    }


    public List<String> getPosition(String userId) {
        return userDao.findUserPosition(userId);
    }

    public List<Department> findPositions(String userId) {
        return departmentDao.findPositions(userId);
    }

    public void changePassword(String userId, String oldPwd, String newPwd) {
        passwordRule.passwordValid(newPwd);
        UserSecurityToken securityToken = userDao.findSecurityToken(userId);
        Validator.notNull(securityToken, "修改密码失败");
        validateOldPwdMatched(securityToken, oldPwd);
        UserSecurityToken newToken = new UserSecurityToken();
        newToken.setUserId(userId);
        newToken.setPassword(newPwd);
        userDao.updateSecurityToken(newToken);
    }

    private void validateOldPwdMatched(UserSecurityToken securityToken, String oldPwd) {
        if (!StringUtils.equals(securityToken.getPassword(), oldPwd)) {
            // TODO 是否要记录密码出错次数？

            throw new ValidateException("旧密码输入错误");
        }
    }

    public boolean validateIfUserEnabledLogin(User user) {
        Integer enabled = userDao.validateIfUserEnabledLogin(user);
        if (enabled == null) {
            return false;
        }
        //0：允许；1：不允许
        boolean flag = "0".equals(enabled.toString());
        return flag;
    }

    public User findUserByToken(String token) {
        return userDao.findByToken(token);
    }

    public String createDomainUser(String userName, String name) {
        String serialNo = serialNoService.newSerialNo("user");
        UserPo user = new UserPo();
        user.setUserID(serialNo);
        user.setUserName(userName);
        user.setName(name);
        user.setDomainAccount(true);
        user.setStatus(1);
        user.setIfDelete(false);
        user.setOperTime(timeSource.getCurrentTime());
        userDao.create(user);
        return serialNo;
    }

    public List<User> findUserWithInvalidPassword() {
        return userDao.findUserWithInvalidPassword();
    }

    public void createPassword(String userId, String hashedPassword, String salt) {
        UserPassPo po = new UserPassPo();
        po.setUserId(userId);
        po.setPassword(hashedPassword);
        po.setSalt(salt);
        po.setLastOperTime(timeSource.getCurrentTime());
        po.setIfForceUpdate("1");
        userPassDao.create(po);
    }

    public User findUser(String userId) {
        return userDao.find(userId);
    }


//    @Transactional(rollbackFor = Throwable.class)
//    public void useNewUserPassword(UserWithPasswordVo vo) {
//        createNewPassword(vo);
//        // TODO should enable disableOldPassword(vo);
//    }
//
//    private void createNewPassword(UserWithPasswordVo vo) {
//        UserPassPo po = new UserPassPo();
//        po.setUserId(vo.getUserId());
//        String salt = RandomStringUtils.randomAlphanumeric(16);
//        po.setSalt(salt);
//        po.setPassword(hash(vo.getPassword(), salt));
//        po.setLastOperatTime(timeSource.getCurrentTime());
//        po.setIfForceUpdate("0");
//        userPassDao.create(po);
//    }
//
//    private String hash(String pwd, String salt) {
//        String pass = new Sha256Hash(pwd.getBytes(), ByteSource.Util.bytes(salt.getBytes())).toHex();
//        return pass;
//    }
//
//    private void disableOldPassword(UserWithPasswordVo vo) {
//        disableOldPassword(vo.getUserId());
//    }
//
//    private void disableOldPassword(String userId) {
//        UserSecurityToken token = new UserSecurityToken();
//        token.setUserId(userId);
//        token.setPassword("********");
//        token.setStatus(UserPasswordStatus.DISABLED);
//        userDao.updateSecurityToken(token);
//    }
}
