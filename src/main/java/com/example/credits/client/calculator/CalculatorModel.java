package com.example.credits.client.calculator;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class CalculatorModel implements Serializable {

    private Integer numberOfDays;
    private Integer amount;
    private BigDecimal comission;
    private BigDecimal prolongation7;
    private BigDecimal prolongation14;
    private BigDecimal prolongation30;
    private Date deadline;
    private Boolean first;

    public Integer getNumberOfDays() {
        return numberOfDays;
    }

    public void setNumberOfDays(Integer numberOfDays) {
        this.numberOfDays = numberOfDays;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public BigDecimal getComission() {
        return comission;
    }

    public void setComission(BigDecimal comission) {
        this.comission = comission;
    }

    public BigDecimal getProlongation7() {
        return prolongation7;
    }

    public void setProlongation7(BigDecimal prolongation7) {
        this.prolongation7 = prolongation7;
    }

    public BigDecimal getProlongation14() {
        return prolongation14;
    }

    public void setProlongation14(BigDecimal prolongation14) {
        this.prolongation14 = prolongation14;
    }

    public BigDecimal getProlongation30() {
        return prolongation30;
    }

    public void setProlongation30(BigDecimal prolongation30) {
        this.prolongation30 = prolongation30;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public Boolean getFirst() {
        return first;
    }

    public void setFirst(Boolean first) {
        this.first = first;
    }

    public BigDecimal calculateTotal() {
        return comission.add(new BigDecimal(amount));
    }
}
