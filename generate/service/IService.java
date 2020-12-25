package com.stardon.stardon_main.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

public interface IService<Pojo> {
    int deleteByPrimaryKey(String key);

    int insertSelective(Pojo record);

    Pojo selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(Pojo record);
    
    PageInfo<Pojo> selectList(Map<String, String> map);
    
    default void initPageHelper(String pageNum, String pageSize) {
        PageHelper.startPage(StringUtils.isEmpty(pageNum)?1:Integer.parseInt(pageNum), 
            StringUtils.isEmpty(pageSize)?10:Integer.parseInt(pageSize));
    }
}