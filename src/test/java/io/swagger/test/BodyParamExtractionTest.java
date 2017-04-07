/*
 *  Copyright 2017 SmartBear Software
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package io.swagger.test;

import com.fasterxml.jackson.databind.JavaType;
import v2.io.swagger.converter.ModelConverters;
import io.swagger.inflector.config.Configuration;
import io.swagger.inflector.utils.ReflectionUtils;
import v2.io.swagger.models.ArrayModel;
import v2.io.swagger.models.Model;
import v2.io.swagger.models.ModelImpl;
import v2.io.swagger.models.RefModel;
import v2.io.swagger.models.parameters.BodyParameter;
import v2.io.swagger.models.parameters.Parameter;
import v2.io.swagger.models.properties.ArrayProperty;
import v2.io.swagger.models.properties.RefProperty;
import v2.io.swagger.models.properties.StringProperty;
import io.swagger.test.models.Person;
import io.swagger.test.models.User;
import org.junit.Before;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.testng.Assert.assertEquals;

public class BodyParamExtractionTest {
    ReflectionUtils utils = new ReflectionUtils();

    @BeforeClass
    @Before
    public void setup() {
        Configuration config = new Configuration();
        config.setModelPackage("io.swagger.test.models");
        config.addModelMapping("User", User.class);

        utils.setConfiguration(config);
    }

    @Test
    public void testStringBodyParam() throws Exception {
        Map<String, Model> definitions = new HashMap<String, Model>();

        Parameter parameter = new BodyParameter().schema(new ModelImpl().type("string"));
        JavaType jt = utils.getTypeFromParameter(parameter, definitions);

        assertEquals(jt.getRawClass(), String.class);
    }

    @org.junit.Test
    public void testUUIDBodyParam() throws Exception {
        Map<String, Model> definitions = new HashMap<String, Model>();

        Parameter parameter = new BodyParameter().schema(new ModelImpl().type("string").format("uuid"));
        JavaType jt = utils.getTypeFromParameter(parameter, definitions);

        assertEquals(jt.getRawClass(), UUID.class);
    }

    @Test
    public void testConvertComplexBodyParamWithConfigMapping() throws Exception {
        Map<String, Model> definitions = new HashMap<String, Model>();

        Parameter parameter = new BodyParameter().schema(new RefModel("#/definitions/User"));
        JavaType jt = utils.getTypeFromParameter(parameter, definitions);

        assertEquals(jt.getRawClass(), User.class);
    }

    @Test
    public void testConvertComplexBodyParamWithoutConfigMapping() throws Exception {
        Map<String, Model> definitions = new HashMap<String, Model>();

        Parameter parameter = new BodyParameter().schema(new RefModel("#/definitions/Person"));
        JavaType jt = utils.getTypeFromParameter(parameter, definitions);

        // will look up from the config model package and ref.simpleName of Person
        assertEquals(jt.getRawClass(), Person.class);
    }

    @Test
    public void testConvertComplexArrayBodyParam() throws Exception {
        Map<String, Model> definitions = ModelConverters.getInstance().read(Person.class);

        Parameter parameter = new BodyParameter()
            .schema(new ArrayModel()
                .items(new RefProperty("#/definitions/Person")));
    
        JavaType jt = utils.getTypeFromParameter(parameter, definitions);
        assertEquals(jt.getRawClass(), Person[].class);
    }

    @Test
    public void testConvertPrimitiveArrayBodyParam() throws Exception {
        Map<String, Model> definitions = ModelConverters.getInstance().read(Person.class);

        Parameter parameter = new BodyParameter()
            .schema(new ArrayModel()
                .items(new StringProperty()));

        JavaType jt = utils.getTypeFromParameter(parameter, definitions);
        assertEquals(jt.getRawClass(), String[].class);
    }

    @org.junit.Test
    public void testConvertDoubleArrayBodyParam() throws Exception {
        Map<String, Model> definitions = ModelConverters.getInstance().read(Person.class);

        Parameter parameter = new BodyParameter()
            .schema(new ArrayModel()
                .items(new ArrayProperty(new StringProperty())));

        JavaType jt = utils.getTypeFromParameter(parameter, definitions);
        assertEquals(jt.getRawClass(), String[][].class);
    }
}
