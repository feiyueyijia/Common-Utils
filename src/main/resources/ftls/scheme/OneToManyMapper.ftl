
    @Select("select * from ${TableName} where id = #{id}")
    @Results({
          <#list ColumnInfoList as ColumnInfo>
             <#if ColumnInfo.primaryKey>
            @Result(id = true, column = "${ColumnInfo.columnName}", property = "${ColumnInfo.propertyName}"),
             <else>
            @Result(property = "${ColumnInfo.propertyName}", column = "${ColumnInfo.columnName}", javaType = ${ColumnInfo.typeName}.class),
             </#if>
          </#list>
            @Result(property = "detailsEntity", column = "id", javaType = ${123}.class),
                    one = @One(select = "com.yfny.servicehello.mapper.DetailsMapper.selectOne", fetchType = FetchType.EAGER)),
            @Result(property = "carEntities", column = "id", javaType = ${123}.class),
                    many = @Many(select = "com.yfny.servicehello.mapper.CarMapper.findList", fetchType = FetchType.EAGER))
    })
    public ${ClassName}Entity select${ClassName}ByCondition(${ClassName}Entity ${EntityName});


    @Select("select * from ${TableName} where id = #{id}")
    @Results({
           <#list ColumnInfoList as ColumnInfo>
              <#if ColumnInfo.primaryKey>
             @Result(id = true, column = "${ColumnInfo.columnName}", property = "${ColumnInfo.propertyName}"),
              <else>
             @Result(property = "${ColumnInfo.propertyName}", column = "${ColumnInfo.columnName}", javaType = ${ColumnInfo.typeName}.class),
              </#if>
           </#list>
             @Result(property = "detailsEntity", column = "id", javaType = ${123}.class),
                     one = @One(select = "com.yfny.servicehello.mapper.DetailsMapper.selectOne", fetchType = FetchType.EAGER)),
             @Result(property = "carEntities", column = "id", javaType = ${123}.class),
                     many = @Many(select = "com.yfny.servicehello.mapper.CarMapper.findList", fetchType = FetchType.EAGER))
     })
     public List<${ClassName}Entity> find${ClassName}ListById(${ClassName}Entity ${EntityName});

