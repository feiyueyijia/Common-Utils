package ${BasePackageName}${HystrixPackageName};

import ${BaseEntityPackageName}${EntityPackageName}.${ClassName}Entity;
import ${BasePackageName}${ServicePackageName}.${ClassName}Client;
import com.yfny.utilscommon.basemvc.consumer.BaseHystrix;
import org.springframework.stereotype.Component;

/**
 * ${Description}Hystrix
 * Author ${Author}
 * Date  ${Date}
 */
@Component
public class ${ClassName}Hystrix extends BaseHystrix<${ClassName}Entity> implements ${ClassName}Client {

}
