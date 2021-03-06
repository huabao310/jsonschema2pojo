/**
 * Copyright © 2010-2020 Nokia
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.huabao.jsonschema2pojo.rules;

import javax.validation.constraints.Pattern;

import org.huabao.jsonschema2pojo.Schema;

import com.fasterxml.jackson.databind.JsonNode;
import org.huabao.codemodel.JAnnotationUse;
import org.huabao.codemodel.JFieldVar;

public class PatternRule implements Rule<JFieldVar, JFieldVar> {

    private RuleFactory ruleFactory;

    public PatternRule(RuleFactory ruleFactory) {
        this.ruleFactory = ruleFactory;
    }

    @Override
    public JFieldVar apply(String nodeName, JsonNode node, JsonNode parent, JFieldVar field, Schema currentSchema) {
        if (ruleFactory.getGenerationConfig().isIncludeJsr303Annotations() && isApplicableType(field)) {
            JAnnotationUse annotation = field.annotate(Pattern.class);
            annotation.param("regexp", node.asText());
            annotation.param("message", "正则表达式：" + node.asText() + " 校验失败");
        }

        return field;
    }

    private boolean isApplicableType(JFieldVar field) {
        try {
            Class<?> fieldClass = Class.forName(field.type().boxify().fullName());
            return String.class.isAssignableFrom(fieldClass);
        } catch (ClassNotFoundException ignore) {
            return false;
        }
    }

}
