package com.space.utils;

import com.space.controller.ShipFilter;
import com.space.controller.ShipOrder;
import com.space.model.Ship;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ShipHelper {

    public static List<Ship> sort(List<Ship> listShips, ShipOrder shipOrder)
    {
        if(shipOrder.equals(ShipOrder.ID))
                listShips = listShips.stream()
                        .sorted((ship1, ship2) -> ship1.getId().compareTo(ship2.getId()))
                        .collect(Collectors.toList());
        else if(shipOrder.equals(ShipOrder.SPEED))
                listShips = listShips.stream()
                        .sorted((ship1, ship2) -> ship1.getSpeed().compareTo(ship2.getSpeed()))
                        .collect(Collectors.toList());
        else if(shipOrder.equals(ShipOrder.DATE))
                listShips = listShips.stream()
                        .sorted((ship1, ship2) -> ship1.getProdDate().compareTo(ship2.getProdDate()))
                        .collect(Collectors.toList());
        else if(shipOrder.equals(ShipOrder.RATING))
                listShips = listShips.stream()
                        .sorted((ship1, ship2) -> ship1.getRating().compareTo(ship2.getRating()))
                        .collect(Collectors.toList());
        return listShips;
    }

    public static List<Ship> filter(List<Ship> listShips, Map<String, String> customQuery)
    {
        if(customQuery.containsKey(ShipFilter.NAME.getFieldName()))
        {
            String nameFilter = customQuery.get(ShipFilter.NAME.getFieldName());
            listShips = listShips.stream()
                    .filter(x -> x.getName().contains(nameFilter))
                    .collect(Collectors.toList());
        }
        if(customQuery.containsKey(ShipFilter.PLANET.getFieldName()))
        {
            String planetFilter = customQuery.get(ShipFilter.PLANET.getFieldName());
            listShips = listShips.stream()
                    .filter(x -> x.getPlanet().contains(planetFilter))
                    .collect(Collectors.toList());
        }
        if(customQuery.containsKey(ShipFilter.SHIP_TYPE.getFieldName()))
        {
            String shipTypeFilter = customQuery.get(ShipFilter.SHIP_TYPE.getFieldName());
            listShips = listShips.stream()
                    .filter(x -> x.getShipType().toString().equals(shipTypeFilter))
                    .collect(Collectors.toList());
        }
        if(customQuery.containsKey(ShipFilter.AFTER.getFieldName()))
        {
            Long prodDateAfterFilter = Long.parseLong(customQuery.get(ShipFilter.AFTER.getFieldName()));
            listShips = listShips.stream()
                    .filter(x -> x.getProdDate().getTime() > prodDateAfterFilter)
                    .collect(Collectors.toList());
        }
        if(customQuery.containsKey(ShipFilter.BEFORE.getFieldName()))
        {
            Long prodDateBeforeFilter = Long.parseLong(customQuery.get(ShipFilter.BEFORE.getFieldName()));
            listShips = listShips.stream()
                    .filter(x -> x.getProdDate().getTime() < prodDateBeforeFilter)
                    .collect(Collectors.toList());
        }
        if(customQuery.containsKey(ShipFilter.IS_USED.getFieldName()))
        {
            Boolean isUsedFilter = Boolean.parseBoolean(customQuery.get(ShipFilter.IS_USED.getFieldName()));
            listShips = listShips.stream()
                    .filter(x -> x.getUsed().equals(isUsedFilter))
                    .collect(Collectors.toList());
        }
        if(customQuery.containsKey(ShipFilter.MIN_SPEED.getFieldName()))
        {
            Double minSpeedFilter = Ship.roundDouble(Double.parseDouble(customQuery.get(ShipFilter.MIN_SPEED.getFieldName())));
            listShips = listShips.stream()
                    .filter(x -> x.getSpeed() >= minSpeedFilter)
                    .collect(Collectors.toList());
        }
        if(customQuery.containsKey(ShipFilter.MAX_SPEED.getFieldName()))
        {
            Double maxSpeedFilter = Ship.roundDouble(Double.parseDouble(customQuery.get(ShipFilter.MAX_SPEED.getFieldName())));
            listShips = listShips.stream()
                    .filter(x -> x.getSpeed() <= maxSpeedFilter)
                    .collect(Collectors.toList());
        }
        if(customQuery.containsKey(ShipFilter.MIN_CREW_SIZE.getFieldName()))
        {
            Integer minCrewSizeFilter = Integer.parseInt(customQuery.get(ShipFilter.MIN_CREW_SIZE.getFieldName()));
            listShips = listShips.stream()
                    .filter(x -> x.getCrewSize() >= minCrewSizeFilter)
                    .collect(Collectors.toList());
        }
        if(customQuery.containsKey(ShipFilter.MAX_CREW_SIZE.getFieldName()))
        {
            Integer maxCrewSizeFilter = Integer.parseInt(customQuery.get(ShipFilter.MAX_CREW_SIZE.getFieldName()));
            listShips = listShips.stream()
                    .filter(x -> x.getCrewSize() <= maxCrewSizeFilter)
                    .collect(Collectors.toList());
        }
        if(customQuery.containsKey(ShipFilter.MIN_RATING.getFieldName()))
        {
            Double minRatingFilter = Double.parseDouble(customQuery.get(ShipFilter.MIN_RATING.getFieldName()));
            listShips = listShips.stream()
                    .filter(x -> x.getRating() >= minRatingFilter)
                    .collect(Collectors.toList());
        }
        if(customQuery.containsKey(ShipFilter.MAX_RATING.getFieldName()))
        {
            Double maxRatingFilter = Double.parseDouble(customQuery.get(ShipFilter.MAX_RATING.getFieldName()));
            listShips = listShips.stream()
                    .filter(x -> x.getRating() <= maxRatingFilter)
                    .collect(Collectors.toList());
        }
        return listShips;
    }

    public static Ship copy(Ship sourceShip, Ship targetShip)
    {
        if(sourceShip.getName() != null)
            targetShip.setName(sourceShip.getName());
        if(sourceShip.getPlanet() != null)
            targetShip.setPlanet(sourceShip.getPlanet());
        if(sourceShip.getCrewSize() != null)
            targetShip.setCrewSize(sourceShip.getCrewSize());
        if(sourceShip.getProdDate() != null)
            targetShip.setProdDate(sourceShip.getProdDate());
        if(sourceShip.getShipType() != null)
            targetShip.setShipType(sourceShip.getShipType());
        if(sourceShip.getSpeed() != null)
            targetShip.setSpeed(sourceShip.getSpeed());
        if(sourceShip.getUsed() != null)
            targetShip.setUsed(sourceShip.getUsed());
        targetShip.calculate();
        return targetShip;
    }
}
