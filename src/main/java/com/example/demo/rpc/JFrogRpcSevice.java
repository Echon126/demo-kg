package com.example.demo.rpc;

import com.example.demo.domain.dto.JfrogDeleteUser;
import com.example.demo.domain.entity.SaveJFrogUser;

/**
 * @author zhangwenjie
 */
public interface JFrogRpcSevice {




    /**
     * 功能描述：删除JFrog用户
     *
     * @param jfrogDeleteUser
     * @return
     */
    boolean deleteJfrogUser(JfrogDeleteUser jfrogDeleteUser);


    boolean saveJFrogUser(SaveJFrogUser jFrogUser);


    boolean editJFrogUser(SaveJFrogUser jFrogUser);
}
