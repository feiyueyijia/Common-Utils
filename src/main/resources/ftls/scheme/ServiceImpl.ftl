package ${BasePackageName}.${ServicePackageName}.impl;

import ${BasePackageName}.${EntityPackageName}.${ClassName}Entity;
import ${BasePackageName}.${MapperPackageName}.${ClassName}Mapper;
import ${BasePackageName}.${ServicePackageName}.${ClassName}Service;
import com.yfny.utilscommon.basemvc.producer.BaseMapper;
import com.yfny.utilscommon.basemvc.producer.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ${Descriptions}ServiceImpl
 * Author ${Author}
 * Date  ${Date}
 */
@Service
public class ${ClassName}ServiceImpl extends BaseServiceImpl<${ClassName}Entity> implements ${ClassName}Service {

    @Autowired
    private ${ClassName}Mapper ${ClassName?uncap_first}Mapper;

    @Override
    public BaseMapper<${ClassName}Entity> getBaseMapper() {
        return this.${ClassName?uncap_first}Mapper;
    }

}
