package com.example.credits.client.calculator;

import java.math.BigDecimal;
import java.util.Date;

public class CalculatorModel {

    private Integer numberOfDays;
    private Integer amount;
    private BigDecimal comission;
    private BigDecimal extension7;
    private BigDecimal extension14;
    private BigDecimal extension30;
    private Date deadline;
    private Boolean isRecurrent;

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

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public Boolean getRecurrent() {
        return isRecurrent;
    }

    public void setRecurrent(Boolean recurrent) {
        isRecurrent = recurrent;
    }
}
