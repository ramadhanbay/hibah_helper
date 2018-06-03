package com.apps.pu.hibah.tools;

public enum PageEnum {

	HMPG("Home"),
	USRADM("User Admin"),
	RLADM("Role Admin"),
	PRGREP("Progress"),
	CATREP("Kategori"),
	INPDIR("Direktorat"),
	INPSAT("Satker"),
	INPBAR("Barang"),
	TRX("Transaksi"),
	;
	
	private String value;
	
	private PageEnum(String value) {
		this.setValue(value);
	}
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
