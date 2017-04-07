package io.swagger.inflector.utils;

import io.swagger.inflector.Constants;

import v2.io.swagger.core.filter.AbstractSpecFilter;
import v2.io.swagger.model.ApiDescription;
import v2.io.swagger.models.Model;
import v2.io.swagger.models.Operation;
import v2.io.swagger.models.parameters.Parameter;
import v2.io.swagger.models.properties.Property;

import java.util.List;
import java.util.Map;

public class DefaultSpecFilter extends AbstractSpecFilter {

    @Override
    public boolean isOperationAllowed(Operation operation, ApiDescription api, Map<String, List<String>> params, Map<String, String> cookies, Map<String, List<String>> headers) {
        if(operation.getVendorExtensions() != null && operation.getVendorExtensions().containsKey(Constants.X_INFLECTOR_HIDDEN)) {
            return false;
        }
        return true;
    }

    @Override
    public boolean isParamAllowed(Parameter parameter, Operation operation, ApiDescription api, Map<String, List<String>> params, Map<String, String> cookies, Map<String, List<String>> headers) {
        if(parameter.getVendorExtensions() != null && parameter.getVendorExtensions().containsKey(Constants.X_INFLECTOR_HIDDEN)) {
            return false;
        }
        return true;
    }

    @Override
    public boolean isPropertyAllowed(Model model, Property property, String propertyName, Map<String, List<String>> params, Map<String, String> cookies, Map<String, List<String>> headers) {
        if(property.getVendorExtensions() != null && property.getVendorExtensions().containsKey(Constants.X_INFLECTOR_HIDDEN)) {
            return false;
        }
        return true;
    }

    @Override
    public boolean isRemovingUnreferencedDefinitions() {
        return false;
    }
}
