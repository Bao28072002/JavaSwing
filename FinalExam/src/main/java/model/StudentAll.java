package model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class StudentAll {
	private String id;
	private String name;
	private boolean gender;
	private LocalDate dob;
	private String diaChi;
	private String IDFac;
	private String NameFac;
	private String idclass;
	private String IDclass;
	private String NameClass;
	private String idStu;
	private String idSub;
	private String nameSub;
	private BigDecimal sc1;
	private BigDecimal sc2;
	private BigDecimal sc3;
	private BigDecimal scTotal;
	private String rate;

	public StudentAll() {
	}


	public StudentAll(String id, String name, boolean gender, LocalDate dob, String diaChi, String iDFac,
			String nameFac, String idclass, String iDclass2, String nameClass, String idStu, String idSub,
			String nameSub, BigDecimal sc1, BigDecimal sc2, BigDecimal sc3, BigDecimal scTotal, String rate) {
		this.id = id;
		this.name = name;
		this.gender = gender;
		this.dob = dob;
		this.diaChi = diaChi;
		IDFac = iDFac;
		NameFac = nameFac;
		this.idclass = idclass;
		IDclass = iDclass2;
		NameClass = nameClass;
		this.idStu = idStu;
		this.idSub = idSub;
		this.nameSub = nameSub;
		this.sc1 = sc1;
		this.sc2 = sc2;
		this.sc3 = sc3;
		this.scTotal = scTotal;
		this.rate = rate;
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isGender() {
		return gender;
	}

	public void setGender(boolean gender) {
		this.gender = gender;
	}

	public LocalDate getDob() {
		return dob;
	}

	public void setDob(LocalDate dob) {
		this.dob = dob;
	}

	public String getDiaChi() {
		return diaChi;
	}

	public void setDiaChi(String diaChi) {
		this.diaChi = diaChi;
	}

	public String getIDFac() {
		return IDFac;
	}

	public void setIDFac(String iDFac) {
		IDFac = iDFac;
	}

	public String getNameFac() {
		return NameFac;
	}

	public void setNameFac(String nameFac) {
		NameFac = nameFac;
	}

	public String getIdclass() {
		return idclass;
	}

	public void setIdclass(String idclass) {
		this.idclass = idclass;
	}

	public String getIDclass() {
		return IDclass;
	}

	public void setIDclass(String iDclass) {
		IDclass = iDclass;
	}

	public String getNameClass() {
		return NameClass;
	}

	public void setNameClass(String nameClass) {
		NameClass = nameClass;
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

	public String getNameSub() {
		return nameSub;
	}

	public void setNameSub(String nameSub) {
		this.nameSub = nameSub;
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

}
