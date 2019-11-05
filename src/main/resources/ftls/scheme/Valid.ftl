package ${BasePackageName}.${ValidPackageName};

import ${BasePackageName}.${EntityPackageName}.${ClassName}Entity;
import ${BasePackageName}.${ServicePackageName}.${ClassName}Service;
import com.yfny.utilscommon.basemvc.common.BaseEntity;
import com.yfny.utilscommon.basemvc.common.BusinessException;
import com.yfny.utilscommon.basemvc.producer.BaseValid;
import com.yfny.utilscommon.util.InvokeResult;
import com.yfny.utilscommon.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * ${Descriptions}Valid
 * Author ${Author}
 * Date  ${Date}
 */
@Component
public class ${ClassName}Valid implements BaseValid<${ClassName}Entity> {

    @Autowired
    private ${ClassName}Service ${ClassName?uncap_first}Service;

    public void validInsert(${ClassName}Entity entity) throws BusinessException {

    }

    public void validUpdate(${ClassName}Entity entity) throws BusinessException {

    }

    public void validDelete(${ClassName}Entity entity) throws BusinessException {

    }

    public void validSelect(${ClassName}Entity entity) throws BusinessException {

    }

}
