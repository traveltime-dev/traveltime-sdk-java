package com.traveltime.sdk.server_tests.dto;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;

public class TestServerResponseDeserializerBootstrap
    extends JsonDeserializer<TestServerResponse<?>>
    implements ContextualDeserializer {

    // Jackson needs this no-arg ctor to instantiate the bootstrap
    public TestServerResponseDeserializerBootstrap() {
    }

    @Override
    public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty prop)
        throws JsonMappingException {

        // Figure out the target generic: TestServerResponse<Details>
        JavaType target = (prop != null) ? prop.getType() : ctxt.getContextualType();
        if (target == null) {
            throw JsonMappingException.from(ctxt,
                "Cannot resolve generic parameter for TestServerResponse<?>. " +
                    "Use TypeReference<TestServerResponse<...>> at the call site.");
        }

        // Extract the single type parameter (Details)
        JavaType detailsType = target.getBindings().getBoundType(0);
        if (detailsType == null || detailsType.hasRawClass(Object.class)) {
            throw JsonMappingException.from(ctxt,
                "Missing concrete Details type for TestServerResponse<?>. " +
                    "Deserialize with a TypeReference<TestServerResponse<YourDetails>>.");
        }

        // Hand off to a fully-bound deserializer
        return new TestServerResponseDeserializer(detailsType);
    }

    @Override
    public TestServerResponse<?> deserialize(JsonParser p, DeserializationContext ctxt) {
        // Jackson should never call this one after contextualization; the bound instance handles it.
        throw new IllegalStateException("Bootstrap deserializer should be contextualized before use");
    }
}