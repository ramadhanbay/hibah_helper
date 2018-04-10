package com.apps.pu.hibah;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkex.zul.Jasperreport;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Longbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.apps.pu.hibah.services.ReportService;
import com.apps.pu.hibah.ui.KeyValue;
import com.apps.pu.hibah.ui.PopupDouble;
import com.apps.pu.hibah.ui.SerializableSearchDelegateDoubleCriteria;
import com.google.common.base.Strings;


@VariableResolver(DelegatingVariableResolver.class)
public class ReportProgressComposser extends SelectorComposer<Window>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Wire
	private Listbox lstStatus;
	@Wire
	private PopupDouble bnbSatuanKerja;
	@Wire
	private Textbox txtPemda;
	@Wire
	private Longbox nilaiMin;
	@Wire
	private Longbox nilaiMax;
	@Wire
	private Jasperreport report;
	@Wire
	private Listbox lstType;
	
	@WireVariable(rewireOnActivate = true)
	private ReportService reportServiceImpl;
	
	
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);			
		
		Listitem listitem = new Listitem();
		listitem.setValue("-1");
		listitem.setLabel(new String(Labels.getLabel("common.select")));
		listitem.setSelected(true);
		listitem.setParent(lstStatus);
		
		listitem = new Listitem();
		listitem.setValue("1");
		listitem.setLabel(new String(Labels.getLabel("report.progress.sedang")));
		listitem.setSelected(true);
		listitem.setParent(lstStatus);
		
		listitem = new Listitem();
		listitem.setValue("2");
		listitem.setLabel(new String(Labels.getLabel("report.progress.terbit")));
		listitem.setSelected(true);
		listitem.setParent(lstStatus);
		
		listitem = new Listitem();
		listitem.setValue("3");
		listitem.setLabel(new String(Labels.getLabel("report.progress.diterima")));
		listitem.setSelected(true);
		listitem.setParent(lstStatus);
		
		listitem = new Listitem();
		listitem.setValue("pdf");
		listitem.setLabel(new String(Labels.getLabel("report.typepdf")));
		listitem.setSelected(true);
		listitem.setParent(lstType);
		
		listitem = new Listitem();
		listitem.setValue("xls");
		listitem.setLabel(new String(Labels.getLabel("report.typeexcel")));
		listitem.setSelected(true);
		listitem.setParent(lstType);
		
		bnbSatuanKerja.setTitle(Labels.getLabel("com.proficiencyLevel"));
		bnbSatuanKerja.setSearchText1("Direktorat");
		bnbSatuanKerja.setHeaderLabel1("Direktorat");
		bnbSatuanKerja.setSearchText2("Satker");
		bnbSatuanKerja.setHeaderLabel2("Satker");
		bnbSatuanKerja.setReadonly(true);
		bnbSatuanKerja.setSearchDelegate(eventSearchParent());
		
	}
	
	private SerializableSearchDelegateDoubleCriteria<KeyValue> eventSearchParent() {
		
		return new SerializableSearchDelegateDoubleCriteria<KeyValue>() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void mapper(KeyValue keyValue, KeyValue data) {
				
				keyValue.setKey(data.getKey());
				keyValue.setValue(data.getValue());
				keyValue.setDescription(data.getDescription());
				
			}
			
			@Override
			public List<KeyValue> findBySearchCriteria(String searchCriteria1, String searchCriteria2, int limit, int offset) {
				
				return reportServiceImpl.getSatkerByNamePaging(searchCriteria1, searchCriteria2, limit, offset);
			}
			
			@Override
			public int countBySearchCriteria(String searchCriteria1, String searchCriteria2) {
				// TODO Auto-generated method stub
				return reportServiceImpl.countSatkerByNamePaging(searchCriteria1, searchCriteria2);
			}
		};
		
		
	}

	@Listen("onClick=#btnReport")
	public void onReport(){

		Map<String, Object> parameters = new HashMap<String, Object>();
		
		String typeReport = lstType.getSelectedItem().getValue();
		Integer satker = bnbSatuanKerja.getKeyValue() == null ? null : (Integer) bnbSatuanKerja.getKeyValue().getKey();
		String pemda = Strings.isNullOrEmpty(txtPemda.getValue()) ? null : txtPemda.getValue();
		String satkerDesc = bnbSatuanKerja.getKeyValue() == null ? null : (String) bnbSatuanKerja.getKeyValue().getValue();
		
		parameters.put("pStatus", "Sedang Proses");
		parameters.put("pSatker", satker);
		parameters.put("pSatkerDesc", satkerDesc);
		parameters.put("pPemda", pemda);
		parameters.put("pNilaiMin", nilaiMin.getValue());
		parameters.put("pNilaiMax", nilaiMax.getValue());
		
		report.setType(typeReport);
		report.setParameters(parameters);
		report.setSrc("report/sedangproses.jasper");
		
	}
}
