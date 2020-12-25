package com.stardon.stardon_main.service;

import com.stardon.stardon_main.pojo.AcdFile;
import com.stardon.stardon_main.service.IService;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import java.util.List;
import java.util.Map;

@Service
public interface IAcdFileService extends IService<AcdFile> {
    @Override
    int deleteByPrimaryKey(String key);

    @Override
    int insertSelective(AcdFile record);

    @Override
    AcdFile selectByPrimaryKey(String key);

    @Override
    int updateByPrimaryKeySelective(AcdFile record);
    
    @Override
    PageInfo<AcdFile> selectList(Map<String, String> map);
    
}