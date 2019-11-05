<#if ClassType == "FOREIGN">
    <#if RelationType == "ONE2ONE" || RelationType == "MANY2ONE">
    /**
     * 根据外键查询相应对象（一对一关系）
     *
     * @param   ${FkProperty}    外键
     * @return  返回对象为查询结果
     */
    @Select("SELECT * FROM ${TableName} WHERE ${ForeignKey} = <#noparse>#{</#noparse>${FkProperty}<#noparse>}</#noparse>")
    ${ClassName}Entity select${ClassName}By${FkProperty?cap_first}(String ${FkProperty});

    <#elseif RelationType == "ONE2MANY">
    /**
     * 根据外键查询相应对象（一对多关系）
     *
     * @param   ${FkProperty}    外键
     * @return  返回对象列表为查询结果
     */
    @Select("SELECT * FROM ${TableName} WHERE ${ForeignKey} = <#noparse>#{</#noparse>${FkProperty}<#noparse>}</#noparse>")
    List<${ClassName}Entity> find${ClassName}By${FkProperty?cap_first}(String ${FkProperty});

    </#if>
<#elseif ClassType == "PRIMARY">
    @SelectProvider(type = ${ClassName}SqlBuilder.class, method = "buildFind${ClassName}ByAndCondition")
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
    List<${ClassName}Entity> findComplexListByAndCondition(@Param("${ClassName?uncap_first}") ${ClassName}Entity ${ClassName?uncap_first});

    @SelectProvider(type = ${ClassName}SqlBuilder.class, method = "buildFind${ClassName}ByORCondition")
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
    List<${ClassName}Entity> findComplexListByORCondition(@Param("${ClassName?uncap_first}") ${ClassName}Entity ${ClassName?uncap_first});

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
</#if>
