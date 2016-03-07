<%@ page language="java" contentType="text/html" %>
<%@page pageEncoding="UTF-8"%>
<%@page import="it.emacro.util.Utils" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>

<% 
	// String[] dates = Utils.getDatesOrNull(session);

	String[] dates = Utils.getAllExtractionDates();
	
    // estrazione valori ricercati (su 8 caselle di testo)
	String n[] = Utils.getSerchValues(request,8);
    
    // inizializza count prelev. il valore dalla request
	int count = Utils.getNewCount(request);
	
	// estrazione date esistenti
	//if(dates == null){
	//	dates = Utils.getAllExtractionDates();
	//	session.setAttribute(Utils.ALL_DATE, dates);
	//}
	
	// colore alternato
	boolean color = false;
	
	// indice dei checkbox
	int idx = 0;

%>

<html>
	<head>
		<title>Lotto</title>
		<SCRIPT TYPE="text/javascript">
<!--
	
	function submitenter(myfield,e) {
		var keycode;
		
		if (window.event){ 
			keycode = window.event.keyCode;
		}
		else if (e) { 
			keycode = e.which;
		}
		else {
			return true;
		}
	
		if (keycode == 13) {
	   		myfield.form.submit();
	   		return false;
	   	} 
	   	else {
	   		return true;
	   	}
	}
	
	function setAllCbx(obj){
		var isChecked = obj.checked;
		
		for(var kk=0;kk<11;kk++){
			document.getElementById("cbx"+kk).checked = isChecked;
		}
		document.getElementById("subm").submit();
	}
	
	function clearTextBox(){
		for(var kk=0;kk<8;kk++){
			document.getElementById("n"+kk).value = '';
		}
		document.getElementById("subm").submit();
	}
	
	function onTbBlur(idxb){
		// document.getElementById("subm").submit();
		// document.getElementById('n'+idxb).setFocus();
	}
