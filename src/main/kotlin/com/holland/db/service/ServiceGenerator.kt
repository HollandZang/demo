package com.holland.db.service

import com.google.common.base.CaseFormat
import com.holland.db.DBController
import com.holland.db.FileWriteUtil
import java.io.File

class ServiceGenerator(private val dbController: DBController) {
    private val className: String = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, dbController.tableName)

    fun execute() {
        /* generate interface */
        StringBuilder(
            """
                package ${dbController.`package`}.service;
        
                import ${dbController.`package`}.pojo;
        
                @Service
                public interface I${className}Service extends Service<${className}> {
                    @Override
                    int deleteByPrimaryKey(String key);
                
                    @Override
                    int insertSelective(${className} record);
                
                    @Override
                    RiskInfo selectByPrimaryKey(String key);
                
                    @Override
                    int updateByPrimaryKeySelective(${className} record);
                }
        """.trimIndent()
        ).let { FileWriteUtil.string2File(it, DBController.dao, className) }

        /* generate implementation */
        val instanceName = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, className)
        StringBuilder(
            """
                package ${dbController.`package`}.service.impl;

                import ${dbController.`package`}.pojo;
        
                public class ${className}ServiceImpl implement I${className}Service {
                    
                    @Autowired
                    private ${className}Mapper ${instanceName}Mapper;
                    
                    @Override
    public Long getNextVal() throws Exception{
        return basCodeMapper.getNextVal();
    }
                    
                    @Override
                    public int deleteByPrimaryKey(String key){
                        return ${instanceName}Mapper.deleteByPrimaryKey(key);
                    };
                
                    @Override
                    public int insertSelective(${className} record){
                        return ${instanceName}Mapper.insertSelective(record)
                    };
                
                    @Override
                    public RiskInfo selectByPrimaryKey(String key){
                        return ${instanceName}Mapper.selectByPrimaryKey(key);
                    };
                
                    @Override
                    public int updateByPrimaryKeySelective(${className} record){
                    
                    };
                }
            """.trimIndent()
        ).let { FileWriteUtil.string2File(it, "${DBController.dao}${File.separatorChar}impl", className) }
    }

    fun initInterface(): ServiceGenerator {
        StringBuilder(
            """
                package ${dbController.`package`}.service;

                public interface Service<Pojo> {
                    int deleteByPrimaryKey(String key);

                    int insertSelective(Pojo record);

                    Pojo selectByPrimaryKey(String key);

                    int updateByPrimaryKeySelective(Pojo record);
                }
            """.trimIndent()
        ).let { FileWriteUtil.string2File(it, DBController.dao, className) }
        return this
    }
}