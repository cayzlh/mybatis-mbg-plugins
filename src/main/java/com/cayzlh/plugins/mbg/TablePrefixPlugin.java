/*
 * Copyright (c) 2017.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.cayzlh.plugins.mbg;

import com.cayzlh.plugins.mbg.utils.BasePlugin;
import com.cayzlh.plugins.mbg.utils.IntrospectedTableTools;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.config.TableConfiguration;

import java.util.List;

/**
 * Description :
 *
 * <p>table前缀，解决多数据源表重名问题插件（为Model、Mapper、Example、xml等增加前缀）</p>
 *
 * @author Ant丶
 * @date 2018-04-27.
 */
public class TablePrefixPlugin extends BasePlugin {

    public static final String PRO_PREFIX = "prefix";  // 前缀 property
    private String prefix;  // 前缀

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean validate(List<String> warnings) {
        // 如果table配置了domainObjectName或者mapperName就不要再启动该插件了
        for (TableConfiguration tableConfiguration : context.getTableConfigurations()) {
            if (tableConfiguration.getDomainObjectName() != null || tableConfiguration.getMapperName() != null) {
                warnings.add(":插件" + this.getClass().getTypeName() + "插件请不要配合table的domainObjectName或者mapperName一起使用！");
                return false;
            }
        }

        return super.validate(warnings);
    }


    /**
     * 初始化阶段
     * 具体执行顺序 http://www.mybatis.org/generator/reference/pluggingIn.html
     * @param introspectedTable
     * @return
     */
    @Override
    public void initialized(IntrospectedTable introspectedTable) {
        // 1. 首先获取全局配置
        this.prefix = getProperties().getProperty(PRO_PREFIX);
        // 2. 获取每个table 具体的
        if (introspectedTable.getTableConfigurationProperty(PRO_PREFIX) != null) {
            this.prefix = introspectedTable.getTableConfigurationProperty(PRO_PREFIX);
        }
        // 3. 判断是否配置了前缀
        // !!! TableRenamePlugin 插件的 tableOverride 优先级最高
        if (this.prefix != null && introspectedTable.getTableConfigurationProperty(TableRenamePlugin.PRO_TABLE_OVERRIDE) == null) {
            String domainObjectName = introspectedTable.getFullyQualifiedTable().getDomainObjectName();
            domainObjectName = prefix + domainObjectName;
            try {
                IntrospectedTableTools.setDomainObjectName(introspectedTable, getContext(), domainObjectName);
            } catch (Exception e) {
                logger.error(":插件" + this.getClass().getTypeName() + "使用prefix替换时异常！", e);
            }
        }
    }
}
