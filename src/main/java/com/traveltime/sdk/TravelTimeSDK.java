package com.traveltime.sdk;

import com.traveltime.sdk.dto.requests.TravelTimeRequest;
import com.traveltime.sdk.dto.responses.HttpResponse;
import jakarta.validation.*;
import jakarta.validation.constraints.Positive;
import lombok.*;
import okhttp3.*;

import java.io.IOException;
import java.net.URI;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class TravelTimeSDK {
    private final OkHttpClient client = new OkHttpClient();
    @NonNull
    private String appId;
    @NonNull
    private String apiKey;
    @Builder.Default
    private URI uri = URI.create("https://api.traveltimeapp.com/v4");

    private <T> Optional<String> validate(TravelTimeRequest<T> request) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<TravelTimeRequest<T>>> violations = validator.validate(request);
        return violations.stream().map(ConstraintViolation::getMessage).findFirst();
    }

    public <T> HttpResponse<T> send(final TravelTimeRequest<T> request) {
        Optional<String> validation = validate(request);
        if(validation.isPresent()) {
            return HttpResponse.<T>builder().httpCode(422).errorMessage(validation.get()).build();
        }

        try {
            Call call = client.newCall(request.createRequest(appId, apiKey, uri));
            Response response = call.execute();
            if(response.code() == 200) {
                String responseBody = Objects.requireNonNull(response.body()).string();
                T parsedBody = Json.fromJson(responseBody, request.responseType());
                return HttpResponse.<T>builder().httpCode(200).parsedBody(parsedBody).build();
            } else {
                return HttpResponse.<T>builder().httpCode(response.code()).errorMessage(response.message()).build();
            }
        }
        catch (IOException e) {
            return HttpResponse.<T>builder().httpCode(400).errorMessage(e.getMessage()).build();
        }
    }
}
