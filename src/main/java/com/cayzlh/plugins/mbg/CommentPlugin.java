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

import java.util.Properties;

/**
 * 描述
 *
 * <p>评论插件</p>
 *
 * @author Ant丶
 * @date 2018-04-27.
 */
public class CommentPlugin extends BasePlugin {
    // 模板 property
    public static final String PRO_TEMPLATE = "template";

    /**
     * 插件具体实现查看BasePlugin
     * @param properties
     */
    @Override
    public void setProperties(Properties properties) {
        super.setProperties(properties);
    }
}
