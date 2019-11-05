package ${BasePackageName}.${ClientPackageName};

import ${BasePackageName}.${EntityPackageName}.${ClassName}Entity;
import ${BasePackageName}.${HystrixPackageName}.${ClassName}Hystrix;
import com.yfny.utilscommon.basemvc.consumer.BaseClient;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * ${Descriptions}Service
 * Author ${Author}
 * Date  ${Date}
 */
@FeignClient(value = "${ApplicationName}", path = "/${ClassName?uncap_first}", fallback = ${ClassName}Hystrix.class)
public interface ${ClassName}Client extends BaseClient<${ClassName}Entity> {

}
