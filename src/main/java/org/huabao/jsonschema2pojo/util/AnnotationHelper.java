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

package org.huabao.jsonschema2pojo.util;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.huabao.codemodel.JAnnotationUse;
import org.huabao.codemodel.JClass;
import org.huabao.codemodel.JDefinedClass;

public class AnnotationHelper {
    public static final String JAVA_8_GENERATED = "javax.annotation.Generated";
    public static final String JAVA_9_GENERATED = "javax.annotation.processing.Generated";
    public static final String JSON_IGNORE_PROPERTIES = "com.fasterxml.jackson.annotation.JsonIgnoreProperties";
    public static final String GENERATOR_NAME = "jsonschema2pojo";

    public static void addGeneratedAnnotation(JDefinedClass jclass, String annotationClassName, String paramName, Object paramValue) {
        try {
            Class.forName(annotationClassName);
            JClass annotationClass = jclass.owner().ref(annotationClassName);
            JAnnotationUse generated = jclass.annotate(annotationClass);

            if (paramName == null){
                return;
            }

            if (paramValue instanceof String) {
                generated.param(paramName, (String) paramValue);
            } else if (paramValue instanceof Boolean) {
                generated.param(paramName, (Boolean) paramValue);
            } else if (paramValue instanceof Integer) {
                generated.param(paramName, (Integer) paramValue);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
