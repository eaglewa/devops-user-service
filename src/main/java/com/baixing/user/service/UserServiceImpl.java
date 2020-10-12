package com.baixing.user.service;

import com.baixing.user.dao.UserDao;
import com.baixing.user.entity.SaveUserReq;
import com.baixing.user.entity.User;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: ao.wang
 *
 * @create: 2020-10-12
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public List<User> list() {
        return userDao.findAll();
    }
    
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public Optional<User> get(Long id) {
        return userDao.findById(id);
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void create(SaveUserReq req) {
        User user = new User();
        user.setName(req.getName());
        user.setAge(req.getAge());
        userDao.save(user);
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void update(SaveUserReq req) {
        userDao.findById(req.getId()).ifPresent(user -> {
            user.setName(req.getName());
            user.setAge(req.getAge());
            userDao.save(user);
        });
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void remove(Long id) {
        userDao.findById(id).ifPresent(userDao::delete);
    }

}
