package ${BasePackageName}${ServicePackageName};

import ${BaseEntityPackageName}${EntityPackageName}.${ClassName}Entity;
import ${BasePackageName}${MapperPackageName}.${ClassName}Mapper;
import com.yfny.utilscommon.basemvc.producer.BaseMapper;
import com.yfny.utilscommon.basemvc.producer.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ${Description}ServiceImpl
 * Author ${Author}
 * Date  ${Date}
 */
@Service
public class ${ClassName}ServiceImpl extends BaseServiceImpl<${ClassName}Entity> {

    @Autowired
    private ${ClassName}Mapper ${ClassName?uncap_first}Mapper;

    @Override
    public BaseMapper<${ClassName}Entity> getBaseMapper() {
        return this.${ClassName?uncap_first}Mapper;
    }

}
