package org.informatics.entity;

import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

/**
 * Static metamodel for {@link org.informatics.entity.Client}
 **/
@StaticMetamodel(Client.class)
public abstract class Client_ {

	
	/**
	 * @see #id
	 **/
	public static final String ID = "id";
	
	/**
	 * @see #name
	 **/
	public static final String NAME = "name";
	
	/**
	 * @see #phoneNumber
	 **/
	public static final String PHONE_NUMBER = "phoneNumber";
	
	/**
	 * @see #email
	 **/
	public static final String EMAIL = "email";

	
	/**
	 * Static metamodel type for {@link org.informatics.entity.Client}
	 **/
	public static volatile EntityType<Client> class_;
	
	/**
	 * Static metamodel for attribute {@link org.informatics.entity.Client#id}
	 **/
	public static volatile SingularAttribute<Client, Long> id;
	
	/**
	 * Static metamodel for attribute {@link org.informatics.entity.Client#name}
	 **/
	public static volatile SingularAttribute<Client, String> name;
	
	/**
	 * Static metamodel for attribute {@link org.informatics.entity.Client#phoneNumber}
	 **/
	public static volatile SingularAttribute<Client, String> phoneNumber;
	
	/**
	 * Static metamodel for attribute {@link org.informatics.entity.Client#email}
	 **/
	public static volatile SingularAttribute<Client, String> email;

}

