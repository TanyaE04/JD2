package by.it.rent.bean;

public class User {
	
	    private int idUser;
	    private int idRole;
	    private String surname;
	    private String name;
	    private String phone;

	    private String mail;
	    private String address;

	    private String roleName;
	    private String driverLicense;
	    private String passport;
	    private String status;
	    private double debt;

	    @Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((address == null) ? 0 : address.hashCode());
			long temp;
			temp = Double.doubleToLongBits(debt);
			result = prime * result + (int) (temp ^ (temp >>> 32));
			result = prime * result + ((driverLicense == null) ? 0 : driverLicense.hashCode());
			result = prime * result + idRole;
			result = prime * result + idUser;
			result = prime * result + ((mail == null) ? 0 : mail.hashCode());
			result = prime * result + ((name == null) ? 0 : name.hashCode());
			result = prime * result + ((passport == null) ? 0 : passport.hashCode());
			result = prime * result + ((phone == null) ? 0 : phone.hashCode());
			result = prime * result + ((roleName == null) ? 0 : roleName.hashCode());
			result = prime * result + ((status == null) ? 0 : status.hashCode());
			result = prime * result + ((surname == null) ? 0 : surname.hashCode());
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
			User other = (User) obj;
			if (address == null) {
				if (other.address != null)
					return false;
			} else if (!address.equals(other.address))
				return false;
			if (Double.doubleToLongBits(debt) != Double.doubleToLongBits(other.debt))
				return false;
			if (driverLicense == null) {
				if (other.driverLicense != null)
					return false;
			} else if (!driverLicense.equals(other.driverLicense))
				return false;
			if (idRole != other.idRole)
				return false;
			if (idUser != other.idUser)
				return false;
			if (mail == null) {
				if (other.mail != null)
					return false;
			} else if (!mail.equals(other.mail))
				return false;
			if (name == null) {
				if (other.name != null)
					return false;
			} else if (!name.equals(other.name))
				return false;
			if (passport == null) {
				if (other.passport != null)
					return false;
			} else if (!passport.equals(other.passport))
				return false;
			if (phone == null) {
				if (other.phone != null)
					return false;
			} else if (!phone.equals(other.phone))
				return false;
			if (roleName == null) {
				if (other.roleName != null)
					return false;
			} else if (!roleName.equals(other.roleName))
				return false;
			if (status == null) {
				if (other.status != null)
					return false;
			} else if (!status.equals(other.status))
				return false;
			if (surname == null) {
				if (other.surname != null)
					return false;
			} else if (!surname.equals(other.surname))
				return false;
			return true;
		}

		@Override
		public String toString() {
			return "User [idUser=" + idUser + ", idRole=" + idRole + ", surname=" + surname + ", name=" + name
					+ ", phone=" + phone + ", mail=" + mail + ", address=" + address + ", roleName=" + roleName
					+ ", driverLicense=" + driverLicense + ", passport=" + passport + ", status=" + status + ", debt="
					+ debt + "]";
		}

		public User() {
	    }

	    public String getRoleName() {
	        return roleName;
	    }

	    public void setRoleName(String roleName) {
	        this.roleName = roleName;
	    }

	    public String getDriverLicense() {
	        return driverLicense;
	    }

	    public void setDriverLicense(String driverLicense) {
	        this.driverLicense = driverLicense;
	    }

	    public String getPassport() {
	        return passport;
	    }

	    public void setPassport(String passport) {
	        this.passport = passport;
	    }


	    public int getIdUser() {
	        return idUser;
	    }

	    public void setIdUser(int idUser) {
	        this.idUser = idUser;
	    }

	    public int getIdRole() {
	        return idRole;
	    }

	    public void setIdRole(int idRole) {
	        this.idRole = idRole;
	    }

	    public String getSurname() {
	        return surname;
	    }

	    public void setSurname(String surname) {
	        this.surname = surname;
	    }

	    public String getName() {
	        return name;
	    }

	    public void setName(String name) {
	        this.name = name;
	    }

	    public String getPhone() {
	        return phone;
	    }

	    public void setPhone(String phone) {
	        this.phone = phone;
	    }

	    public String getMail() {
	        return mail;
	    }

	    public void setMail(String mail) {
	        this.mail = mail;
	    }

	    public String getAddress() {
	        return address;
	    }

	    public void setAddress(String address) {
	        this.address = address;
	    }

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public double getDebt() {
			return debt;
		}

		public void setDebt(double debt) {
			this.debt = debt;
		}

}
