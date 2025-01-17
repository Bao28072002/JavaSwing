package model;

import java.math.BigDecimal;

public class Score {
	private String idStu;
	private String idSub;
	private BigDecimal sc1;
	private BigDecimal sc2;
	private BigDecimal sc3;
	private BigDecimal scTotal;
	private String rate;

	public Score() {
	}

	public Score(String idStu, String idSub, BigDecimal sc1, BigDecimal sc2, BigDecimal sc3, BigDecimal scTotal,
			String rate) {
		this.idStu = idStu;
		this.idSub = idSub;
		this.sc1 = sc1;
		this.sc2 = sc2;
		this.sc3 = sc3;
		this.scTotal = scTotal;
		this.rate = rate;
	}

	public String getIdStu() {
		return idStu;
	}

	public void setIdStu(String idStu) {
		this.idStu = idStu;
	}

	public String getIdSub() {
		return idSub;
	}

	public void setIdSub(String idSub) {
		this.idSub = idSub;
	}

	public BigDecimal getSc1() {
		return sc1;
	}

	public void setSc1(BigDecimal sc1) {
		this.sc1 = sc1;
	}

	public BigDecimal getSc2() {
		return sc2;
	}

	public void setSc2(BigDecimal sc2) {
		this.sc2 = sc2;
	}

	public BigDecimal getSc3() {
		return sc3;
	}

	public void setSc3(BigDecimal sc3) {
		this.sc3 = sc3;
	}

	public BigDecimal getScTotal() {
		return scTotal;
	}

	public void setScTotal(BigDecimal scTotal) {
		this.scTotal = scTotal;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	@Override
	public String toString() {
		return "Score [idStu=" + idStu + ", idSub=" + idSub + ", sc1=" + sc1 + ", sc2=" + sc2 + ", sc3=" + sc3
				+ ", scTotal=" + scTotal + ", rate=" + rate + "]";
	}

}
