package com.endava.shopbe.controller;

import com.endava.shopbe.entity.Cart;
import com.endava.shopbe.service.CartService;
import com.endava.shopbe.service.ProduceService;
import com.endava.shopbe.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/cart")
@CrossOrigin
@AllArgsConstructor
public class CartController {

    private final CartService cartService;

    private final ProduceService produceService;

    private final UserService userService;

    @GetMapping("getAllShops")
    public ResponseEntity<List<Cart>> findAll() {
        try {
            List<Cart> cartList = cartService.findAll();
            if (cartList != null) {
                return new ResponseEntity<>(cartList, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("addToCart")
    public void save(@RequestParam("productName") String productName, @RequestParam("productCantity") Integer cantity, @RequestParam("username") String username) {
        Cart cart = new Cart(produceService.getProductByName(productName), cantity, userService.getUserByUsername(username));
        cartService.save(cart);
    }

    @GetMapping("getById")
    public ResponseEntity<Cart> findById(Long aLong) {
        try {
            Cart cart = cartService.findById(aLong);
            if (cart != null) {
                return new ResponseEntity<>(cart, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("deleteById")
    public ResponseEntity<Cart> deleteById(Long aLong) {

        try {
            Cart cart = cartService.findById(aLong);
            if (cart != null) {
                cartService.deleteById(aLong);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("deleteEntity")
    public void delete(Cart cart) {
        cartService.delete(cart);
    }

    @PutMapping("/modify")
    public void modify(Cart cart) {
        cartService.save(cart);
    }
}
