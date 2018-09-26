/*
 *
 *  * Copyright (c) 2017.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *     http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package com.cayzlh.plugins.mbg;

import com.cayzlh.plugins.mbg.utils.BasePlugin;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.JavaModelGeneratorConfiguration;

import java.util.List;
import java.util.Properties;

/**
 * Description :
 *
 * <p>Example类生成位置修改</p>
 *
 * @author Ant丶
 * @date 2018-04-27.
 */
public class ExampleTargetPlugin extends BasePlugin {
    /**
     * 配置targetPackage名
     */
    public static final String PRO_TARGET_PACKAGE = "targetPackage";
    /**
     * 目标包
     */
    private static String targetPackage;

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean validate(List<String> warnings) {
        // 获取配置的目标package
        Properties properties = getProperties();
        targetPackage = properties.getProperty(PRO_TARGET_PACKAGE);
        if (targetPackage == null){
            warnings.add("请配置com.cayzlh.plugins.mbg.ExampleTargetPlugin插件的目标包名(targetPackage)！");
            return false;
        }
        return super.validate(warnings);
    }

    /**
     * 初始化阶段
     * 具体执行顺序 http://www.mybatis.org/generator/reference/pluggingIn.html
     *
     * @param introspectedTable
     */
    @Override
    public void initialized(IntrospectedTable introspectedTable) {
        String exampleType = introspectedTable.getExampleType();
        // 修改包名
        Context context = getContext();
        JavaModelGeneratorConfiguration configuration = context.getJavaModelGeneratorConfiguration();
        String targetPackage = configuration.getTargetPackage();
        String newExampleType = exampleType.replace(targetPackage, ExampleTargetPlugin.targetPackage);

        introspectedTable.setExampleType(newExampleType);

        logger.debug("(Example 目标包修改插件):修改"+introspectedTable.getExampleType()+"的包到"+ ExampleTargetPlugin.targetPackage);
    }

}
