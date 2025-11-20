package com.traveltime.sdk.server_tests;

import com.traveltime.sdk.dto.common.Location;
import com.traveltime.sdk.dto.requests.*;
import com.traveltime.sdk.dto.requests.timefilter.ArrivalSearch;
import com.traveltime.sdk.dto.requests.timefilter.DepartureSearch;
import com.traveltime.sdk.dto.requests.timefilterfast.ArrivalSearches;
import com.traveltime.sdk.server_tests.values.Endpoint;
import com.traveltime.sdk.server_tests.values.TestName;
import lombok.*;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

public class TimeFilterFastTest extends BaseEndpointTest {

    @Override
    protected Endpoint endpoint() {
        return new Endpoint("/v4/time-filter/fast");
    }

    private final class TimeFilterTestRequest extends TimeFilterFastRequest {
        TestName testName;

        public TimeFilterTestRequest(
            TestName testName,
            @NonNull List<Location> locations,
            @NonNull ArrivalSearches arrivalSearches
        ) {
            super(locations, arrivalSearches);
            this.testName = testName;
        }
    }

    @Test
    public void batching() {
        val testName = new TestName("batching");
    }
}
