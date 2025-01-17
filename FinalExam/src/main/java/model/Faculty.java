package model;

public class Faculty {
	private String IDFac;
	private String NameFac;

	public Faculty() {
	}

	public Faculty(String nameFac) {
		NameFac = nameFac;
	}
	public Faculty(String iDFac, String nameFac) {
		IDFac = iDFac;
		NameFac = nameFac;

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

	@Override
	public String toString() {
		return "Faculty [IDFac=" + IDFac + ", NameFac=" + NameFac + "]";
	}

}
