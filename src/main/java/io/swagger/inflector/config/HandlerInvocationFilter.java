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

package io.swagger.inflector.config;

import io.swagger.inflector.models.RequestContext;
import io.swagger.inflector.models.ResponseContext;
import io.swagger.inflector.utils.ApiException;

import java.lang.reflect.Method;

public interface HandlerInvocationFilter {

    /**
     * Called for each controller invocation allowing for pre processing of requests
     *
     * @param requestContext the current RequestContext
     * @param targetMethod   the method that will be called on the target controller
     * @param args           the target method arguments
     * @return if a responseContext is returned that will be returned to the user - return null if processing should continue as normal
     * @throws ApiException if an error should be returned to the user
     */

    ResponseContext filterRequest(RequestContext requestContext, Method targetMethod, Object[] args) throws ApiException;

    /**
     * Called for each controller invocation allowing for post processing of responses
     *
     * @param requestContext  the current RequestContext
     * @param responseContext the current ResponseContext
     * @param targetMethod    the target method that returned the responseContext
     * @param args            the target method arguments
     * @return the response to return to the client
     */

    ResponseContext filterResponse(RequestContext requestContext, ResponseContext responseContext, Method targetMethod, Object[] args) throws ApiException;
}
