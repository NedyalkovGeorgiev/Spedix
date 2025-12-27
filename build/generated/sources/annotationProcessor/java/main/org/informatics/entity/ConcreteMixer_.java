package org.informatics.entity;

import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

/**
 * Static metamodel for {@link org.informatics.entity.ConcreteMixer}
 **/
@StaticMetamodel(ConcreteMixer.class)
public abstract class ConcreteMixer_ extends Truck_ {

	
	/**
	 * @see #drumCapacity
	 **/
	public static final String DRUM_CAPACITY = "drumCapacity";

	
	/**
	 * Static metamodel type for {@link org.informatics.entity.ConcreteMixer}
	 **/
	public static volatile EntityType<ConcreteMixer> class_;
	
	/**
	 * Static metamodel for attribute {@link org.informatics.entity.ConcreteMixer#drumCapacity}
	 **/
	public static volatile SingularAttribute<ConcreteMixer, Double> drumCapacity;

}

