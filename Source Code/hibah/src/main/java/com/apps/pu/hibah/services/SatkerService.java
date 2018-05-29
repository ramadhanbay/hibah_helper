package com.apps.pu.hibah.services;

import java.util.List;

import com.apps.pu.hibah.entity.Satker;


public interface SatkerService {

	List<Satker> findSatkerByDirektorat(Integer idDirektorat, String  satker);

	void save(Satker satker);

	void delete(Satker satker);
}
