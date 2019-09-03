package ${BasePackageName}${ControllerPackageName};

import ${BaseEntityPackageName}${EntityPackageName}.${ClassName}Entity;
import ${BasePackageName}${FuturePackageName}.${ClassName}Future;
import com.yfny.utilscommon.basemvc.producer.BaseController;
import com.yfny.utilscommon.basemvc.producer.BaseFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ${Description}Controller
 * Author ${Author}
 * Date  ${Date}
 */
@RestController
@RequestMapping(value = "/${ClassName?uncap_first}")
public class ${ClassName}Controller extends BaseController<${ClassName}Entity> {

    @Autowired
    private ${ClassName}Future ${ClassName?uncap_first}Future;

    @Override
    public BaseFuture<${ClassName}Entity> getBaseFuture() {
        return this.${ClassName?uncap_first}Future;
    }

}
