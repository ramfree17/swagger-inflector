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

/**
 * Default ObjectFactory implementation that just calls newInstance
 */

public class DefaultObjectFactory extends DefaultControllerFactory implements ObjectFactory {

    /**
     * Instantiates the provided filter class calling cls.newInstance()
     *
     * @param filterClass name of the class to be instantiated
     * @return an instance of the specified class
     */

    @Override
    public HandlerInvocationFilter instantiateFilter(String filterClass) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        return (HandlerInvocationFilter) Class.forName(filterClass).newInstance();
    }
}
