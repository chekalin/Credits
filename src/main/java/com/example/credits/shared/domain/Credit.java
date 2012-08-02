package com.example.credits.shared.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "CREDIT")
public class Credit implements Serializable {

	private static final long serialVersionUID = -223386250647129650L;

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CREDIT_Gen")
    @SequenceGenerator(name="CREDIT_Gen", sequenceName="CREDIT_SEQ")
	private Long creditId;

	@Column(name = "DAYS")
	private int numberOfDays;

	@Column(name = "AMOUNT")
	private int amount;

	@Column(name = "COMISSION")
	private BigDecimal comission;

	@Column(name = "EXTENSION_7")
	private BigDecimal extension7;
	
	@Column(name = "EXTENSION_14")
	private BigDecimal extension14;
	
	@Column(name = "EXTENSION_30")
	private BigDecimal extension30;
	
	@Column(name = "CREATED", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable=false, updatable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date created;
	
	public Long getCreditId() {
		return creditId;
	}

	public void setCreditId(Long creditId) {
		this.creditId = creditId;
	}

	public int getNumberOfDays() {
		return numberOfDays;
	}

	public void setNumberOfDays(int numberOfDays) {
		this.numberOfDays = numberOfDays;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public BigDecimal getComission() {
		return comission;
	}

	public void setComission(BigDecimal comission) {
		this.comission = comission;
	}

	public BigDecimal getTotal() {
		return comission.add(new BigDecimal(amount));
	}

	public BigDecimal getExtension7() {
		return extension7;
	}

	public void setExtension7(BigDecimal extension7) {
		this.extension7 = extension7;
	}

	public BigDecimal getExtension14() {
		return extension14;
	}

	public void setExtension14(BigDecimal extension14) {
		this.extension14 = extension14;
	}

	public BigDecimal getExtension30() {
		return extension30;
	}

	public void setExtension30(BigDecimal extension30) {
		this.extension30 = extension30;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

    @Override
    public String toString() {
        return "Credit{" +
                "creditId=" + creditId +
                ", numberOfDays=" + numberOfDays +
                ", amount=" + amount +
                ", comission=" + comission +
                ", extension7=" + extension7 +
                ", extension14=" + extension14 +
                ", extension30=" + extension30 +
                ", created=" + created +
                '}';
    }
}
