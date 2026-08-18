package com.traveltime.sdk.dto.requests.proto;

import com.igeolise.traveltime.rabbitmq.requests.RequestsCommon;
import java.util.Collection;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CellProperty {
    MIN(RequestsCommon.CellPropertyType.MIN),
    MAX(RequestsCommon.CellPropertyType.MAX),
    MEAN(RequestsCommon.CellPropertyType.MEAN);

    private final RequestsCommon.CellPropertyType protoValue;

    /**
     * An empty list requests no statistics at all, which returns cell ids on their own.
     */
    public static List<RequestsCommon.CellPropertyType> toProtoOrAll(List<CellProperty> properties) {
        Collection<CellProperty> effective = properties == null ? EnumSet.allOf(CellProperty.class) : properties;
        return effective.stream().map(CellProperty::getProtoValue).collect(Collectors.toList());
    }
}
