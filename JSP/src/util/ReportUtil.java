package util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.collections.map.HashedMap;

import jakarta.servlet.ServletContext;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class ReportUtil implements Serializable {

	private static final long serialVersionUID = 1L;

	//metodo para gerar relatorio no exel
	public byte[] geraReltorioExcel(List listaDados, String nomeRelatorio, HashMap<String, Object> params,
			ServletContext servletContext) throws Exception {

		/*
		 * Cria a lista de dados que vem do nosso SQL da consulta feita e imprimi os
		 * telefones
		 */
		JRBeanCollectionDataSource jrbcds = new JRBeanCollectionDataSource(listaDados);

		String caminhoJasper = servletContext.getRealPath("relatorio") + File.separator + nomeRelatorio + ".jasper";

		JasperPrint impressoraJasper = JasperFillManager.fillReport(caminhoJasper, params, jrbcds);
		
		JRExporter exporter = new JRXlsxExporter();//Excel
		
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, impressoraJasper);
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
		
		exporter.exportReport();

		return baos.toByteArray();

	}

	//metodo para gerar um sub relario e trazer os telefones de cada usuario no PDF
	public byte[] geraReltorioPDF(List listaDados, String nomeRelatorio, HashMap<String, Object> params,
			ServletContext servletContext) throws Exception {

		/*
		 * Cria a lista de dados que vem do nosso SQL da consulta feita e imprimi os
		 * telefones
		 */
		JRBeanCollectionDataSource jrbcds = new JRBeanCollectionDataSource(listaDados);

		String caminhoJasper = servletContext.getRealPath("relatorio") + File.separator + nomeRelatorio + ".jasper";

		JasperPrint impressoraJasper = JasperFillManager.fillReport(caminhoJasper, params, jrbcds);

		return JasperExportManager.exportReportToPdf(impressoraJasper);

	}

	// metodo para gerar o relatorio em pdf
	public byte[] geraRelatorioPDF(List listaDados, String nomeRelatorio, ServletContext servletContext)
			throws Exception {

		// cria a lista de dados que vem do nosso SQL da consulta feita
		JRBeanCollectionDataSource jrbcds = new JRBeanCollectionDataSource(listaDados);

		String caminhoJasper = servletContext.getRealPath("relatorio") + File.separator + nomeRelatorio + "jasper.";

		// passando o caminho do relatorio e fazendo a impressao
		JasperPrint ImpressoraJasper = JasperFillManager.fillReport(caminhoJasper, new HashedMap(), jrbcds);

		return JasperExportManager.exportReportToPdf(ImpressoraJasper);
	}
}
