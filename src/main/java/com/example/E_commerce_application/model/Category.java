package com.example.E_commerce_application.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "categorys")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    /// branch out list of products inside category service apis
    @OneToMany(mappedBy = "category")
    @JsonIgnore ///  to sensibly avoid stackoverflow and unnecessary infos exposes
    private List<Product> products;


    public Category(String name, String description) {
        this.name = name;
        this.description = description;
    }

}