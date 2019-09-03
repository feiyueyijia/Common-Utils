package ${BasePackageName}${ServicePackageName};

import ${BaseEntityPackageName}${EntityPackageName}.${ClassName}Entity;
import ${BasePackageName}${HystricPackageName}.${ClassName}Hystrix;
import com.yfny.utilscommon.basemvc.consumer.BaseClient;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * ${Description}Service
 * Author ${Author}
 * Date  ${Date}
 */
@FeignClient(value = "${ApplicationName}", path = "/${ClassName?uncap_first}", fallback = ${ClassName}Hystrix.class)
public interface ${ClassName}Client extends BaseClient<${ClassName}Entity> {

}
