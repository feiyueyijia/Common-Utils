package ${BasePackageName}.${HystrixPackageName};

import ${BasePackageName}.${EntityPackageName}.${ClassName}Entity;
import ${BasePackageName}.${ClientPackageName}.${ClassName}Client;
import com.yfny.utilscommon.basemvc.consumer.BaseHystrix;
import org.springframework.stereotype.Component;

/**
 * ${Descriptions}Hystrix
 * Author ${Author}
 * Date  ${Date}
 */
@Component
public class ${ClassName}Hystrix extends BaseHystrix<${ClassName}Entity> implements ${ClassName}Client {

}
