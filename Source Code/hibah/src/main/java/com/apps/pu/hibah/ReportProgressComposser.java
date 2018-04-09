package com.apps.pu.hibah;

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
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.apps.pu.hibah.services.ReportService;
import com.apps.pu.hibah.ui.KeyValue;
import com.apps.pu.hibah.ui.PopupDouble;
import com.apps.pu.hibah.ui.SerializableSearchDelegateDoubleCriteria;


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
	private Intbox nilaiMin;
	@Wire
	private Intbox nilaiMax;
	@Wire
	private Jasperreport report;
	
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
		
		parameters.put("pStatus", "Sedang Proses");
		parameters.put("pSatker", null);
		parameters.put("pPemda", null);
		parameters.put("pNilaiMin", 0);
		parameters.put("pNilaiMax", 300000000);
		
		report.setType("pdf");
		report.setParameters(parameters);
		report.setSrc("report/sedangproses.jasper");
		
	}
}
