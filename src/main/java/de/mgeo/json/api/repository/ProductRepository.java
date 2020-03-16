package de.mgeo.json.api.repository;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import de.mgeo.json.api.configs.AppProperties;

import de.mgeo.json.api.controllers.Products;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

@Component
public class ProductRepository {

    @Autowired
    AppProperties appProperties;

    private static Logger logger = LoggerFactory.getLogger(Products.class);

    private ObjectMapper objectMapper;
    private HashMap<String,Product> LIST_PRODUCTS;


    /**
     * ProductRepository
     * @throws IOException
     */
    public ProductRepository() throws IOException {

        this.LIST_PRODUCTS = new HashMap<>();
        logger.debug("WORK-DIR: "+appProperties.getDataFilePath());
        byte[] jsonData = Files.readAllBytes(Paths.get(appProperties.getDataFilePath()));
        this.objectMapper = new ObjectMapper();

        JsonNode rootNode = objectMapper.readTree(jsonData);
        Iterator<JsonNode> rootElements = rootNode.elements();
        Iterator<String> fieldElements = rootNode.fieldNames();

        while(rootElements.hasNext()) {
            JsonNode jRootNode = rootElements.next();

            JsonNode nodeAttributes = jRootNode.path("attributes");
            JsonNode attributeNodes = objectMapper.readTree(String.valueOf(nodeAttributes));

            //JSON-Elements from ROOT

            Iterator<JsonNode>  attributesElements = attributeNodes.elements();
            String productDate =  jRootNode.get("date").asText();
            String productName = fieldElements.next();

            //JSON-Elements from ATTRIBUTES
            List<ProductAttributes>LIST_ATTRIBUTES = new ArrayList();
            while(attributesElements.hasNext()){
                JsonNode elProd = attributesElements.next();
                ProductAttributes attributes = new ProductAttributes(elProd.get("name").asText(), elProd.get("value").asText());
                logger.trace(" - Put "+productName+ " - "+attributes.toString());
                LIST_ATTRIBUTES.add(attributes);
            }
            Product productEntry =  new Product(productName,LIST_ATTRIBUTES);
            LIST_PRODUCTS.put(productName,productEntry);
        }
    }


    /**
     * DATA: Get All
     * @return
     */
    public HashMap<String,Product> getAll() {
        logger.trace("Read Data!");
        return LIST_PRODUCTS;
    }

    public boolean isProductExist(String productName) {
        logger.trace("Is Product '"+productName+"' exists? - "+getProductByName(productName).getName().equals(productName));
        return this.getProductByName(productName).getName().equals(productName);
    }

    /**
     * DATA: Attribute Exists
     * @param productName
     * @param findStep
     * @return
     */
    public Boolean isAttributeExist(String productName, String findStep){
        List<ProductAttributes> LIST_ATTR = getProductByName(productName).getAttributes();
        Iterator<ProductAttributes> attrIter = LIST_ATTR.iterator();
        while (attrIter.hasNext()){
            ProductAttributes prodAttr = attrIter.next();
            logger.trace("name: "+prodAttr.getName()+", value:"+prodAttr.getValue());
            if (prodAttr.getName().equals(findStep)) {
                return true;
            }
        }
        return false;
    }

    /**
     * DATA: getProductByName
     * @param name
     * @return
     */
    public Product getProductByName(String name) {
        for (Map.Entry<String, Product> entry : LIST_PRODUCTS.entrySet()) {
            logger.trace("READ: --> Key : " + entry.getKey() + " Value : " + entry.getValue());
            if (entry.getKey().equals(name)) {
                return entry.getValue();
            }
        }

        logger.warn("Entry "+name+" not found!");
        return null;
    }


    public int updateProduct(String productName, String attributeName, String attributeValue) {
        if (!this.isProductExist(productName)) {
            logger.error("Can't define new product "+productName+" here! - For add new produt, use PUT");
            return 400;
        }
        Product product = this.getProductByName(productName);
        List<ProductAttributes> productAttributes=product.getAttributes();
        if (!this.isAttributeExist(productName,attributeName)) {
            productAttributes.add(new ProductAttributes(attributeName,attributeValue));
        }
        LIST_PRODUCTS.put(productName,new Product(productName,productAttributes));
        return 200;
    }


    /**
     * DATA: new Product
     * @param productName
     * @param attributeName
     * @param attributeValue
     * @return
     * @throws IOException
     */
    public int addProduct(String productName, String attributeName, String attributeValue) throws IOException {
        if (this.isProductExist(productName)) {
            logger.error("Product "+productName+" already exists! - For updating, use POST");
            return 400;
        }
        List<ProductAttributes> LIST_ATTRIBUTES=new ArrayList<>();
        LIST_ATTRIBUTES.add(new ProductAttributes(attributeName,attributeValue));
        LIST_PRODUCTS.put(productName,new Product(productName,LIST_ATTRIBUTES));
        logger.debug("Saving new product "+productName);
        save();
        return 200;
    }

    /**
     * DATA: remove complete Product
     * @param productName
     * @return
     * @throws IOException
     */
    public int removeProduct(String productName) throws IOException  {
            int rc=400;
            HashMap<String, Product> LIST_NEW= new HashMap<>();
            for (Map.Entry<String, Product> productEntry : LIST_PRODUCTS.entrySet()) {
                if (productEntry.getKey().equals(productName)) {
                    rc=200;
                    logger.debug("Removing Key : " + productEntry.getKey() + " Value : " + productEntry.getValue());
                }
                else {
                    LIST_NEW.put(productEntry.getKey(),productEntry.getValue());
                }
            }
            this.LIST_PRODUCTS=LIST_NEW;
            save();
            return rc;
    }

    /**
     * DATA: Delte only a Attribute
     * @param productName
     * @param attributeName
     * @return
     * @throws IOException
     */
    public int deleteAttribute(String productName, String attributeName) throws IOException {
        int rc=400;
        Product p = this.getProductByName(productName);
        List<ProductAttributes>LIST_ATTS=p.getAttributes();
        List<ProductAttributes> LIST_NEW = new ArrayList<>();
        Iterator<ProductAttributes> paIter=LIST_ATTS.iterator();
        while (paIter.hasNext()){
            ProductAttributes pAtt = paIter.next();
            if (pAtt.getName().equals(attributeName)) {
                rc=200;
                logger.debug("Updating stepid "+attributeName+ "!");
            } else {
                LIST_NEW.add(pAtt);
            }
        }
        p.add(LIST_NEW);
        save();
        return rc;
    }

    /**
     * Save all
     * @throws IOException
     */
    private void save() throws IOException {
        //write objectMapper in pretty input
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        objectMapper.writeValue(new File(appProperties.getDataFilePath()), LIST_PRODUCTS);
    }
}
