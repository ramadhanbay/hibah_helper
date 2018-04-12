package com.apps.pu.hibah;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkex.zul.Jasperreport;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.apps.pu.hibah.services.ReportService;
import com.apps.pu.hibah.ui.KeyValue;
import com.apps.pu.hibah.ui.PopupSingle;
import com.apps.pu.hibah.ui.SerializableSearchDelegate;
import com.google.common.base.Strings;


@VariableResolver(DelegatingVariableResolver.class)
public class ReportEntityComposser extends SelectorComposer<Window>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Wire
	private PopupSingle bnbDirektorat;
	@Wire
	private PopupSingle bnbSatuanKerja;
	@Wire
	private Textbox txtPemda;
	@Wire
	private Jasperreport report;
	@Wire
	private Listbox lstType;
	@Wire
	private Listbox lstPerolehan;
	
	@WireVariable(rewireOnActivate = true)
	private ReportService reportServiceImpl;
	
	
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);			
		
		Listitem listitem = new Listitem();
		listitem.setValue(null);
		listitem.setLabel(new String(Labels.getLabel("common.select")));
		listitem.setSelected(true);
		listitem.setParent(lstPerolehan);
		
		for(int i = 1; i<=10; i++) {
			int tahun = 2009+i;
			
			listitem = new Listitem();
			listitem.setValue(String.valueOf(tahun));
			listitem.setLabel(tahun+"");
			listitem.setSelected(false);
			listitem.setParent(lstPerolehan);
			
		}
		
		listitem = new Listitem();
		listitem.setValue("pdf");
		listitem.setLabel(new String(Labels.getLabel("report.typepdf")));
		listitem.setSelected(true);
		listitem.setParent(lstType);
		
		listitem = new Listitem();
		listitem.setValue("xls");
		listitem.setLabel(new String(Labels.getLabel("report.typeexcel")));
		listitem.setParent(lstType);
		
		bnbDirektorat.setTitle(Labels.getLabel("report.Direktorat"));
		bnbDirektorat.setSearchText("Direktorat");
		bnbDirektorat.setHeaderLabel("Direktorat");
		bnbDirektorat.setReadonly(true);
		bnbDirektorat.setSearchDelegate(eventSearchDir());
		bnbDirektorat.addEventListener(Events.ON_CLOSE, new EventListener<Event>() {

			@Override
			public void onEvent(Event event) throws Exception {
				// TODO Auto-generated method stub
				KeyValue keyVal = (KeyValue) event.getData();
				if(keyVal == null) {
					bnbSatuanKerja.setDisabled(true);
					bnbSatuanKerja.setRawValue(null);
				}else {
					bnbSatuanKerja.setDisabled(false);
				}
				
			}
		});
		
		bnbSatuanKerja.setDisabled(true);
		bnbSatuanKerja.setTitle(Labels.getLabel("report.satuankerja"));
		bnbSatuanKerja.setSearchText("Satuan Kerja");
		bnbSatuanKerja.setHeaderLabel("Satuan Kerja");
		bnbSatuanKerja.setReadonly(true);
		bnbSatuanKerja.setSearchDelegate(eventSearchSatker());
		
	}
	
	private SerializableSearchDelegate<?> eventSearchSatker() {
		
		return new SerializableSearchDelegate<KeyValue>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public List<KeyValue> findBySearchCriteria(String searchCriteria, int limit, int offset) {
				String dirName = (String) bnbDirektorat.getKeyValue().getValue();
				return reportServiceImpl.getSatkerByNamePaging(dirName, searchCriteria, limit, offset);
			}

			@Override
			public int countBySearchCriteria(String searchCriteria) {
				String dirName = (String) bnbDirektorat.getKeyValue().getValue();
				return reportServiceImpl.countSatkerByNamePaging(dirName, searchCriteria);
			}

			@Override
			public void mapper(KeyValue keyValue, KeyValue data) {
				
				if(data != null) {
					keyValue.setKey(data.getKey());
					keyValue.setValue(data.getValue());
					keyValue.setDescription(data.getDescription());
				}
				
			}
		};
	}

	private SerializableSearchDelegate<KeyValue> eventSearchDir() {
		
		return new SerializableSearchDelegate<KeyValue>() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void mapper(KeyValue keyValue, KeyValue data) {
				
				if(data != null) {
					keyValue.setKey(data.getKey());
					keyValue.setValue(data.getValue());
					keyValue.setDescription(data.getDescription());
				}			
				
			}
			
			@Override
			public List<KeyValue> findBySearchCriteria(String searchCriteria, int limit, int offset) {
				
				return reportServiceImpl.getDirektoratByNamePaging(searchCriteria, limit, offset);
			}
			
			@Override
			public int countBySearchCriteria(String searchCriteria) {
				return reportServiceImpl.countDirektoratByNamePaging(searchCriteria);
			}
		};		
		
	}

	@Listen("onClick=#btnReport")
	public void onReport(){
		
		Map<String, Object> parameters = new HashMap<String, Object>();		
		
		String typeReport = lstType.getSelectedItem().getValue();
		Integer satker = bnbSatuanKerja.getKeyValue() == null ? null : (Integer) bnbSatuanKerja.getKeyValue().getKey();
		String pemda = Strings.isNullOrEmpty(txtPemda.getValue()) ? null : txtPemda.getValue();
		String satkerDesc = bnbSatuanKerja.getKeyValue() == null ? null : (String) bnbSatuanKerja.getKeyValue().getDescription();
		Integer direktorat = bnbDirektorat.getKeyValue() == null ? null : (Integer) bnbDirektorat.getKeyValue().getKey();
		String dirDesc = bnbDirektorat.getKeyValue() == null ? null : (String) bnbDirektorat.getKeyValue().getDescription();
		String ta = lstPerolehan.getSelectedItem().getValue();
		
		parameters.put("pSatker", satker);
		parameters.put("pSatkerDesc", satkerDesc);
		parameters.put("pPemda", pemda);
		parameters.put("pDirektorat", direktorat);
		parameters.put("pTahunAnggaran", ta);
		parameters.put("pDirektoratDesc", dirDesc);
		
		try {
			report.setDataConnection(reportServiceImpl.getConnection());
		}catch (Exception e) {
			e.printStackTrace();
		}	
		
		report.setSrc("report/entity_report.jasper");
		report.setType(typeReport);
		report.setParameters(parameters);
		
		
	}
}
