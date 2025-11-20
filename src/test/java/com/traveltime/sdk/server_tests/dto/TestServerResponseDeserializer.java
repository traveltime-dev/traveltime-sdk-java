package com.traveltime.sdk.server_tests.dto;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.traveltime.sdk.server_tests.dto.details.NoDetails;
import lombok.NonNull;

import java.io.IOException;

public class TestServerResponseDeserializer
    extends JsonDeserializer<TestServerResponse<?>> {

    private final JavaType detailsType;

    public TestServerResponseDeserializer(@NonNull JavaType detailsType) {
        this.detailsType = detailsType;
    }

    @Override
    public TestServerResponse<?> deserialize(JsonParser parser, DeserializationContext context)
        throws IOException {

        ObjectMapper mapper = (ObjectMapper) parser.getCodec();
        ObjectNode root = mapper.readTree(parser);

        @NonNull TestServerResponse.Status status =
            mapper.treeToValue(root.get("status"), TestServerResponse.Status.class);
        int code = root.path("code").asInt();
        @NonNull String type = root.path("type").asText();
        @NonNull String message = root.path("message").asText();
        Object details = detailsType.hasRawClass(NoDetails.class) ?
            new NoDetails() :
            mapper.readerFor(detailsType).readValue(getDetailsNode(root, type));

        return new TestServerResponse<>(status, code, type, message, details);
    }

    JsonNode getDetailsNode(ObjectNode root, String type) {
        JsonNode detailsNode = root.path("details");
        return type.equals("TestStarted") ? detailsNode.get("test") : detailsNode;
    }
}
