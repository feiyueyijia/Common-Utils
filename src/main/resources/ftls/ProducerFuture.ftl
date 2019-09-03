package ${BasePackageName}${FuturePackageName};

import ${BaseEntityPackageName}${EntityPackageName}.${ClassName}Entity;
import ${BasePackageName}${ServicePackageName}.${ClassName}ServiceImpl;
import com.yfny.utilscommon.basemvc.producer.BaseFuture;
import com.yfny.utilscommon.basemvc.producer.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * ${Description}Future
 * Author ${Author}
 * Date  ${Date}
 */
@Component
public class ${ClassName}Future extends BaseFuture<${ClassName}Entity> {

    @Autowired
    private ${ClassName}ServiceImpl ${ClassName?uncap_first}Service;

    @Override
    public BaseServiceImpl<${ClassName}Entity> getBaseService() {
        return this.${ClassName?uncap_first}Service;
    }

}
