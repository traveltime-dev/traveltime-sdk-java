package com.traveltime.sdk.dto.responses;

import com.traveltime.sdk.dto.responses.proto.ProtoResult;
import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.List;

@Value
@AllArgsConstructor
public class FullProtoResponse {
    List<ProtoResult> protoResults;
}
