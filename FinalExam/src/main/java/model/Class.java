package model;

public class Class {
	private String IDclass;
	private String NameClass;
	private String IDFac;

	public Class() {
	}

	public Class(String iDclass, String nameClass, String iDFac) {
		IDclass = iDclass;
		NameClass = nameClass;
		IDFac = iDFac;
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

	public String getIDFac() {
		return IDFac;
	}

	public void setIDFac(String iDFac) {
		IDFac = iDFac;
	}

	@Override
	public String toString() {
		return "Class [IDclass=" + IDclass + ", NameClass=" + NameClass + ", IDFac=" + IDFac + "]";
	}

}