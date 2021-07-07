/**
 * Copyright © 2010-2020 Nokia
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.huabao.jsonschema2pojo.rules;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Nonnull;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.huabao.jsonschema2pojo.Schema;

import com.fasterxml.jackson.databind.JsonNode;
import org.huabao.codemodel.JDefinedClass;
import org.huabao.codemodel.JDocComment;
import org.huabao.codemodel.JDocCommentable;
import org.huabao.codemodel.JFieldVar;
import org.huabao.codemodel.JMethod;
import org.huabao.codemodel.JType;

/**
 * Applies the "required" JSON schema rule.
 *
 * @see <a
 * href="http://tools.ietf.org/html/draft-fge-json-schema-validation-00#section-5.4.3">http://tools.ietf.org/html/draft-fge-json-schema-validation-00#section-5.4.3</a>
 */
public class RequiredArrayRule implements Rule<JDefinedClass, JDefinedClass> {

    private final RuleFactory ruleFactory;

    public static final String REQUIRED_COMMENT_TEXT = "\n(Required)";

    protected RequiredArrayRule(RuleFactory ruleFactory) {
        this.ruleFactory = ruleFactory;
    }

    @Override
    public JDefinedClass apply(String nodeName, JsonNode node, JsonNode parent, JDefinedClass jclass, Schema schema) {
        List<String> requiredFieldMethods = new ArrayList<>();

        JsonNode properties = schema.getContent().get("properties");

        for (Iterator<JsonNode> iterator = node.elements(); iterator.hasNext(); ) {
            String requiredArrayItem = iterator.next().asText();
            if (requiredArrayItem.isEmpty()) {
                continue;
            }

            JsonNode propertyNode = null;

            if (properties != null) {
                propertyNode = properties.findValue(requiredArrayItem);
            }

            String fieldName = ruleFactory.getNameHelper().getPropertyName(requiredArrayItem, propertyNode);
            JFieldVar field = jclass.fields().get(fieldName);

            if (field == null) {
                continue;
            }

            addJavaDoc(field);

            if (ruleFactory.getGenerationConfig().isIncludeJsr303Annotations()) {
                String messagePrefix = propertyNode.get("description") == null ?
                        field.name() : propertyNode.get("description").textValue();
                String message = messagePrefix + "不能为空";
                String type = field.type().name();
                if ("String".equals(type)){
                    field.annotate(NotBlank.class).param("message", message);
                } else if (type.startsWith("List<")) {
                    field.annotate(NotEmpty.class).param("message", message);
                } else {
                    field.annotate(NotNull.class).param("message", message);
                }
            }

            if (ruleFactory.getGenerationConfig().isIncludeJsr305Annotations()) {
                addNonnullAnnotation(field);
            }

            requiredFieldMethods.add(getGetterName(fieldName, field.type(), node));
            requiredFieldMethods.add(getSetterName(fieldName, node));
        }

        updateGetterSetterJavaDoc(jclass, requiredFieldMethods);

        return jclass;
    }

    private void updateGetterSetterJavaDoc(JDefinedClass jclass, List<String> requiredFieldMethods) {
        for (Iterator<JMethod> methods = jclass.methods().iterator(); methods.hasNext(); ) {
            JMethod method = methods.next();
            if (requiredFieldMethods.contains(method.name())) {
                addJavaDoc(method);
            }
        }
    }

    private void addNonnullAnnotation(JFieldVar field) {
        field.annotate(Nonnull.class);
    }

    private void addJavaDoc(JDocCommentable docCommentable) {
        JDocComment javadoc = docCommentable.javadoc();
        javadoc.append(REQUIRED_COMMENT_TEXT);
    }

    private String getSetterName(String propertyName, JsonNode node) {
        return ruleFactory.getNameHelper().getSetterName(propertyName, node);
    }

    private String getGetterName(String propertyName, JType type, JsonNode node) {
        return ruleFactory.getNameHelper().getGetterName(propertyName, type, node);
    }

}
