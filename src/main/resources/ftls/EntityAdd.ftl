    <#list RelationClassNameMap?keys as ClassName>
        <#if RelationClassNameMap[ClassName] == "ONE2ONE">
    @OneToOne
    @Transient
    private ${ClassName}Entity ${ClassName?uncap_first};
        <#elseif RelationClassNameMap[ClassName] == "ONE2MANY">
    @OneToMany
    @Transient
    private List<${ClassName}Entity> ${ClassName?uncap_first}List;
        </#if>
    </#list>

    <#list RelationClassNameMap?keys as ClassName>
        <#if RelationClassNameMap[ClassName] == "ONE2ONE">
    public ${ClassName}Entity get${ClassName}() {
        return ${ClassName?uncap_first};
    }

    public void set${ClassName}(${ClassName}Entity ${ClassName?uncap_first}) {
        this.${ClassName?uncap_first} = ${ClassName?uncap_first};
    }

        <#elseif RelationClassNameMap[ClassName] == "ONE2MANY">
    public List<${ClassName}Entity> get${ClassName}List() {
        return ${ClassName?uncap_first}List;
    }

    public void set${ClassName}List(List<${ClassName}Entity> ${ClassName?uncap_first}List) {
        this.${ClassName?uncap_first}List = ${ClassName?uncap_first}List;
    }

        </#if>
    </#list>
