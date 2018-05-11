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

package com.cayzlh.plugins.mbg.utils;

import com.cayzlh.plugins.mbg.CommentPlugin;
import com.cayzlh.plugins.mbg.utils.enhanced.TemplateCommentGenerator;
import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.PluginConfiguration;
import org.mybatis.generator.internal.DefaultCommentGenerator;
import org.mybatis.generator.internal.util.StringUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.List;

/**
 * 描述
 *
 * <p>基础plugin</p>
 *
 * @author Ant丶
 * @date 2018-04-27.
 */
public class BasePlugin extends PluginAdapter {
	/**
	 * 日志
	 */
	protected static final Logger logger = LoggerFactory.getLogger(BasePlugin.class);
	/**
	 * 注释工具
 	 */
    protected CommentGenerator commentGenerator;
	/**
	 * 提示
 	 */
    protected List<String> warnings;

	/**
	 * 要求运行targetRuntime必须为MyBatis3
	 */
	private final static String TARGET_RUNTIME = "MyBatis3";

    /**
     * Set the context under which this plugin is running.
     *
     * @param context
     *            the new context
     */
    @Override
    public void setContext(Context context) {
        super.setContext(context);

        // 配置插件使用的模板引擎
        PluginConfiguration cfg = PluginTools.getPluginConfiguration(context, CommentPlugin.class);

        if (cfg == null || cfg.getProperty(CommentPlugin.PRO_TEMPLATE) == null){
            if (context.getCommentGenerator() instanceof DefaultCommentGenerator){
                // 使用默认模板引擎
                commentGenerator = new TemplateCommentGenerator("default-comment.ftl", true);
            } else {
                // 用户自定义
                commentGenerator = context.getCommentGenerator();
            }
        } else {
            TemplateCommentGenerator templateCommentGenerator = new TemplateCommentGenerator(cfg.getProperty(CommentPlugin.PRO_TEMPLATE), false);

            //  插件使用的注释生成器
            commentGenerator = templateCommentGenerator;

            // 修正系统插件
            try {
                // 先执行一次生成CommentGenerator操作，然后再替换
                context.getCommentGenerator();

                Field field = Context.class.getDeclaredField("commentGenerator");
                field.setAccessible(true);
                field.set(context, templateCommentGenerator);
            } catch (Exception e) {
                logger.error("反射异常",e);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean validate(List<String> warnings) {
        this.warnings = warnings;
        // 插件使用前提是targetRuntime为MyBatis3
        if (StringUtility.stringHasValue(getContext().getTargetRuntime()) && TARGET_RUNTIME.equalsIgnoreCase(getContext().getTargetRuntime()) == false) {
            warnings.add("插件" + this.getClass().getTypeName() + "要求运行targetRuntime必须为MyBatis3！");
            return false;
        }

        return true;
    }
}
