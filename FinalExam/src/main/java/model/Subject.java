package model;

public class Subject {
	private String iDSub;
	private String NameSub;
	private int Numcredits;

	public Subject() {
	}

	public Subject(String iDSub, String nameSub, int numcredits) {
		this.iDSub = iDSub;
		NameSub = nameSub;
		Numcredits = numcredits;
	}

	public String getiDSub() {
		return iDSub;
	}

	public void setiDSub(String iDSub) {
		this.iDSub = iDSub;
	}

	public String getNameSub() {
		return NameSub;
	}

	public void setNameSub(String nameSub) {
		NameSub = nameSub;
	}

	public int getNumcredits() {
		return Numcredits;
	}

	public void setNumcredits(int numcredits) {
		Numcredits = numcredits;
	}

	@Override
	public String toString() {
		return "Subject [iDSub=" + iDSub + ", NameSub=" + NameSub + ", Numcredits=" + Numcredits + "]";
	}

}
