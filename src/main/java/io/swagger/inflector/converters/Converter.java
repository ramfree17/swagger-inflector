package io.swagger.inflector.converters;

import v2.io.swagger.models.Model;
import v2.io.swagger.models.parameters.Parameter;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public interface Converter {
    Object convert(List<String> value, Parameter parameter, Class<?> cls, Map<String, Model> definitions, Iterator<Converter> chain) throws ConversionException;
}