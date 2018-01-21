package com.apps.pu.hibah.services;

import com.apps.pu.hibah.entity.Log;
import java.util.List;

public interface MyService {

	Log addLog(Log log);

	List<Log> getLogs();

	void deleteLog(Log log);
}
