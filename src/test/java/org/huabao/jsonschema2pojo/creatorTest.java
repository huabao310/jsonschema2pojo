package org.huabao.jsonschema2pojo;

import org.huabao.codemodel.JCodeModel;
import org.huabao.jsonschema2pojo.*;
import org.huabao.jsonschema2pojo.rules.RuleFactory;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class creatorTest {
    @Test
    public void test() throws IOException {
        JCodeModel codeModel = new JCodeModel();
        GenerationConfig config = new DefaultGenerationConfig();

        String className = "LoginRequest";
        String packageName = "org.huabao";
        String outputDir = "/Users/yaoyang/test";
        String jsonSchemaContent = "{\n" +
                "  \"type\": \"object\",\n" +
                "  \"title\": \"empty object\",\n" +
                "  \"properties\": {\n" +
                "    \"email\": {\n" +
                "      \"type\": \"string\",\n" +
                "      \"description\": \"邮箱\",\n" +
                "      \"format\": \"email\",\n" +
                "      \"pattern\": \"\\\\w+$\",\n" +
                "      \"minLength\": 11,\n" +
                "      \"maxLength\": 22\n" +
                "    },\n" +
                "    \"password\": {\n" +
                "      \"type\": \"string\",\n" +
                "      \"description\": \"密码\",\n" +
                "      \"format\": \"uri\"\n" +
                "    },\n" +
                "    \"publisherId\": {\n" +
                "      \"type\": \"integer\",\n" +
                "      \"description\": \"出版商id\",\n" +
                "      \"minimum\": 33,\n" +
                "      \"maximum\": 44\n" +
                "    },\n" +
                "    \"clientCode\": {\n" +
                "      \"type\": \"number\",\n" +
                "      \"description\": \"客户端编号（OFFICIAL 官网客户端；EDIT 投稿管理端）\"\n" +
                "    },\n" +
                "    \"verCode\": {\n" +
                "      \"type\": \"array\",\n" +
                "      \"items\": {\n" +
                "        \"type\": \"string\"\n" +
                "      },\n" +
                "      \"description\": \"验证码\",\n" +
                "      \"minItems\": 55,\n" +
                "      \"maxItems\": 66\n" +
                "    },\n" +
                "    \"person\": {\n" +
                "      \"type\": \"object\",\n" +
                "      \"properties\": {}\n" +
                "    },\n" +
                "    \"loginDate\": {\n" +
                "      \"type\": \"string\",\n" +
                "      \"format\": \"date-time\"\n" +
                "    }\n" +
                "  },\n" +
                "  \"required\": [\n" +
                "    \"email\",\n" +
                "    \"password\",\n" +
                "    \"publisherId\",\n" +
                "    \"loginDate\",\n" +
                "    \"clientCode\",\n" +
                "    \"verCode\",\n" +
                "    \"person\"\n" +
                "  ]\n" +
                "}";

        RuleFactory ruleFactory = new RuleFactory(config, new Jackson2Annotator(config), new SchemaStore());
        SchemaMapper mapper = new SchemaMapper(ruleFactory, new SchemaGenerator());
        mapper.generate(codeModel, className, packageName, jsonSchemaContent);
        codeModel.build(new File(outputDir));

    }
}
