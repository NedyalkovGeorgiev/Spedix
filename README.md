# Spedix Logistics Management System 🚛
​
 ## 🚀 Overview
 Spedix is a professional-grade logistics management application designed to handle complex transport operations, specialized vehicle fleets, and qualified personnel. Built with a focus on **Software Engineering best practices**, the system ensures data integrity through advanced validation and optimized performance via strict LAZY loading.
​
 ## 🏗️ Architectural Design (SOLID)
 The project implements a decoupled **4-Tier Architecture**:
 - **Entity Layer**: Domain models using JPA `JOINED` inheritance for complex hierarchies (Vehicles & Transports).
 - **DAO Layer**: Optimized persistence using Generic DAOs and specialized HQL with `JOIN FETCH` to maintain 100% **Strict LAZY** compliance.
 - **Service Layer**: Centralized "Brain" handling business rules, financial logic, and DTO mapping.
 - **DTO Layer**: Safe data transfer using "flat" objects to prevent circular references and encapsulate the database schema.
​
 ## ✨ Key Features
 - **Intelligent Validation Shield**: Dual-layered validation using **Jakarta Bean Validation** (@NotBlank, @Positive) and custom service-level logic.
 - **Advanced Business Rules**:
     - Automatic **Oversized Cargo** detection based on legal limits.
     - **Driver Qualification** checks (HAZMAT, Oversized permits).
     - **Vehicle Constraint** validation (Weight capacity, Passenger seating, Temperature control).
 - **Financial Analytics**: Real-time company revenue tracking, driver performance stats, and period-based reporting.
 - **Professional Reporting**: Automated export of logistics manifests to formatted `.txt` files.
​
 ## 🧪 Quality Assurance & Testing
 The system is verified by a robust **JUnit 5** suite and integration tests:
 1. **Business Rule Tests**: Isolated verification of cargo constraints and qualification rules.
 2. **Financial Logic Tests**: Accuracy verification of revenue triggers.
 3. **Mapping Tests**: Proving the integrity of Entity-to-DTO transformations.
 4. **Validation Tests**: Ensuring the "Zero-Trust" shield blocks invalid data.
​
 ## 🛠️ Requirements & Setup
 - **Java**: 17 or higher
 - **Framework**: Hibernate 7.1.0.Final
 - **Database**: MySQL 8.0+
 - **Build Tool**: Gradle
​
 ### Configuration
 1. Configure your database credentials in `src/main/resources/hibernate.properties`.
 2. Set `hibernate.hbm2ddl.auto` to `create` for the first run to generate the schema.
 3. Run `Main.java` to see the full analytics suite and generate the `transport_report.txt`.
​
 ---
 *Developed as a high-quality academic project demonstrating professional Java backend engineering.*
