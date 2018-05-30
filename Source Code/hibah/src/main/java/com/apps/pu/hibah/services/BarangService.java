package com.apps.pu.hibah.services;

import java.util.List;

import com.apps.pu.hibah.entity.Barang;

public interface BarangService {

	List<Barang> findBarangBykodeAndName(String kode, String name);

	void delete(Barang barang);

	void save(Barang barang);

}
