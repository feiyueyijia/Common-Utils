package ${BasePackageName}${MapperPackageName};

import ${BaseEntityPackageName}${EntityPackageName}.${ClassName}Entity;
import ${BasePackageName}${SqlBuilderPackageName}.${ClassName}SqlBuilder;
import com.yfny.utilscommon.basemvc.producer.BaseMapper;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

/**
 * ${Description}Mapper
 * Author ${Author}
 * Date  ${Date}
 */
public interface ${ClassName}Mapper extends BaseMapper<${ClassName}Entity> {

    /**
     * 根据实体中的属性值进行查询，查询条件使用LIKE，并列查询取交集
     *
     * @param   ${ClassName?uncap_first}    对象实体
     * @return  返回对象列表为查询结果
     */
    @SelectProvider(type = ${ClassName}SqlBuilder.class, method = "buildFind${ClassName}ByAndCondition")
    List<${ClassName}Entity> findSimpleListByAndCondition(@Param("${ClassName?uncap_first}") ${ClassName}Entity ${ClassName?uncap_first});

    /**
     * 根据实体中的属性值进行查询，查询条件使用LIKE，亦或查询取并集
     *
     * @param   ${ClassName?uncap_first}    对象实体
     * @return  返回对象列表为查询结果
     */
    @SelectProvider(type = ${ClassName}SqlBuilder.class, method = "buildFind${ClassName}ByORCondition")
    List<${ClassName}Entity> findSimpleListByORCondition(@Param("${ClassName?uncap_first}") ${ClassName}Entity ${ClassName?uncap_first});

}
