package model.services;

import java.time.Duration;

import model.entities.CarRental;
import model.entities.Invoice;

public class RentalService {
	
	private Double pricePerHour;
	private Double pricePerDay;
	
	private TaxService taxService;

	public RentalService(Double pricePerHour, Double pricePerDay, TaxService taxService) {

		this.pricePerHour = pricePerHour;
		this.pricePerDay = pricePerDay;
		this.taxService = taxService;
	}

	public Double getPricePerHour() {
		return pricePerHour;
	}

	public void setPricePerHour(Double pricePerHour) {
		this.pricePerHour = pricePerHour;
	}

	public Double getPricePerDay() {
		return pricePerDay;
	}

	public void setPricePerDay(Double pricePerDay) {
		this.pricePerDay = pricePerDay;
	}

	public TaxService getTaxService() {
		return taxService;
	}

	public void setTaxService(BrazilTaxService taxService) {
		this.taxService = taxService;
	}
	
	public void processInvoice(CarRental car) {
		
		double minutes = Duration.between(car.getStart(), car.getFinish()).toMinutes();
		double hours = minutes / 60.0;
		
		double basicPayment;
		if(hours <= 12) {
			basicPayment = pricePerHour * Math.ceil(hours);
		}else {
			basicPayment = pricePerDay * Math.ceil(hours / 24.0);
		}
		
		double tax = taxService.tax(basicPayment);
		
		car.setInvoice(new Invoice(basicPayment, tax));
		
		
	}
	
	

}
