package ${BasePackageName}${CompositePackageName};

import ${BaseEntityPackageName}${EntityPackageName}.${ClassName}Entity;
import ${BasePackageName}${MapperPackageName}.${ClassName}Mapper;
import com.yfny.utilscommon.basemvc.producer.BaseComponent;
import com.yfny.utilscommon.basemvc.producer.BaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
* ${Description}Composite
* Author ${Author}
* Date  ${Date}
*/
@Component
public class ${ClassName}Composite extends BaseComponent<${ClassName}Entity> {

    @Autowired
    private ${ClassName}Mapper ${ClassName?uncap_first}Mapper;

    public static ${ClassName}Composite ${ClassName?uncap_first}Composite;

    @PostConstruct
    public void init() {
        ${ClassName?uncap_first}Composite = this;
    }

    @Override
    public BaseMapper<${ClassName}Entity> getBaseMapper() {
        return ${ClassName?uncap_first}Composite.${ClassName?uncap_first}Mapper;
    }

}
