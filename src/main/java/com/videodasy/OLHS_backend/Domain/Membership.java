package com.videodasy.OLHS_backend.Domain;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;





@Entity
@EntityListeners(AuditingEntityListener.class)
public class Membership implements Serializable {
 
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	 @Column(nullable = false, updatable = false)
	private String uuid=UUID.randomUUID().toString();
	private String description;
	private double amount;
	private Date applicationDate;
	private boolean paymentReceived=false;
	
	@Enumerated(EnumType.STRING)
	private MembershipStatus memberStatus;
	
	@ManyToOne
	private Registrant applicant;
	
	@Column(nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	@JsonIgnore
	private Date doneAt; //= new Timestamp(new Date().getTime());
	private String doneBy = "";
    @Temporal(TemporalType.TIMESTAMP) 
	@LastModifiedDate
    @JsonIgnore
	private Date lastUpdatedAt; // = null;
    private String lastUpdatedBy = "";
	private boolean deletedStatus = false;
	private Date issuedAt;
	private String membershipStage;
	@Column(length=1000)
	private String comment; // this will take the latest comment of the loan
	
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public Date getApplicationDate() {
		return applicationDate;
	}
	public void setApplicationDate(Date applicationDate) {
		this.applicationDate = applicationDate;
	}
	public MembershipStatus getMemberStatus() {
		return memberStatus;
	}
	public void setMemberStatus(MembershipStatus memberStatus) {
		this.memberStatus = memberStatus;
	}
	public Registrant getApplicant() {
		return applicant;
	}
	public void setApplicant(Registrant applicant) {
		this.applicant = applicant;
	}
	public Date getDoneAt() {
		return doneAt;
	}
	public void setDoneAt(Date doneAt) {
		this.doneAt = doneAt;
	}
	public String getDoneBy() {
		return doneBy;
	}
	public void setDoneBy(String doneBy) {
		this.doneBy = doneBy;
	}
	public Date getLastUpdatedAt() {
		return lastUpdatedAt;
	}
	public void setLastUpdatedAt(Date lastUpdatedAt) {
		this.lastUpdatedAt = lastUpdatedAt;
	}
	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	public boolean isDeletedStatus() {
		return deletedStatus;
	}
	public void setDeletedStatus(boolean deletedStatus) {
		this.deletedStatus = deletedStatus;
	}
	public boolean isPaymentReceived() {
		return paymentReceived;
	}
	public void setPaymentReceived(boolean paymentReceived) {
		this.paymentReceived = paymentReceived;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public String getMembershipStage() {
		return membershipStage;
	}
	public void setMembershipStage(String membershipStage) {
		this.membershipStage = membershipStage;
	}
	public Date getIssuedAt() {
		return issuedAt;
	}
	public void setIssuedAt(Date issuedAt) {
		this.issuedAt = issuedAt;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	
	
	
	
}
