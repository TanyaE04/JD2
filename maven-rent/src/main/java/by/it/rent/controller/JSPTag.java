package by.it.rent.controller;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import by.it.rent.bean.Car;

public class JSPTag extends TagSupport  {
	private Car car;

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}
	
	public int doStartTag () throws JspException {
		try {
			JspWriter out = pageContext.getOut();
			out.write("<tr><td colspan=\"2\">");
			out.write(car.getBrand() +" "+ car.getModel() +", "+ car.getColor()+", "+ car.getYear() +"г.в., "+ car.getGearbox());
			out.write("<br></td><td></td></tr><tr><td><b>");
			out.write("Статус: </b>" + car.getStatus()+"</td> ");
			out.write("<td><b>Цена за сутки: </b>"+ car.getPrice()+"</td>");
		} catch (IOException e) {
			throw new JspException (e);
		}
		return SKIP_BODY;
		
	}
}
