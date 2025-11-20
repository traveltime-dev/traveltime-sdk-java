package com.traveltime.sdk.server_tests;

import com.traveltime.sdk.dto.common.Coordinates;
import com.traveltime.sdk.dto.common.Location;
import org.instancio.Instancio;
import org.instancio.Model;

import java.util.Collections;
import java.util.List;

import static org.instancio.Select.field;

public class LocationsGen {

    public static Model<Coordinates> coordinatesModel = Instancio.of(Coordinates.class)
        .generate(field(Coordinates::getLat), gen -> gen.spatial().coordinate().lat())
        .generate(field(Coordinates::getLng), gen -> gen.spatial().coordinate().lon())
        .toModel();

    public static Model<Location> locationModel = Instancio.of(Location.class)
        .setModel(field(Location::getCoords), coordinatesModel)
        .generate(field(Location::getId), gen -> gen.string())
        .toModel();

    public static Coordinates coordinates() {
        return Instancio.create(coordinatesModel);
    }

    public static List<Coordinates> coordinatesSingletonList() {
        return Collections.singletonList(coordinates());
    }

    public static List<Location> singletonList() {
        return Collections.singletonList(Instancio.create(locationModel));
    }

    public static List<Location> listOfN(int n) {
        return Instancio.ofList(locationModel).size(n).create();
    }
}
