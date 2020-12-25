package com.stardon.stardon_main.service.impl;

import com.stardon.stardon_main.pojo.AcdFile;
import com.stardon.stardon_main.service.IAcdFileService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class AcdFileServiceImpl implements IAcdFileService {
    
    @Autowired
    private AcdFileMapper acdFileMapper;
    
    @Override
    public int deleteByPrimaryKey(String key) {
        return acdFileMapper.deleteByPrimaryKey(key);
    }

    @Override
    public int insertSelective(AcdFile record) {
        return acdFileMapper.insertSelective(record);
    }

    @Override
    public AcdFile selectByPrimaryKey(String key) {
        return acdFileMapper.selectByPrimaryKey(key);
    }

    @Override
    public int updateByPrimaryKeySelective(AcdFile record) {
        return acdFileMapper.updateByPrimaryKeySelective(record);
    }
    
    @Override
    public PageInfo<AcdFile> selectList(Map<String, String> map) {
        initPageHelper(map.get("page"), map.get("limit"));
        return new PageInfo<>(acdFileMapper.selectAllByMap(map));
    }
    
}