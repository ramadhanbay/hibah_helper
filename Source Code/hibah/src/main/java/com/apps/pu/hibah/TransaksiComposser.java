package com.apps.pu.hibah;

import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.Window;

@VariableResolver(DelegatingVariableResolver.class)
public class TransaksiComposser  extends SelectorComposer<Window>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
	}

}
