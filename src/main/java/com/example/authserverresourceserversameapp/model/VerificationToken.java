package com.example.authserverresourceserversameapp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Calendar;
import java.util.Date;

@Entity(name = "tokens")
@Getter
@Setter
public class VerificationToken {
    private static final int EXPIRATION = 20;
    @Id
    @SequenceGenerator(name = "tokenGen", sequenceName = "tokenSeq", initialValue = 10)
    @GeneratedValue(generator = "tokenGen")
    private Long id;

    private String token;
    @Column(name = "expiry_date")
    private Date expiryDate;
    @OneToOne(fetch = FetchType.EAGER, targetEntity = User.class)
    private User user;
    public VerificationToken() {
    }
    public VerificationToken(final String token) {
        this.token = token;
        this.expiryDate = calculateExpiryDate(EXPIRATION);
    }
    public VerificationToken(String token, User user) {
        this.token = token;
        this.user = user;
        this.expiryDate  = calculateExpiryDate(EXPIRATION);
    }
       private Date calculateExpiryDate(final int expiryTimeInMinutes) {
        final Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(new Date().getTime());
        cal.add(Calendar.SECOND, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }
    public void updateToken(final String token) {
        this.token = token;
        this.expiryDate = calculateExpiryDate(EXPIRATION);
    }
}
