/*
 *  Copyright 2015 SmartBear Software
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

package io.swagger.test.filters;

import io.swagger.inflector.SwaggerInflector;
import io.swagger.inflector.config.Configuration;
import io.swagger.inflector.config.DefaultObjectFactory;
import io.swagger.inflector.config.HandlerInvocationFilter;
import io.swagger.inflector.config.ObjectFactory;
import io.swagger.inflector.models.RequestContext;
import io.swagger.inflector.models.ResponseContext;
import org.testng.annotations.Test;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.lang.reflect.Method;
import java.util.Arrays;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HandlerInvocationFilterTest {

    public static final String FILTER_CLASS = "io.swagger.test.filter.TestFilter";

    @Test
    public void testHandlerInvocationFilter() throws Exception {

        ObjectFactory objectFactory = mock(ObjectFactory.class);
        final HandlerInvocationFilter filter = mock(HandlerInvocationFilter.class);

        Configuration configuration = Configuration.defaultConfiguration();
        configuration.setSwaggerUrl("src/test/swagger/sample1.yaml");
        configuration.setHandlerInvocationFilters(Arrays.asList(FILTER_CLASS));
        configuration.setObjectFactory(new DefaultObjectFactory() {
            @Override
            public HandlerInvocationFilter instantiateFilter(String filterClass) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
                return filter;
            }
        });

        SwaggerInflector inflector = new SwaggerInflector(configuration);

        ContainerRequestContext containerRequestContext = mock(ContainerRequestContext.class);
        UriInfo uriInfo = mock(UriInfo.class);

        when(containerRequestContext.getUriInfo()).thenReturn(uriInfo);
        when(uriInfo.getPathParameters()).thenReturn(new MultivaluedHashMap<String, String>());

        MultivaluedHashMap<String, String> queryParams = new MultivaluedHashMap<>();
        queryParams.put("users", Arrays.asList("test"));

        when(uriInfo.getQueryParameters()).thenReturn(queryParams);
        when(containerRequestContext.getHeaders()).thenReturn(new MultivaluedHashMap<String, String>());

        Response response = inflector.getControllers().get(0).apply(containerRequestContext);
        assertEquals(200, response.getStatus());

        when(filter.filterResponse((RequestContext) anyObject(),
                (ResponseContext) anyObject(), (Method) anyObject(), (Object[]) anyObject())).thenReturn(new ResponseContext().status(300));

        response = inflector.getControllers().get(0).apply(containerRequestContext);
        assertEquals(300, response.getStatus());

        when(filter.filterRequest((RequestContext) anyObject(), (Method) anyObject(), (Object[]) anyObject())).thenReturn(new ResponseContext().status(400));
        response = inflector.getControllers().get(0).apply(containerRequestContext);
        assertEquals(400, response.getStatus());
    }
}
