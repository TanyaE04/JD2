package by.it.rent.bean;

public class Order {
    @Override
	public String toString() {
		return "Order [idOrder=" + idOrder + ", idCar=" + idCar + ", dateRent=" + dateRent + ", days=" + days
				+ ", dateReturn=" + dateReturn + ", realDays=" + realDays + ", total=" + total + ", idRefusal="
				+ idRefusal + ", idUsers=" + idUsers + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dateRent == null) ? 0 : dateRent.hashCode());
		result = prime * result + ((dateReturn == null) ? 0 : dateReturn.hashCode());
		result = prime * result + days;
		result = prime * result + idCar;
		result = prime * result + idOrder;
		result = prime * result + idRefusal;
		result = prime * result + idUsers;
		result = prime * result + realDays;
		long temp;
		temp = Double.doubleToLongBits(total);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		if (dateRent == null) {
			if (other.dateRent != null)
				return false;
		} else if (!dateRent.equals(other.dateRent))
			return false;
		if (dateReturn == null) {
			if (other.dateReturn != null)
				return false;
		} else if (!dateReturn.equals(other.dateReturn))
			return false;
		if (days != other.days)
			return false;
		if (idCar != other.idCar)
			return false;
		if (idOrder != other.idOrder)
			return false;
		if (idRefusal != other.idRefusal)
			return false;
		if (idUsers != other.idUsers)
			return false;
		if (realDays != other.realDays)
			return false;
		if (Double.doubleToLongBits(total) != Double.doubleToLongBits(other.total))
			return false;
		return true;
	}

	private int idOrder;
    private int idCar;
    private String dateRent;
    private int days;
    private String dateReturn;
    private int realDays;
    private double total;
    private int idRefusal;
    private int idUsers;
    private String markCar;
    private String damage;
    private double damageSum;
    private String status;

    public Order() {
    }

    public int getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(int idOrder) {
        this.idOrder = idOrder;
    }

    public int getIdCar() {
        return idCar;
    }

    public void setIdCar(int idCar) {
        this.idCar = idCar;
    }

    public String getDateRent() {
        return dateRent;
    }

    public void setDateRent(String dateRent) {
        this.dateRent = dateRent;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public String getDateReturn() {
        return dateReturn;
    }

    public void setDateReturn(String dateReturn) {
        this.dateReturn = dateReturn;
    }

    public int getRealDays() {
        return realDays;
    }

    public void setRealDays(int realDays) {
        this.realDays = realDays;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getIdRefusal() {
        return idRefusal;
    }

    public void setIdRefusal(int idRefusal) {
        this.idRefusal = idRefusal;
    }

    public int getIdUsers() {
        return idUsers;
    }

    public void setIdUsers(int idUsers) {
        this.idUsers = idUsers;
    }

	public String getMarkCar() {
		return markCar;
	}

	public void setMarkCar(String markCar) {
		this.markCar = markCar;
	}

	public String getDamage() {
		return damage;
	}

	public void setDamage(String demage) {
		this.damage = demage;
	}

	public double getDamageSum() {
		return damageSum;
	}

	public void setDamageSum(double demageSum) {
		this.damageSum = demageSum;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
