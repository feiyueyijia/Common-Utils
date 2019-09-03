<#if ClassType == "VICE">
    <#if RelationType == "ONE2ONE">
    /**
     * 根据外键查询相应对象（一对一关系）
     *
     * @param   ${ForeignKeyPropertyName}    外键
     * @return  返回对象列表为查询结果
     */
    @Select("select * from ${ClassName?uncap_first} where ${ForeignKeyColumnName} = <#noparse>#{</#noparse>${ForeignKeyPropertyName}<#noparse>}</#noparse>")
    ${ClassName}Entity select${ClassName}By${ForeignKeyPropertyName?cap_first}(String ${ForeignKeyPropertyName});

    <#elseif RelationType == "ONE2MANY">
    /**
     * 根据外键查询相应对象（一对多关系）
     *
     * @param   ${ForeignKeyPropertyName}    外键
     * @return  返回对象列表为查询结果
     */
    @Select("select * from ${ClassName?uncap_first} where ${ForeignKeyColumnName} = <#noparse>#{</#noparse>${ForeignKeyPropertyName}<#noparse>}</#noparse>")
    List<${ClassName}Entity> find${ClassName}By${ForeignKeyPropertyName?cap_first}(String ${ForeignKeyPropertyName});

    </#if>
<#elseif ClassType == "MAIN">
    <#--<#assign PrimaryKey = ""/>-->
    @Results({
        <#--<#list ColumnInfoList as ColumnInfo>
            <#if ColumnInfo.primaryKey>
            <#assign PrimaryKey = ColumnInfo.columnName/>
            @Result(id = true, column = "${ColumnInfo.columnName}", property = "${ColumnInfo.propertyName}"),
            <else>
            @Result(property = "${ColumnInfo.propertyName}", column = "${ColumnInfo.columnName}", javaType = ${ColumnInfo.typeName}.class),
            </#if>
        </#list>-->
        <#list RelationClassNameMap?keys as ClassName>
            <#if RelationClassNameMap[ClassName] == "ONE2ONE">
            @Result(property = "${ClassName?uncap_first}", column = "${PrimaryKey}",
                    one = @One(select = "${BasePackageName}${MapperPackageName}.${ClassName}Mapper.select${ClassName}By${ForeignKeyPropertyName?cap_first}", fetchType = FetchType.EAGER)),
            <#elseif RelationClassNameMap[ClassName] == "ONE2MANY">
            @Result(property = "${ClassName?uncap_first}List", column = "${PrimaryKey}",
                    many = @Many(select = "${BasePackageName}${MapperPackageName}.${ClassName}Mapper.find${ClassName}By${ForeignKeyPropertyName?cap_first}", fetchType = FetchType.EAGER)),
            </#if>
        </#list>
    })
</#if>
