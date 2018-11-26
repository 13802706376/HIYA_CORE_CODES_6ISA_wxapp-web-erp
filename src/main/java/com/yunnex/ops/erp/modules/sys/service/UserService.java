package com.yunnex.ops.erp.modules.sys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yunnex.ops.erp.common.service.CrudService;
import com.yunnex.ops.erp.modules.sys.dao.UserDao;
import com.yunnex.ops.erp.modules.sys.entity.User;

@Service
@Transactional(readOnly = true)
public class UserService extends CrudService<UserDao, User> {

    @Autowired
    private UserDao userDao;
    
    public List<User> getUserByRoleName(String roleName) {
        return userDao.getUserByRoleName(roleName);
    }
    
}
