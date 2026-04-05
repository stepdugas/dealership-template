package com.dealership.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;

/**
 * DTO for client intake form submissions.
 * Captures all dealership info needed to customize the template.
 */
public class ClientIntakeRequest {

    @NotBlank(message = "Business name is required")
    private String businessName;

    private String tagline;

    @NotBlank(message = "Phone is required")
    private String phone;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Address is required")
    private String address;

    @NotBlank(message = "City is required")
    private String city;

    @NotBlank(message = "State is required")
    private String state;

    @NotBlank(message = "ZIP is required")
    private String zip;

    private Map<String, String> hours; // Monday-Sunday with open/close times

    private String primaryColor;

    private String facebook;

    private String instagram;

    private String notes;

    /** About Us page content */
    private String aboutBlurb;
    private String aboutMission;
    private String aboutYearFounded;
    private String aboutStatYears;
    private String aboutStatVehicles;
    private String aboutStatReviews;
    private String aboutStatTeam;

    /** Desired domain name or "I need help choosing one" */
    private String domain;

    /** Which optional pages the client wants enabled */
    private List<String> pages;

    // Getters and Setters
    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public Map<String, String> getHours() {
        return hours;
    }

    public void setHours(Map<String, String> hours) {
        this.hours = hours;
    }

    public String getPrimaryColor() {
        return primaryColor;
    }

    public void setPrimaryColor(String primaryColor) {
        this.primaryColor = primaryColor;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getAboutBlurb() { return aboutBlurb; }
    public void setAboutBlurb(String aboutBlurb) { this.aboutBlurb = aboutBlurb; }

    public String getAboutMission() { return aboutMission; }
    public void setAboutMission(String aboutMission) { this.aboutMission = aboutMission; }

    public String getAboutYearFounded() { return aboutYearFounded; }
    public void setAboutYearFounded(String aboutYearFounded) { this.aboutYearFounded = aboutYearFounded; }

    public String getAboutStatYears() { return aboutStatYears; }
    public void setAboutStatYears(String aboutStatYears) { this.aboutStatYears = aboutStatYears; }

    public String getAboutStatVehicles() { return aboutStatVehicles; }
    public void setAboutStatVehicles(String aboutStatVehicles) { this.aboutStatVehicles = aboutStatVehicles; }

    public String getAboutStatReviews() { return aboutStatReviews; }
    public void setAboutStatReviews(String aboutStatReviews) { this.aboutStatReviews = aboutStatReviews; }

    public String getAboutStatTeam() { return aboutStatTeam; }
    public void setAboutStatTeam(String aboutStatTeam) { this.aboutStatTeam = aboutStatTeam; }

    public String getDomain() { return domain; }
    public void setDomain(String domain) { this.domain = domain; }

    public List<String> getPages() { return pages; }
    public void setPages(List<String> pages) { this.pages = pages; }
}
