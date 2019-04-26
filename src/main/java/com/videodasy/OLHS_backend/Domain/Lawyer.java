package com.videodasy.OLHS_backend.Domain;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Lawyer implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@Column(nullable = false, updatable = false)
	private String uuid = UUID.randomUUID().toString();
	private String names;
	private String address;
	private int succeededCase;
	private int failedCase;
	private WorkStatus workstatus;
	private Date dob;
	private CaseType casetype;
	@ManyToOne
	private District district;
	@OneToOne
	private Registrant registrant;
	
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
	public String getNames() {
		return names;
	}
	public void setNames(String names) {
		this.names = names;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getSucceededCase() {
		return succeededCase;
	}
	public void setSucceededCase(int succeededCase) {
		this.succeededCase = succeededCase;
	}
	public int getFailedCase() {
		return failedCase;
	}
	public void setFailedCase(int failedCase) {
		this.failedCase = failedCase;
	}
	public WorkStatus getWorkstatus() {
		return workstatus;
	}
	public void setWorkstatus(WorkStatus workstatus) {
		this.workstatus = workstatus;
	}
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	public CaseType getCasetype() {
		return casetype;
	}
	public void setCasetype(CaseType casetype) {
		this.casetype = casetype;
	}
	public District getDistrict() {
		return district;
	}
	public void setDistrict(District district) {
		this.district = district;
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
	

	public Registrant getRegistrant() {
		return registrant;
	}
	public void setRegistrant(Registrant registrant) {
		this.registrant = registrant;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}
