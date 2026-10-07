package com.votingsystem.for_reality_shows.model; // Adjust to your package

import jakarta.persistence.*;

@Entity
@Table(name = "sponsors")
public class Sponsor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String companyName;

    @Enumerated(EnumType.STRING)
    private SponsorTier tier;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    private Double contributionAmount;

    private Long showId; // Associated show reference

    public Sponsor() {}

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }

    public SponsorTier getTier() { return tier; }
    public void setTier(SponsorTier tier) { this.tier = tier; }

    public PaymentStatus getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(PaymentStatus paymentStatus) { this.paymentStatus = paymentStatus; }

    public Double getContributionAmount() { return contributionAmount; }
    public void setContributionAmount(Double contributionAmount) { this.contributionAmount = contributionAmount; }

    public Long getShowId() { return showId; }
    public void setShowId(Long showId) { this.showId = showId; }
}
