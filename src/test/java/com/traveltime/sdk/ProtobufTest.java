package com.traveltime.sdk;


import com.igeolise.traveltime.rabbitmq.requests.RequestsCommon;
import com.igeolise.traveltime.rabbitmq.requests.TimeFilterFastRequestOuterClass.TimeFilterFastRequest;
import com.igeolise.traveltime.rabbitmq.responses.TimeFilterFastResponseOuterClass.TimeFilterFastResponse;
import com.traveltime.sdk.dto.common.Coordinates;
import okhttp3.*;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.util.Objects;

public class ProtobufTest {
    @Test
    public void shouldSendTimeMapRequest() throws IOException {
        Coordinates origin = new Coordinates(51.425709, -0.122061);
        Coordinates destination = new Coordinates(51.348605, -0.314783);
        double mult = Math.pow(10, 5);


        TimeFilterFastRequest.OneToMany oneToMany = TimeFilterFastRequest.OneToMany
                .newBuilder()
                .setDepartureLocation(RequestsCommon.Coords.newBuilder().setLat((float)origin.getLat()).setLng((float)origin.getLng()).build())
                .setArrivalTimePeriod(RequestsCommon.TimePeriod.WEEKDAY_MORNING)
                .setTransportation(RequestsCommon.Transportation.newBuilder().setType(RequestsCommon.TransportationType.DRIVING_AND_FERRY).build())
                .setTravelTime(7200)
                .addLocationDeltas((int)Math.round((destination.getLat() - origin.getLat()) * mult))
                .addLocationDeltas((int)Math.round((destination.getLng() - origin.getLng()) * mult))
                .build();

        TimeFilterFastRequest timeFilterRequest = TimeFilterFastRequest.newBuilder().setOneToManyRequest(oneToMany).build();

        OkHttpClient client = new OkHttpClient();

        String credential = Credentials.basic("crude-curl", "qf3ywym4omkzqhonjxthgvlmigjand7p3cvfwyipcybu3odb");

        Request request = new Request.Builder()
                .url("https://proto.api.traveltimeapp.com/api/v2/nl/time-filter/fast/driving+ferry")
                .addHeader("Authorization", credential)
                .addHeader("Content-Type", "application/octet-stream")
                .post(RequestBody.create(timeFilterRequest.toByteArray()))
                .build();

        Response response = client.newCall(request).execute();

        TimeFilterFastResponse res = TimeFilterFastResponse.parseFrom(Objects.requireNonNull(response.body()).bytes());

        System.out.println(res.getProperties().getTravelTimesList() );
        System.out.println(response.code());

        Assert.assertTrue(response.isSuccessful());

    }
}
