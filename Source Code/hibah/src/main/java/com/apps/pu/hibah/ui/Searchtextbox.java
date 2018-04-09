package com.apps.pu.hibah.ui;

import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Textbox;

import com.apps.pu.hibah.tools.StringUtil;

public class Searchtextbox extends Textbox{
	
	private static final long serialVersionUID = 1L;
	
	@Override
	public String getValue() throws WrongValueException {
		String value =  super.getValue();
		return StringUtil.wrap(value, "%", true);
	}
}
