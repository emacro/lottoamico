<%@ page language="java" contentType="text/html" errorPage="error.jsp"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="it.emacro.util.Utils,java.util.List" %>

<% 
	String[] dates = Utils.getDatesOrNull(session);
	
    // estrazione valori ricercati (su 8 caselle di testo)
	String n[] = Utils.getSerchValues(request,8);
    
    // inizializza count prelev. il valore dalla request
	int count = Utils.getNewCount(request);
	
	// estrazione date esistenti
	if(dates == null){
		dates = Utils.getAllExtractionDates();
		session.setAttribute(Utils.ALL_DATE, dates);
	}
	
	Utils.cashExtractionS(30);
	
//  cashing delle estrazioni --------------------------------------------------

	List<String[]> extraction;

	if(request.getParameter(Utils.IS_FIRST_RUN)==null){
		for(int ff=0;ff<30;ff++){
			session.setAttribute("ext" + ff, Utils.getExtraction(ff));
		}
	}
	
	if(request.getParameter(Utils.COUNT)!=null){
		extraction = (List<String[]>)session.getAttribute("ext" + count);
		// out.print(extraction);
		if(extraction == null){
			out.print("not cashed");
			extraction = Utils.getExtraction(count);
			session.setAttribute("ext" + count, extraction);
		}
	}else {
		out.print("not cashed");
		extraction = Utils.getExtraction(count);
		session.setAttribute("ext" + count, extraction);
	}

//  cashing delle estrazioni --------------------------------------------------
	
	// colore alternato
	boolean color = false;
	
	// indice dei checkbox
	int idx = 0;

%>