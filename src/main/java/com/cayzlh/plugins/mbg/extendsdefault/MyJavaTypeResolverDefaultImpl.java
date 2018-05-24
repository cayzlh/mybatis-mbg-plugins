package com.cayzlh.plugins.mbg.extendsdefault;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.internal.types.JavaTypeResolverDefaultImpl;

import java.sql.Types;

/**
 * Description:
 *
 * <p>
 *     将BIT和TINYINT在映射时都映射为JAVA中的integer类型
 * </p>
 *
 * @author Ant丶
 * @date 2018-05-24.
 */
public class MyJavaTypeResolverDefaultImpl extends JavaTypeResolverDefaultImpl {

    public MyJavaTypeResolverDefaultImpl() {
        super();
        typeMap.put(Types.BIT, new JdbcTypeInformation("BIT",
                new FullyQualifiedJavaType(Integer.class.getName())));
        typeMap.put(Types.TINYINT, new JdbcTypeInformation("TINYINT",
                new FullyQualifiedJavaType(Integer.class.getName())));
    }
}
