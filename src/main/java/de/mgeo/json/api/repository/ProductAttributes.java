package de.mgeo.json.api.repository;

public class ProductAttributes {
    String name;
    String value;

    public ProductAttributes(String name, String value){
        this.name=name;
        this.value=value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}
