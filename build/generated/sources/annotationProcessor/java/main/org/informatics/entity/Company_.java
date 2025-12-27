package org.informatics.entity;

import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

/**
 * Static metamodel for {@link org.informatics.entity.Company}
 **/
@StaticMetamodel(Company.class)
public abstract class Company_ {

	
	/**
	 * @see #id
	 **/
	public static final String ID = "id";
	
	/**
	 * @see #name
	 **/
	public static final String NAME = "name";
	
	/**
	 * @see #address
	 **/
	public static final String ADDRESS = "address";
	
	/**
	 * @see #employees
	 **/
	public static final String EMPLOYEES = "employees";
	
	/**
	 * @see #vehicles
	 **/
	public static final String VEHICLES = "vehicles";
	
	/**
	 * @see #transports
	 **/
	public static final String TRANSPORTS = "transports";

	
	/**
	 * Static metamodel type for {@link org.informatics.entity.Company}
	 **/
	public static volatile EntityType<Company> class_;
	
	/**
	 * Static metamodel for attribute {@link org.informatics.entity.Company#id}
	 **/
	public static volatile SingularAttribute<Company, Long> id;
	
	/**
	 * Static metamodel for attribute {@link org.informatics.entity.Company#name}
	 **/
	public static volatile SingularAttribute<Company, String> name;
	
	/**
	 * Static metamodel for attribute {@link org.informatics.entity.Company#address}
	 **/
	public static volatile SingularAttribute<Company, String> address;
	
	/**
	 * Static metamodel for attribute {@link org.informatics.entity.Company#employees}
	 **/
	public static volatile ListAttribute<Company, Employee> employees;
	
	/**
	 * Static metamodel for attribute {@link org.informatics.entity.Company#vehicles}
	 **/
	public static volatile ListAttribute<Company, Vehicle> vehicles;
	
	/**
	 * Static metamodel for attribute {@link org.informatics.entity.Company#transports}
	 **/
	public static volatile ListAttribute<Company, Transport> transports;

}

