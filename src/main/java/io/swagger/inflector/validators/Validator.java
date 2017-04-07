package io.swagger.inflector.validators;

import v2.io.swagger.models.parameters.Parameter;

import java.util.Iterator;

public interface Validator {
    void validate(Object o, Parameter parameter, Iterator<Validator> next) throws ValidationException;
}