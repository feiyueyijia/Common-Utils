    <#list RelationList as Relation>
        <#assign relation = Relation.relation/>
        <#assign fkClassName = Relation.fkClassName/>
        <#if relation == "ONE2ONE">
    @OneToOne
    @Transient
    private ${fkClassName}Entity ${fkClassName?uncap_first};
        <#elseif relation == "ONE2MANY">
    @OneToMany
    @Transient
    private List<${fkClassName}Entity> ${fkClassName?uncap_first}List;
        <#elseif relation == "MANY2ONE">
    @ManyToOne
    @Transient
    private ${fkClassName}Entity ${fkClassName?uncap_first};
        </#if>
    </#list>

    <#list RelationList as Relation>
        <#assign relation = Relation.relation/>
        <#assign fkClassName = Relation.fkClassName/>
        <#if relation == "ONE2ONE">
    public ${fkClassName}Entity get${fkClassName}() {
        return ${fkClassName?uncap_first};
    }

    public void set${fkClassName}(${fkClassName}Entity ${fkClassName?uncap_first}) {
        this.${fkClassName?uncap_first} = ${fkClassName?uncap_first};
    }

        <#elseif relation == "ONE2MANY">
    public List<${fkClassName}Entity> get${fkClassName}List() {
        return ${fkClassName?uncap_first}List;
    }

    public void set${fkClassName}List(List<${fkClassName}Entity> ${fkClassName?uncap_first}List) {
        this.${fkClassName?uncap_first}List = ${fkClassName?uncap_first}List;
    }

        <#elseif relation == "MANY2ONE">
    public ${fkClassName}Entity get${fkClassName}() {
        return ${fkClassName?uncap_first};
    }

    public void set${fkClassName}(${fkClassName}Entity ${fkClassName?uncap_first}) {
        this.${fkClassName?uncap_first} = ${fkClassName?uncap_first};
    }

        </#if>
    </#list>
