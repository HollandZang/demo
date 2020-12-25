package com.holland.db.service

import com.google.common.base.CaseFormat
import com.holland.db.DBController
import com.holland.db.FileWriteUtil
import java.io.File

/**
 * 集成Page helper, Spring
 */
class ServiceGenerator(private val dbController: DBController) {
    private val className: String = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, dbController.tableName)

    fun execute() {
        /* generate interface */
        StringBuilder(
            """
                package ${dbController.`package`}.${DBController.dao};
        
                import ${dbController.`package`}.${DBController.pojo}.${className};
                import ${dbController.`package`}.${DBController.dao}.IService;
                import org.springframework.stereotype.Service;
        
                import com.github.pagehelper.PageInfo;
                import java.util.List;
                import java.util.Map;
        
                @Service
                public interface I${className}Service extends IService<${className}> {
                    @Override
                    int deleteByPrimaryKey(String key);
                
                    @Override
                    int insertSelective(${className} record);
                
                    @Override
                    $className selectByPrimaryKey(String key);
                
                    @Override
                    int updateByPrimaryKeySelective(${className} record);
                    
                    @Override
                    PageInfo<${className}> selectList(Map<String, String> map);
                    ${if (dbController.dataSource == "ORACLE") "@Override\n\tLong getNextVal();" else ""}
                }
        """.trimIndent()
        ).let {
            FileWriteUtil.string2File(it,
                DBController.rootPath + File.separatorChar + DBController.dao,
                "I${className}Service")
        }

        /* generate implementation */
        val instanceName = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, className)
        StringBuilder(
            """
                package ${dbController.`package`}.${DBController.dao}.impl;

                import ${dbController.`package`}.${DBController.pojo}.${className};
                import ${dbController.`package`}.${DBController.dao}.I${className}Service;
                
                import org.springframework.beans.factory.annotation.Autowired;
                import org.springframework.util.StringUtils;
                import com.github.pagehelper.PageHelper;
                import com.github.pagehelper.PageInfo;
                
                import java.util.ArrayList;
                import java.util.List;
                import java.util.Map;

        
                public class ${className}ServiceImpl implements I${className}Service {
                    
                    @Autowired
                    private ${className}Mapper ${instanceName}Mapper;
                    
                    @Override
                    public int deleteByPrimaryKey(String key) {
                        return ${instanceName}Mapper.deleteByPrimaryKey(key);
                    }
                
                    @Override
                    public int insertSelective(${className} record) {
                        return ${instanceName}Mapper.insertSelective(record);
                    }
                
                    @Override
                    public $className selectByPrimaryKey(String key) {
                        return ${instanceName}Mapper.selectByPrimaryKey(key);
                    }
                
                    @Override
                    public int updateByPrimaryKeySelective(${className} record) {
                        return ${instanceName}Mapper.updateByPrimaryKeySelective(record);
                    }
                    
                    @Override
                    public PageInfo<${className}> selectList(Map<String, String> map) {
                        initPageHelper(map.get("page"), map.get("limit"));
                        return new PageInfo<>(${instanceName}Mapper.selectAllByMap(map));
                    }
                    ${
                if (dbController.dataSource == "ORACLE") """
                            @Override    
                            public Long getNextVal() {
                                return ${instanceName}Mapper.getNextVal();
                            }
                            """.trimIndent()
                else ""
            }
                }
            """.trimIndent()
        ).let {
            FileWriteUtil.string2File(it,
                "${DBController.rootPath}${File.separatorChar}${DBController.dao}${File.separatorChar}impl",
                "${className}ServiceImpl")
        }
    }

    fun initInterface(): ServiceGenerator {
        StringBuilder(
            """
                package ${dbController.`package`}.${DBController.dao};

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
                    ${if (dbController.dataSource == "ORACLE") "Long getNextVal();" else ""}
                    default void initPageHelper(String pageNum, String pageSize) {
                        PageHelper.startPage(StringUtils.isEmpty(pageNum)?1:Integer.parseInt(pageNum), 
                            StringUtils.isEmpty(pageSize)?10:Integer.parseInt(pageSize));
                    }
                }
            """.trimIndent()
        ).let {
            FileWriteUtil.string2File(it,
                DBController.rootPath + File.separatorChar + DBController.dao,
                "IService")
        }
        return this
    }
}