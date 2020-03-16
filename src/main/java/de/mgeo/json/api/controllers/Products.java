package de.mgeo.json.api.controllers;

import de.mgeo.json.api.repository.Product;
import de.mgeo.json.api.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;

@RestController
public class Products {
    private static Logger logger = LoggerFactory.getLogger(Products.class);

    @Autowired
    private ProductRepository productRepository;

    /**
     *  GET
     **/
    @GetMapping({"/api/products"})
    public HashMap<String, Product> getAllEntries() {
        return productRepository.getAll();
    }

    @RequestMapping(method = RequestMethod.GET, value="/api/products/{product}")
    public Product getProductByName(@PathVariable("product") String productName) {
        return productRepository.getProductByName(productName);
    }

    @RequestMapping(method = RequestMethod.PUT, value="/api/products/{product}/{attribute}/{avalue}")
    public int newProduct(@PathVariable("product") String productName,@PathVariable("attribute") String attributeName,@PathVariable("avalue") String attributeValue) throws IOException {
        return productRepository.addProduct(productName,attributeName,attributeValue);
    }
    @RequestMapping(method = RequestMethod.POST, value="/api/products/{product}/{attribute}/{avalue}")
    public int updateProduct(@PathVariable("product") String productName,@PathVariable("attribute") String attributeName,@PathVariable("avalue") String attributeValue){
        return productRepository.updateProduct(productName,attributeName,attributeValue);
    }

    @RequestMapping(method = RequestMethod.DELETE, value="/api/products/{product}")
    public int removeProduct(@PathVariable("product") String productName) throws IOException {
        return productRepository.removeProduct(productName);
    }

    @RequestMapping(method = RequestMethod.DELETE, value="/api/products/{product}/{attribute}")
    public int deleteAttribute(@PathVariable("product") String productName,@PathVariable("attribute") String attributeName) throws IOException {
        return productRepository.deleteAttribute(productName, attributeName);
    }
}
