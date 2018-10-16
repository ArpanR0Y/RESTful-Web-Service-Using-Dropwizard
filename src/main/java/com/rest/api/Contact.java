package com.rest.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rest.dao.ContactDAO;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.xml.validation.Validator;

public class Contact {
        private int id;

        @NotBlank
        @Length(min=2, max=255)
        private String firstName;

        @NotBlank
        @Length(min=2, max=255)
        private String lastName;

        @NotBlank
        @Length(min=10, max=10, message = "The phone number must have 10 digits")
        private String phone;

    public Contact() {
        this.id = 0;
        this.firstName = null;
        this.lastName = null;
        this.phone = null;
    }

    public Contact(int id, String firstName, String lastName, String phone) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
    }

    @JsonProperty
    public int getId() {
        return id;
    }

    @JsonProperty
    public String getFirstName() {
        return firstName;
    }

    @JsonProperty
    public String getLastName() {
        return lastName;
    }

    @JsonProperty
    public String getPhone() {
        return phone;
    }
}