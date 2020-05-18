    @SelectProvider(type = ${ClassName}SqlBuilder.class, method = "buildFind${ClassName}ByCondition")
    @Results({
            @Result(id = true, column = "${PrimaryKey}", property = "${PkProperty}"),
        <#list RelationList as Relation>
            <#assign relation = Relation.relation/>
            <#assign fkClassName = Relation.fkClassName/>
            <#assign foreignKey = Relation.foreignKey/>
            <#assign fkProperty = Relation.fkProperty/>
            <#assign primaryKey = Relation.primaryKey/>
            <#assign pkProperty = Relation.pkProperty/>
            <#if relation == "ONE2ONE" || relation == "MANY2ONE">
            @Result(column = "${primaryKey}", property = "${fkClassName?uncap_first}",
                    one = @One(select = "${BasePackageName}.${MapperPackageName}.${fkClassName}Mapper.selectById", fetchType = FetchType.EAGER)),
            <#elseif relation == "ONE2MANY">
            @Result(column = "${PrimaryKey}", property = "${fkClassName?uncap_first}List",
                    many = @Many(select = "${BasePackageName}.${MapperPackageName}.${fkClassName}Mapper.find${fkClassName}By${fkProperty?cap_first}", fetchType = FetchType.EAGER)),
            </#if>
        </#list>
    })
    List<${ClassName}Entity> findComplexListByCondition(@Param("${ClassName?uncap_first}") ${ClassName}Entity ${ClassName?uncap_first});

    @Select("SELECT * FROM ${TableName} WHERE ${PrimaryKey} = ${r'#{id}'}")
    @Results({
            @Result(id = true, column = "${PrimaryKey}", property = "${PkProperty}"),
        <#list RelationList as Relation>
            <#assign relation = Relation.relation/>
            <#assign fkClassName = Relation.fkClassName/>
            <#assign foreignKey = Relation.foreignKey/>
            <#assign fkProperty = Relation.fkProperty/>
            <#assign primaryKey = Relation.primaryKey/>
            <#assign pkProperty = Relation.pkProperty/>
            <#if relation == "ONE2ONE" || relation == "MANY2ONE">
            @Result(column = "${primaryKey}", property = "${fkClassName?uncap_first}",
                    one = @One(select = "${BasePackageName}.${MapperPackageName}.${fkClassName}Mapper.selectById", fetchType = FetchType.EAGER)),
            <#elseif relation == "ONE2MANY">
            @Result(column = "${PrimaryKey}", property = "${fkClassName?uncap_first}List",
                    many = @Many(select = "${BasePackageName}.${MapperPackageName}.${fkClassName}Mapper.find${fkClassName}By${fkProperty?cap_first}", fetchType = FetchType.EAGER)),
            </#if>
        </#list>
    })
    ${ClassName}Entity selectComplexById(@Param("id") String id);

