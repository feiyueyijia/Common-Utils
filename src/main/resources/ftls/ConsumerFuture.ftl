package ${BasePackageName}${FuturePackageName};

import ${BaseEntityPackageName}${EntityPackageName}.${ClassName}Entity;
import ${BasePackageName}${ServicePackageName}.${ClassName}Client;
import com.yfny.utilscommon.basemvc.consumer.BaseClient;
import com.yfny.utilscommon.basemvc.consumer.BaseFuture;
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
    private ${ClassName}Client ${ClassName?uncap_first}Client;

    @Override
    public BaseClient<${ClassName}Entity> getBaseClient() {
        return this.${ClassName?uncap_first}Client;
    }

}
