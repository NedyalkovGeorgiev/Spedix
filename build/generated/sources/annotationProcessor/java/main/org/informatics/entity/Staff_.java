package org.informatics.entity;

import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.StaticMetamodel;

/**
 * Static metamodel for {@link org.informatics.entity.Staff}
 **/
@StaticMetamodel(Staff.class)
public abstract class Staff_ extends Employee_ {


	
	/**
	 * Static metamodel type for {@link org.informatics.entity.Staff}
	 **/
	public static volatile EntityType<Staff> class_;

}

