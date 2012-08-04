package com.example.credits.shared.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@SuppressWarnings("JpaDataSourceORMInspection")
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
	private BigDecimal commission;

    @Column(name = "FIRST")
    private boolean first;

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

	public BigDecimal getCommission() {
		return commission;
	}

	public void setCommission(BigDecimal comission) {
		this.commission = comission;
	}

	public BigDecimal getTotal() {
		return commission.add(new BigDecimal(amount));
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

    public void setFirst(boolean first) {
        this.first = first;
    }

    public boolean isFirst() {
        return first;
    }

    @Override
    public String toString() {
        return "Credit{" +
                "creditId=" + creditId +
                ", numberOfDays=" + numberOfDays +
                ", amount=" + amount +
                ", comission=" + commission +
                ", first=" + first +
                ", created=" + created +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Credit credit = (Credit) o;

        if (creditId != null ? !creditId.equals(credit.creditId) : credit.creditId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return creditId != null ? creditId.hashCode() : 0;
    }
}
