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

package org.huabao.jsonschema2pojo.model;

import org.huabao.jsonschema2pojo.rules.EnumRule;
import org.huabao.jsonschema2pojo.rules.RuleFactory;

public enum EnumDefinitionExtensionType {

    /**
     * Enum defined just by JSON Schema
     */
    NONE,

    /**
     * Enum defined by JSON Schema and javaEnumNames jsonschema2pojo extension.
     */
    JAVA_ENUM_NAMES,

    /**
     * Enum defined by JSON Schema and javaEnums jsonschema2pojo extension.
     */
    JAVA_ENUMS,

    /**
     * Enum defined by JSON Schema and a custom jsonschema2pojo extension,
     * defined by custom {@link EnumRule} implementation provided by custom {@link RuleFactory}.
     */
    CUSTOM
}