//-->
</SCRIPT>
	</head>

	<body bgcolor="lightblue">

		<!-- stampa la data dell'estrazione per esteso -->
		<h1 align="center"><%=Utils.getParsedDate(count) %></h1>
	
		<!-- passa in request il numero dell'array dell'estrazione visualizzata  -->
		<form id="subm" method="post" action="index.jsp?count=<%=count %>">
	
		<!-- tabella esterna -->
		<table border="1" align="center" >
		
		<tr><td >
		
		<table border="0" align="center" cellspacing="5" cellpadding="5">
		<tr bgcolor="#98AFC7">
		<td align='left' width="93"><strong>RUOTA</strong></td>
		<td align='center' width="15"><strong>I</strong></td>
		<td align='center' width="15"><strong>II</strong></td>
		<td align='center' width="15"><strong>III</strong></td>
		<td align='center' width="15"><strong>IV</strong></td>
		<td align='center' width="15"><strong>V</strong></td>
		<td align="left">
			<input id="mainCbx" name="mainCbx" type="checkbox" 
					value="checked" <%=Utils.getCheckboxState(request,"mainCbx") %> onClick="setAllCbx(this)"/>
		</tr>
		</table>
		
		</td></tr>
		
		<tr><td colspan="2">
		
			<table border="0" align="center" cellspacing="5" cellpadding="5">
			
			<!-- estrae intera estrazione -->
			<c:forEach var="s" items="<%=Utils.getExtraction(count)%>">

				<c:choose>
					<c:when test="<%=color %>">
						<tr bgcolor='#F0F0F0'>
					</c:when>
					<c:otherwise>
						<tr bgcolor='#FFFFFF'>
					</c:otherwise>
				</c:choose>
			
				<%color = !color; %>
			
				<c:choose>
				
					<c:when test="<%=Utils.mustWriteNumbers(request,"cbx"+idx) %>">
			
						<c:forTokens var="ii" items="0,1,2,3,4,5" delims=",">
						
							<c:choose>
								<c:when test="${s[ii] == 'BARI' || s[ii] == 'CAGLIARI' || s[ii] == 'FIRENZE' || s[ii] == 'GENOVA' || s[ii] == 'MILANO' || s[ii] == 'NAPOLI' || s[ii] == 'PALERMO' || s[ii] == 'ROMA' || s[ii] == 'TORINO' || s[ii] == 'VENEZIA' || s[ii] == 'NAZIONALE'}">
									<!-- e' una ruota -->
									<td align='left'><strong><c:out value="${s[0]}"/></strong></td>
								</c:when>
								
								<c:otherwise>
								
									<!-- e' un numero quindi controlla su gli 8 numeri cercati -->
									<c:set var="mustDo" value="false"/>
									<c:forEach var="nnn" items="<%=n%>">
										<c:if test="${s[ii] == nnn}">
											<c:set var="mustDo" value="true"/>
										</c:if>
									</c:forEach>
									<c:choose> 
										<c:when test="${mustDo}">
											<td align='right' bgcolor='yellow'><strong><c:out value="${s[ii]}"/></strong></td>
										</c:when>
										<c:otherwise>
											<td align='right'><strong><c:out value="${s[ii]}"/></strong></td>
										</c:otherwise>
									</c:choose> 
								</c:otherwise>
							</c:choose>
				
						</c:forTokens>
				
					</c:when>
				
					<c:otherwise>
		
						<!-- il checkbox e' UNCHECKED, quindi i numeri non verranno visualizzati 
			      	     	scrive solo il nome ruota -->
			 			<td align='left'><c:out value="${s[0]}"/></td>
						<td colspan='5'  width="150"></td>
		
					</c:otherwise>
				</c:choose>
			
				<td>
			
				<input id="cbx<%=idx %>" name="cbx<%=idx %>" type="checkbox" 
					value="checked" <%=Utils.getCheckboxState(request,"cbx"+idx) %> onClick="submit()"/>
				 
				</td>
	
				<% idx++; %>
			</tr>

			</c:forEach>
		
			</table>
			
		</td></tr></table>	
	
		<br/>
	
		<table align="center" border="0" >
			<tr>
				<td><input type="text" id="n0" name="n0" size="1" maxlength="2" value="<%=n[0] %>" onKeyPress="return submitenter(this,event)"/></td>
				<td><input type="text" id="n1" name="n1" size="1" maxlength="2" value="<%=n[1] %>" onKeyPress="return submitenter(this,event)"/></td>
				<td><input type="text" id="n2" name="n2" size="1" maxlength="2" value="<%=n[2] %>" onKeyPress="return submitenter(this,event)"/></td>
				<td><input type="text" id="n3" name="n3" size="1" maxlength="2" value="<%=n[3] %>" onKeyPress="return submitenter(this,event)"/></td>
				<td><input type="text" id="n4" name="n4" size="1" maxlength="2" value="<%=n[4] %>" onKeyPress="return submitenter(this,event)"/></td>
				<td><input type="text" id="n5" name="n5" size="1" maxlength="2" value="<%=n[5] %>" onKeyPress="return submitenter(this,event)"/></td>
				<td><input type="text" id="n6" name="n6" size="1" maxlength="2" value="<%=n[6] %>" onKeyPress="return submitenter(this,event)"/></td>
				<td><input type="text" id="n7" name="n7" size="1" maxlength="2" value="<%=n[7] %>" onKeyPress="return submitenter(this,event)"/></td>
				<td><input type="button" value="svuota" onClick="clearTextBox()"/></td>
			</tr>
			
			<tr><td colspan="8"><table><tr><td></td></tr></table></td></tr>
			
			<tr>
				<td colspan="9" >
					<table border="0" width="100%"><tr>
						<td align="left">
							<input name="<%=Utils.PREVIOUS %>" type="submit" value="<%=Utils.PREVIOUS %>"/>
						</td>
						<td align="center">
							<input name="<%=Utils.INDIETRO %>" type="submit" value=" - "/>
							<input type="text" id="<%=Utils.JUMP %>" name="<%=Utils.JUMP %>" size="1" maxlength="2" value="<%=Utils.getValue(request,Utils.JUMP) %>"/>
							<input name="<%=Utils.AVANTI %>" type="submit" value=" + "/>
						</td>
						<td align="center">
							<input name="<%=Utils.APPLY %>" type="submit" value="<%=Utils.APPLY %>"/>
						</td>
						<td align="right">
							<input name="<%=Utils.NEXT %>" type="submit" value="<%=Utils.NEXT %>"/>
						</td>
					</tr></table>
				</td>
			</tr>
		</table>
		
		<input type="hidden" name="<%=Utils.IS_FIRST_RUN %>" value="false"/>
		
	</form>
	
</body>
</html>