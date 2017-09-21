package io.pivotal.catalog.events;

/**
 * Created by benwilcock on 18/04/2017.
 */
public class ProductAddedEvent {

    private String id;
    private String name;
    private double price;

    public ProductAddedEvent() {
    }

    public ProductAddedEvent(String id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

	public double getPrice() {
		return price;
	}

	
}
