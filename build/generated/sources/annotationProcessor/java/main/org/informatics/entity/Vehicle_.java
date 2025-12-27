package org.informatics.entity;

import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

/**
 * Static metamodel for {@link org.informatics.entity.Vehicle}
 **/
@StaticMetamodel(Vehicle.class)
public abstract class Vehicle_ {

	
	/**
	 * @see #id
	 **/
	public static final String ID = "id";
	
	/**
	 * @see #make
	 **/
	public static final String MAKE = "make";
	
	/**
	 * @see #model
	 **/
	public static final String MODEL = "model";
	
	/**
	 * @see #company
	 **/
	public static final String COMPANY = "company";

	
	/**
	 * Static metamodel type for {@link org.informatics.entity.Vehicle}
	 **/
	public static volatile EntityType<Vehicle> class_;
	
	/**
	 * Static metamodel for attribute {@link org.informatics.entity.Vehicle#id}
	 **/
	public static volatile SingularAttribute<Vehicle, Long> id;
	
	/**
	 * Static metamodel for attribute {@link org.informatics.entity.Vehicle#make}
	 **/
	public static volatile SingularAttribute<Vehicle, String> make;
	
	/**
	 * Static metamodel for attribute {@link org.informatics.entity.Vehicle#model}
	 **/
	public static volatile SingularAttribute<Vehicle, String> model;
	
	/**
	 * Static metamodel for attribute {@link org.informatics.entity.Vehicle#company}
	 **/
	public static volatile SingularAttribute<Vehicle, Company> company;

}

