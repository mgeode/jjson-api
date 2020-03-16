package de.mgeo.json.api.repository;

import de.mgeo.json.api.Utils;

import java.util.List;

public class Product {
    private String name;
    private String date;
    private List<ProductAttributes> attributes;
    private Utils utils = new Utils();

    public Product(String productName, List<ProductAttributes> attributes) {
        this.name=productName;
        this.date=utils.getDateCurrent("dd.MM.YYYY-HH:mm:ss");;
        this.attributes=attributes;
    }

    public void Product(String strName, String strDate, List<ProductAttributes> attributes) {
        this.name=strName;
        this.date=strDate;
        this.attributes=attributes;
    }

    public String getName() {
        return name;
    }




    public List<ProductAttributes> getAttributes() {
        return attributes;
    }

    public void add(List<ProductAttributes> NEW_LIST) {
        this.attributes=NEW_LIST;
    }
}
