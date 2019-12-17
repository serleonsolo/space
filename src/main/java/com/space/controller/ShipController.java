package com.space.controller;

import com.space.utils.ShipHelper;
import com.space.model.Ship;
import com.space.repository.ShipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/rest")
public class ShipController {

    private static final Integer DEFAULT_PAGE_NUMBER = 0;
    private static final Integer DEFAULT_PAGE_SIZE = 3;
    private static final ShipOrder DEFAULT_SHIP_ORDER = ShipOrder.ID;

    @Autowired
    private ShipRepository shipRepository;

    @GetMapping("/ships")
    public ResponseEntity<List<Ship>> getShips(@RequestParam Map<String, String> customQuery) {
        List<Ship> listShips = shipRepository.findAll();

        listShips = ShipHelper.filter(listShips,customQuery);

        ShipOrder shipOrder = DEFAULT_SHIP_ORDER;
        if(customQuery.containsKey(ShipFilter.ORDER.getFieldName()))
            shipOrder = ShipOrder.getType(customQuery.get(ShipFilter.ORDER.getFieldName()));
        listShips = ShipHelper.sort(listShips,shipOrder);

        Integer pageSize = DEFAULT_PAGE_SIZE;
        Integer pageNumber = DEFAULT_PAGE_NUMBER;
        if(customQuery.containsKey(ShipFilter.PAGE_SIZE.getFieldName()))
            pageSize = Integer.parseInt(customQuery.get(ShipFilter.PAGE_SIZE.getFieldName()));
        if(customQuery.containsKey(ShipFilter.PAGE_NUMBER.getFieldName()))
            pageNumber = Integer.parseInt(customQuery.get(ShipFilter.PAGE_NUMBER.getFieldName()));
        listShips = listShips.stream()
                    .skip(pageNumber*pageSize)
                    .limit(pageSize)
                    .collect(Collectors.toList());

        return ResponseEntity.ok().body(listShips);
    }

    @GetMapping("/ships/count")
    public ResponseEntity<Integer> getShipsCount(@RequestParam Map<String, String> customQuery) {
        List<Ship> listShips = shipRepository.findAll();
        listShips = ShipHelper.filter(listShips,customQuery);
        Integer count = listShips.size();
        return ResponseEntity.ok().body(count);
    }

    @PostMapping("/ships")
    public ResponseEntity<Ship> createShip(@RequestBody Ship ship) {
        if(!ship.isValid())
            return ResponseEntity.badRequest().build();
        ship.calculate();
        return ResponseEntity.ok().body(shipRepository.save(ship));
    }

    @PostMapping("/ships/{id}")
    public ResponseEntity<Ship> update(@PathVariable("id") Long id, @RequestBody Ship sourceShip)
    {
        if(id <= 0)
            return ResponseEntity.badRequest().build();
        try
        {
            Ship targetShip = shipRepository.findById(id).get();
            targetShip = ShipHelper.copy(sourceShip,targetShip);
            if(!targetShip.isValid())
                return ResponseEntity.badRequest().build();
            return ResponseEntity.ok().body(targetShip);
        }
        catch(NoSuchElementException e)
        {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/ships/{id}")
    public ResponseEntity<Ship> deleteShip(@PathVariable("id") Long id) {
        if(id <= 0)
            return ResponseEntity.badRequest().build();
        try
        {
            Ship ship = shipRepository.findById(id).get();
            shipRepository.delete(ship);
            return ResponseEntity.ok().build();
        }
        catch(NoSuchElementException e)
        {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/ships/{id}")
    public ResponseEntity<Ship> getShip(@PathVariable("id") Long id) {
        if(id <= 0)
            return ResponseEntity.badRequest().build();
        try
        {
            Ship ship = shipRepository.findById(id).get();
            return ResponseEntity.ok().body(ship);
        }
        catch(NoSuchElementException e)
        {
            return ResponseEntity.notFound().build();
        }
    }
}
