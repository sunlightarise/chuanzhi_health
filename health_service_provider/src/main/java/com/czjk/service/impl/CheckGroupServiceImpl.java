package com.czjk.service.impl;

import cn.hutool.core.util.ArrayUtil;
import com.alibaba.dubbo.config.annotation.Service;
import com.czjk.dao.CheckGroupDao;
import com.czjk.pojo.CheckGroup;
import com.czjk.service.CheckGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Haotian
 * @Date: 2019/12/24 19:39
 * @Description: 检查组服务
 */
@Service(interfaceClass = CheckGroupService.class)
@Transactional
public class CheckGroupServiceImpl implements CheckGroupService {
    @Autowired
    private CheckGroupDao checkGroupDao;

    @Override
    public void add(CheckGroup checkGroup, Integer[] checkitemIds) {
        //新增检查组，操作t_checkgroup表
        checkGroupDao.add( checkGroup );
        //设置检查组和检查项的多对多的关联关系，操作t_checkgroup_checkitem表
        this.setCheckGroupAndCheckItem( checkGroup.getId(), checkitemIds );
    }

    /**
     * 设置检查组合和检查项的关联关系
     *
     * @param checkGroupId 检查组id
     * @param checkitemIds 检查项id
     */
    public void setCheckGroupAndCheckItem(Integer checkGroupId, Integer[] checkitemIds) {
        if (ArrayUtil.isNotEmpty( checkitemIds )) {
            Map<String, Integer> map = new HashMap<>( 0 );
            for (Integer checkitemId : checkitemIds) {
                map.put( "checkgroupId", checkGroupId );
                map.put( "checkitemId", checkitemId );
                checkGroupDao.setCheckGroupAndCheckItem( map );
            }
        }
    }
}
