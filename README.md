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

APP_ID and API_Key for base requests:

```java
    TravelTimeCredentials credentials = new KeyAuth("APP_ID", "API_KEY");
    TravelTimeSDK sdk = new TravelTimeSDK(credentials);
```

USERNAME and PASSWORD for proto requests:

```java
    TravelTimeCredentials credentials = new BaseAuth("PROTO_USERNAME","PROTO_PASSWORD");
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
* exclude_location_types: Exclude location types from results. Available values: "country".

```java
    GeocodingRequest request = GeocodingRequest
        .builder()
        .query("Geneva")
        .withinCountries(Arrays.asList("CH", "DE"))
        .limit(1)
        .build();

    Either<TravelTimeError, FeatureCollection> response = sdk.send(request);
    
    if(response.isRight()) {
        System.out.println(response.get().getMaps().size());
    } else {
        System.out.println(response.getLeft().getMessage());
    }
```

### Time Filter Fast (Proto)
Filter out points that cannot be reached within specified time limit.

Body attributes:
* origin: Original point.
* destination: destination points. Cannot be more than 200,000.
* transportation: transportation type.
* travelTime: time limit;
* country: return the results that are within the specified country

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
    
    if(response.isRight()) {
        System.out.println(response.get().getMaps().size());
    } else {
        System.out.println(response.getLeft().getMessage());
    }
```

## Support

If you have problems, please write an issue or contact us by writing to support@traveltime.com


