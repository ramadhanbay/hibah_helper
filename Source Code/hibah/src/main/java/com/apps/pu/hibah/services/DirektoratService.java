package com.apps.pu.hibah.services;

import java.util.List;

import com.apps.pu.hibah.entity.Direktorat;

public interface DirektoratService {

	List<Direktorat> findDirektoratLikeName(String dirName);

}
