package ar.ospim.empleadores.nuevo.infra.out.getwaypago.webservice.pagomiscuentas.dto;

public class Due_Dates{
	private String due_date;
	private String due_date_hour;
	private String amount;
	public String getDue_date() {
		return due_date;
	}
	public void setDue_date(String due_date) {
		this.due_date = due_date;
	}
	public String getDue_date_hour() {
		return due_date_hour;
	}
	public void setDue_date_hour(String due_date_hour) {
		this.due_date_hour = due_date_hour;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	
}
