package model;

public class Product {
	private String code;
	private ProductType type;
	private String name;
	private String description;
	private double unitPrice;
	private double groupPrice;
	
	
	public Product(String code, ProductType type, String name, String description, double unitPrice, double groupPrice) {
		super();
		if(unitPrice < 0) {
			throw new IllegalArgumentException("The price per unit cannot be less than 0.");
		}
		if(groupPrice < 0) {
			throw new IllegalArgumentException("The price per person cannot be less than 0.");
		}
		this.code = code;
		this.type = type;
		this.name = name;
		this.description = description;
		this.unitPrice = unitPrice;
		this.groupPrice = groupPrice;
	}
	
	public String getCode() {
		return code;
	}
	public ProductType getType() {
		return type;
	}
	public String getName() {
		return name;
	}
	public String getDescription() {
		return description;
	}
	public double getUnitPrice() {
		return unitPrice;
	}
	public double getGroupPrice() {
		return groupPrice;
	}

	@Override
	public String toString() {
		return "Product [code=" + code + ", type=" + type + ", name=" + name + ", description=" + description
				+ ", unitPrice=" + unitPrice + ", groupPrice=" + groupPrice + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
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
		Product other = (Product) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		return true;
	}
	
}
