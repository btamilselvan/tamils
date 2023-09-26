<#if ejb3>
<#if pojo.hasIdentifierProperty()>
<#if field.equals(clazz.identifierProperty)>
 ${pojo.generateAnnIdGenerator()}
<#-- if this is the id property (getter)-->
<#-- explicitly set the column name for this property-->
</#if>
</#if>

<#if c2h.isOneToOne(field)>
${pojo.generateOneToOneAnnotation(field, md)}
<#elseif c2h.isManyToOne(field)>
${pojo.generateManyToOneAnnotation(field)}
<#--TODO support optional and targetEntity-->    
${pojo.generateJoinColumnsAnnotation(field, md)}
<#elseif c2h.isCollection(field)>
${pojo.generateCollectionAnnotation(field, md)}
<#else>
${pojo.generateBasicAnnotation(field)}
${pojo.generateAnnColumnAnnotation(field)}
</#if>
</#if>