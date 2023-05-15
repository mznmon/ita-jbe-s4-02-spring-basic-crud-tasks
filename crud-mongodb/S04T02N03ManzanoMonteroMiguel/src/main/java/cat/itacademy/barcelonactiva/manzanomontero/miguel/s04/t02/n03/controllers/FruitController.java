package cat.itacademy.barcelonactiva.manzanomontero.miguel.s04.t02.n03.controllers;


import cat.itacademy.barcelonactiva.manzanomontero.miguel.s04.t02.n03.model.domain.Fruit;
import cat.itacademy.barcelonactiva.manzanomontero.miguel.s04.t02.n03.model.services.IFruitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class FruitController {
    @Autowired
    IFruitService fruitService;

    @PostMapping("/fruits")
    public ResponseEntity<Fruit> addFruit(@RequestBody Fruit fruit) {
        Fruit savedFruit = fruitService.save(new Fruit(fruit.getName(), fruit.getQuantityKilos()));
        if (savedFruit == null) return ResponseEntity.status(500).body(null);
        return ResponseEntity.status(201).body(savedFruit);
    }

    @PutMapping("/fruits")
    public ResponseEntity<Fruit> updateFruit(@RequestBody Fruit fruit) {
        Fruit updatedFruit = fruitService.update(fruit);
        if (updatedFruit != null) {
            return new ResponseEntity<>(updatedFruit, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/fruits/{id}")
    public ResponseEntity<Fruit> deleteFruit(@PathVariable String id) {
        try {
            this.fruitService.delete(id);
            return ResponseEntity.status(204).body(null);
        } catch (Exception e) {
            return  ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/fruits")
    public ResponseEntity<List<Fruit>> getAll() {
        List<Fruit> fruits = this.fruitService.findAll();
        if (fruits.isEmpty()) return ResponseEntity.status(204).body(null);
        return ResponseEntity.status(200).body(fruits);
    }

    @GetMapping("/fruits/{id}")
    public ResponseEntity<Fruit> getOneById(@PathVariable String id) {
        Optional<Fruit> fruit = this.fruitService.findOne(id);
        if (fruit.isPresent()) {
            return ResponseEntity.status(200).body(fruit.get());
        }
        return ResponseEntity.status(404).body(null);
    }


}
