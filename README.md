# TravelTime JAVA SDK
[![Artifact publish](https://github.com/traveltime-dev/traveltime-sdk-java/actions/workflows/publish-artifact.yaml/badge.svg?branch=master)](https://github.com/traveltime-dev/traveltime-sdk-java/actions/workflows/publish-artifact.yaml)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.traveltime/traveltime-sdk-java/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.traveltime/traveltime-sdk-java)

This open-source library allows you to access [TravelTime API](http://docs.traveltime.com/overview/introduction) endpoints. TravelTime SDK is published for Java 8.

## Maven, Java package

All annotations are in Java package `com.traveltime.sdk`.
To use annotations, you need to use Maven dependency:

```xml
<dependency>
    <groupId>com.traveltime</groupId>
    <artifactId>traveltime-sdk-java</artifactId>
    <version>${traveltime-sdk-version}</version>
</dependency>
```

## SDK usage

All requests return TravelTime response body.
On invalid request functions will return API error response.
Check indvidual function documentation or API documention for information on how to construct it.

### Authentication
In order to authenticate with Travel Time API, you will have to supply the Credentials.

```java
TravelTimeCredentials credentials = new TravelTimeCredentials("APP_ID", "API_KEY");
TravelTimeSDK sdk = new TravelTimeSDK(credentials);
```

### [Isochrones (Time Map)](https://traveltime.com/docs/api/reference/isochrones)
Given origin coordinates, find shapes of zones reachable within corresponding travel time.
Find unions/intersections between different searches

Body attributes:
* departure_searches: Searches based on departure times.
  Leave departure location at no earlier than given time. You can define a maximum of 10 searches
* arrival_searches: Searches based on arrival times.
  Arrive at destination location at no later than given time. You can define a maximum of 10 searches
* unions: Define unions of shapes that are results of previously defined searches.
* intersections: Define intersections of shapes that are results of previously defined searches.

```java
DepartureSearch departureSearch1 = DepartureSearch
    .builder()
    .id("Public transport from Trafalgar Square")
    .coords(new Coordinates(51.507609, -0.128315))
    .transportation(PublicTransport.builder().build())
    .departureTime(Instant.now())
    .travelTime(900)
    .build();

DepartureSearch departureSearch2 = DepartureSearch
    .builder()
    .id("Driving from Trafalgar Square")
    .coords(new Coordinates(51.507609, -0.128315))
    .transportation(Driving.builder().build())
    .departureTime(Instant.now())
    .travelTime(900)
    .build();

ArrivalSearch arrivalSearch = ArrivalSearch
    .builder()
    .id("Public transport to Trafalgar Square")
    .coords(new Coordinates(51.507609, -0.128315))
    .transportation(Driving.builder().build())
    .arrivalTime(Instant.now())
    .travelTime(900)
    .build();

Union union = Union
    .builder()
    .id("Union of driving and public transport")
    .searchIds(Arrays.asList("Public transport from Trafalgar Square", "Driving from Trafalgar Square"))
    .build();

Intersection intersection = Intersection
    .builder()
    .id("Intersection of driving and public transport")
    .searchIds(Arrays.asList("Public transport from Trafalgar Square", "Driving from Trafalgar Square"))
    .build();

TimeMapRequest request = TimeMapRequest
    .builder()
    .departureSearches(Arrays.asList(departureSearch1, departureSearch2))
    .arrivalSearches(Collections.singletonList(arrivalSearch))
    .unions(Collections.singletonList(union))
    .intersections(Collections.singletonList(intersection))
    .build();

Either<TravelTimeError, TimeMapResponse> response = sdk.send(request);

if(response.isRight()) {
    System.out.println(response.get().getResults().size());
} else {
    System.out.println(response.getLeft().getMessage());
}
```


### [Distance Matrix (Time Filter)](https://traveltime.com/docs/api/reference/distance-matrix)
Given origin and destination points filter out points that cannot be reached within specified time limit.
Find out travel times, distances and costs between an origin and up to 2,000 destination points.

Body attributes:
* locations: Locations to use. Each location requires an id and lat/lng values
* departure_searches: Searches based on departure times.
  Leave departure location at no earlier than given time. You can define a maximum of 10 searches
* arrival_searches: Searches based on arrival times.
  Arrive at destination location at no later than given time. You can define a maximum of 10 searches


```java
List<Location> locations = Arrays.asList(
    new Location("London center", new Coordinates(51.508930,-0.131387)),
    new Location("Hyde Park", new Coordinates(51.508824,-0.167093)),
    new Location("ZSL London Zoo", new Coordinates(51.536067,-0.153596))
);

DepartureSearch departureSearch = DepartureSearch
    .builder()
    .id("Forward search example")
    .departureLocationId("London center")
    .arrivalLocationIds(Arrays.asList("Hyde Park", "ZSL London Zoo"))
    .transportation(PublicTransport.builder().build())
    .departureTime(Instant.now())
    .travelTime(1800)
    .properties(Arrays.asList(Property.TRAVEL_TIME, Property.DISTANCE, Property.ROUTE))
    .build();

ArrivalSearch arrivalSearch = ArrivalSearch
    .builder()
    .id("Backward search example")
    .departureLocationIds(Arrays.asList("Hyde Park", "ZSL London Zoo"))
    .arrivalLocationId("London center")
    .transportation(PublicTransport.builder().build())
    .arrivalTime(Instant.now())
    .travelTime(900)
    .properties(Arrays.asList(Property.TRAVEL_TIME, Property.DISTANCE, Property.ROUTE, Property.FARES))
    .range(FullRange.builder().enabled(true).maxResults(3).width(600).build())
    .build();

TimeFilterRequest request = TimeFilterRequest
    .builder()
    .locations(locations)
    .arrivalSearches(Collections.singletonList(arrivalSearch))
    .departureSearches(Collections.singletonList(departureSearch))
    .build();
    
Either<TravelTimeError, TimeFilterResponse> response = sdk.send(request);   
    
if(response.isRight()) {
    System.out.println(response.get().getResults().size());
} else {
    System.out.println(response.getLeft().getMessage());
}
```

### [Routes](https://traveltime.com/docs/api/reference/routes)
Returns routing information between source and destinations.

Body attributes:
* locations: Locations to use. Each location requires an id and lat/lng values
* departure_searches: Searches based on departure times.
  Leave departure location at no earlier than given time. You can define a maximum of 10 searches
* arrival_searches: Searches based on arrival times.
  Arrive at destination location at no later than given time. You can define a maximum of 10 searches
  
```java
List<Location> locations = Arrays.asList(
    new Location("London center", new Coordinates(51.508930,-0.131387)),
    new Location("Hyde Park", new Coordinates(51.508824,-0.167093)),
    new Location("ZSL London Zoo", new Coordinates(51.536067,-0.153596))
);

DepartureSearch departureSearch = DepartureSearch
    .builder()
    .id("Departure search example")
    .departureLocationId("London center")
    .arrivalLocationIds(Arrays.asList("Hyde Park", "ZSL London Zoo"))
    .transportation(Driving.builder().build())
    .departureTime(Instant.now())
    .properties(Arrays.asList(Property.TRAVEL_TIME, Property.DISTANCE, Property.ROUTE))
    .build();
    
ArrivalSearch arrivalSearch = ArrivalSearch
    .builder()
    .id("Arrival search example")
    .arrivalLocationId("London center")
    .departureLocationIds(Arrays.asList("Hyde Park", "ZSL London Zoo"))
    .transportation(PublicTransport.builder().build())
    .arrivalTime(Instant.now())
    .properties(Arrays.asList(Property.TRAVEL_TIME, Property.DISTANCE, Property.ROUTE, Property.FARES))
    .range(FullRange.builder().enabled(true).maxResults(3).width(1800).build())
    .build();

RoutesRequest request = RoutesRequest
    .builder()
    .locations(locations)
    .departureSearches(Collections.singletonList(departureSearch))
    .arrivalSearches(Collections.singletonList(arrivalSearch))
    .build();
    
Either<TravelTimeError, RoutesResponse> response = sdk.send(request);

if(response.isRight()) {
    System.out.println(response.get().getResults().size());
} else {
    System.out.println(response.getLeft().getMessage());
}
```

### [Map Info](https://traveltime.com/docs/api/reference/map-info)
Get information about currently supported countries and find out what points supported by the api.

```java
MapInfoRequest request = new MapInfoRequest();
Either<TravelTimeError, MapInfoResponse> response = sdk.send(request);

if(response.isRight()) {
    System.out.println(response.get().getMaps().size());
} else {
    System.out.println(response.getLeft().getMessage());
}
```

### [Geocoding (Search)](https://traveltime.com/docs/api/reference/geocoding-search)
Match a query string to geographic coordinates.

Body attributes:
* query: A query to geocode. Can be an address, a postcode or a venue.
* within_country: Only return the results that are within the specified country.
  If no results are found it will return the country itself. Format:ISO 3166-1 alpha-2 or alpha-3

```java
GeocodingRequest request = GeocodingRequest
    .builder()
    .query("Geneva")
    .withinCountries(Arrays.asList("CH", "DE"))
    .limit(1)
    .build();

Either<TravelTimeError, GeocodingResponse> response = sdk.send(request);
    
if(response.isRight()) {
    System.out.println(response.get());
} else {
    System.out.println(response.getLeft().getMessage());
}
```

### Time Filter Fast (Proto)

A fast version of time filter communicating using [protocol buffers](https://github.com/protocolbuffers/protobuf).

The request parameters are much more limited and only travel time is returned. In addition, the results are only approximately correct (95% of the results are guaranteed to be within 5% of the routes returned by regular time filter).

This inflexibility comes with a benefit of faster response times (Over 5x faster compared to regular time filter) and larger limits on the amount of destination points.

Body attributes:
* origin: Origin point.
* destination: Destination points. Cannot be more than 200,000.
* transportation: Transportation type.
* travelTime: Time limit;
* country: Return the results that are within the specified country

```java
OneToMany oneToMany = OneToMany
    .builder()
    .originCoordinate(new Coordinates(51.425709, -0.122061))
    .destinationCoordinates(Collections.singletonList(new Coordinates(51.348605, -0.314783)))
    .transportation(Transportation.DRIVING_FERRY)
    .travelTime(7200)
    .country(Country.NETHERLANDS)
    .build();

TimeFilterFastProtoRequest request = TimeFilterFastProtoRequest
    .builder()
    .oneToMany(oneToMany)
    .build();

Either<TravelTimeError, TimeFilterFastProtoResponse> response = sdk.sendProto(request);

if(response.isRight()) {
    System.out.println(response.get().getTravelTimes());
} else {
    System.out.println(response.getLeft().getMessage());
}
```

The responses are in the form of a list where each position denotes either a
travel time (in seconds) of a journey, or if negative that the journey from the
origin to the destination point is impossible.

### Time Filter Fast with distance (Proto)

A fast version of time filter communicating using [protocol buffers](https://github.com/protocolbuffers/protobuf) that supports distance information.

The request parameters are much more limited and only travel time and distance is returned. In addition, the results are only approximately correct (95% of the results are guaranteed to be within 5% of the routes returned by regular time filter).

Body attributes:
* origin: Origin point.
* destination: Destination points. Cannot be more than 200,000.
* transportation: Transportation type.
* travelTime: Time limit;
* country: Return the results that are within the specified country

```java
TimeFilterFastProtoDistanceRequest request = new TimeFilterFastProtoDistanceRequest(
    new Coordinates(51.425709, -0.122061),
    Collections.singletonList(new Coordinates(51.425600, -0.122000)), 
    3200,
    Transportation.DRIVING_FERRY,
    Country.UNITED_KINGDOM      
);

Either<TravelTimeError, TimeFilterFastProtoDistanceResponse> response = sdk.sendProto(request);

if(response.isRight()) {
    System.out.println(response.get().getDistances());
} else {
    System.out.println(response.getLeft().getMessage());
}
```

The responses are in the form of lists where each position denotes:
  * travel time (in seconds) of a journey, or if negative that the journey from the origin to the destination point is impossible.
  * if a travel time for a position is non negative than the distance list at the same position denotes travel distance in meters for that journey, if the journey is impossible the distance value at the position is *undefined*.

### Passing custom parameters 
In order to pass custom parameters, you will have to create TravelTimeSDK builder.

```java
TravelTimeCredentials credentials = new TravelTimeCredentials("APP_ID", "API_KEY");

URI baseUri = "BASE_URI";

OkHttpClient client = new OkHttpClient
    .Builder()
    .callTimeout(120, TimeUnit.SECONDS)  
    .build();

TravelTimeSDK sdk = TravelTimeSDK
    .builder()
    .baseUri(baseUri)
    .credentials(credentials)
    .client(client)
    .build();
```

## Support

If you have problems, please write an issue or contact us by writing to support@traveltime.com
