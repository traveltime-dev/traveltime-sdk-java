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
In order to authenticate with Travel Time API, you will have to supply the Application Id and Api Key.

```java
    TravelTimeSDK sdk = new TravelTimeSDK("APP_ID", "API_KEY");
```

### [Isochrones (Time Map)](https://traveltime.com/docs/api/reference/isochrones)
Given origin coordinates, find shapes of zones reachable within corresponding travel time.
Find unions/intersections between different searches

Body attributes:
* departure_searches ( optional): Searches based on departure times.
  Leave departure location at no earlier than given time. You can define a maximum of 10 searches
* arrival_searches ( optional): Searches based on arrival times.
  Arrive at destination location at no later than given time. You can define a maximum of 10 searches
* unions ( optional): Define unions of shapes that are results of previously defined searches.
* intersections ( optional): Define intersections of shapes that are results of previously defined searches.

```java
    DepartureSearch departureSearch1 = new DepartureSearch(
        "Public transport from Trafalgar Square",
        new Coordinates(51.507609, -0.128315),
        new PublicTransport(),
        Date.from(Instant.now()),
        900
    );

    DepartureSearch departureSearch2 = new DepartureSearch(
        "Driving from Trafalgar Square",
        new Coordinates(51.507609, -0.128315),
        new Driving(),
        Date.from(Instant.now()),
        900
    );

    ArrivalSearch arrivalSearch = new ArrivalSearch(
        "Public transport to Trafalgar Square",
        new Coordinates(51.507609, -0.128315),
        new PublicTransport(),
        Date.from(Instant.now()),
        900
    );

    Union union = new Union(
        "Union of driving and public transport",
        Arrays.asList("Public transport from Trafalgar Square", "Driving from Trafalgar Square")
    );

    Intersection intersection = new Intersection(
        "Intersection of driving and public transport",
        Arrays.asList("Public transport from Trafalgar Square", "Driving from Trafalgar Square")
    );

    TimeMapRequest request = new TimeMapRequest(
        Arrays.asList(departureSearch1, departureSearch2),
        Collections.singletonList(arrivalSearch),
        Collections.singletonList(intersection),
        Collections.singletonList(union)
    );

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
* locations (Array of Objects): Locations to use. Each location requires an id and lat/lng values
* departure_searches (Array of Objects, optional): Searches based on departure times.
  Leave departure location at no earlier than given time. You can define a maximum of 10 searches
* arrival_searches (Array of Objects, optional): Searches based on arrival times.
  Arrive at destination location at no later than given time. You can define a maximum of 10 searches


```java
    List<Location> locations = Arrays.asList(
        new Location("London center", new Coordinates(51.508930,-0.131387)),
        new Location("Hyde Park", new Coordinates(51.508824,-0.167093)),
        new Location("ZSL London Zoo", new Coordinates(51.536067,-0.153596))
    );

    DepartureSearch departureSearch = new DepartureSearch(
        "Forward search example",
        "London center",
        Arrays.asList("Hyde Park", "ZSL London Zoo"),
        new PublicTransport(),
        Date.from(Instant.now()),
        1800,
        Arrays.asList(Property.TRAVEL_TIME, Property.DISTANCE, Property.ROUTE) 
    );  
    
    ArrivalSearch arrivalSearch = new ArrivalSearch(
        "Backward search example",
        Arrays.asList("Hyde Park", "ZSL London Zoo"),
        "London center",
        new PublicTransport(),
        Date.from(Instant.now()),
        1900,
        Arrays.asList(Property.TRAVEL_TIME, Property.DISTANCE, Property.ROUTE, Property.FARES),
        new FullRange(true, 3, 600) 
    );  
    
    TimeFilterRequest request = new TimeFilterRequest(
        locations,
        Collections.singletonList(departureSearch),
        Collections.singletonList(arrivalSearch)
    );  
    
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
* locations (Array of Objects): Locations to use. Each location requires an id and lat/lng values
* departure_searches (Array of Objects, optional): Searches based on departure times.
  Leave departure location at no earlier than given time. You can define a maximum of 10 searches
* arrival_searches (Array of Objects, optional): Searches based on arrival times.
  Arrive at destination location at no later than given time. You can define a maximum of 10 searches
  
```java
    List<Location> locations = Arrays.asList(
        new Location("London center", new Coordinates(51.508930,-0.131387)),
        new Location("Hyde Park", new Coordinates(51.508824,-0.167093)),
        new Location("ZSL London Zoo", new Coordinates(51.536067,-0.153596))
    );

    DepartureSearch departureSearch = new DepartureSearch(
        "Departure search example",
        "London center",
        Arrays.asList("Hyde Park", "ZSL London Zoo"),
        new Driving(),
        Date.from(Instant.now()),
        Arrays.asList(Property.TRAVEL_TIME, Property.DISTANCE, Property.ROUTE)
    );

    ArrivalSearch arrivalSearch = new ArrivalSearch(
        "Arrival search example",
        Arrays.asList("Hyde Park", "ZSL London Zoo"),
        "London center",
        new PublicTransport(),
        Date.from(Instant.now()),
        Arrays.asList(Property.TRAVEL_TIME, Property.DISTANCE, Property.ROUTE, Property.FARES),
        new FullRange(true, 1, 1800)
    );

    RoutesRequest request = new RoutesRequest(
        locations,
        Collections.singletonList(departureSearch),
        Collections.singletonList(arrivalSearch)
    );

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