package it.emacro.util;

import it.emacro.log.Log;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("serial")
public class RuoteUtils {
	
	private static final Map<String, SimpleRuota> RUOTE_MAP = 
			new LinkedHashMap<String, SimpleRuota>(){{
				
				put("BA", new RuoteUtils().new SimpleRuota(0,"BARI"));
				put("CA", new RuoteUtils().new SimpleRuota(1,"CAGLIARI"));
				put("FI", new RuoteUtils().new SimpleRuota(2,"FIRENZE"));
				put("GE", new RuoteUtils().new SimpleRuota(3,"GENOVA"));
				put("MI", new RuoteUtils().new SimpleRuota(4,"MILANO"));
				put("NA", new RuoteUtils().new SimpleRuota(5,"NAPOLI"));
				put("PA", new RuoteUtils().new SimpleRuota(6,"PALERMO"));
				put("RM", new RuoteUtils().new SimpleRuota(7,"ROMA"));
				put("TO", new RuoteUtils().new SimpleRuota(8,"TORINO"));
				put("VE", new RuoteUtils().new SimpleRuota(9,"VENEZIA"));
				put("RN", new RuoteUtils().new SimpleRuota(10,"NAZIONALE"));
	}};
	
	private RuoteUtils() {
	}
	
	
	public static Ordinator getNewOrdinator() {
		return new RuoteUtils().new Ordinator();
	}
	
	public class Ordinator {
		
		private List<String[]> numbersArrays;

		private Ordinator() {
			numbersArrays = new ArrayList<String[]>(RUOTE_MAP.size());
			for(int i=0; i<RUOTE_MAP.size();i++) numbersArrays.add(null);
		}
		
		public Ordinator add(String sigla, String...numbers) {
			
			SimpleRuota localRuota = RUOTE_MAP.get(sigla);
			
			if(localRuota != null){
				numbersArrays.set(localRuota.order, numbers);
			}else{
				Log.print("Non riesco ad identificare la ruota con la sigla " + sigla);
				throw new IllegalArgumentException("Non riesco ad identificare la ruota con la sigla " + sigla);
			}
			
			return this;
		}
		
		public Ordinator add(SimpleRuota simpleRuota) {
			
			SimpleRuota localRuota = RUOTE_MAP.get(simpleRuota.name);
			
			if(localRuota != null){
				numbersArrays.set(localRuota.order, simpleRuota.numbers); 
			}else{
				Log.print("Non riesco ad identificare la ruota con la sigla " + simpleRuota.name);
				throw new IllegalArgumentException("Non riesco ad identificare la ruota con la sigla " + simpleRuota.name);
			}
			
			return this;
		}
		
	}
	
	public static List<String> getNumbersFromOrdinator(Ordinator ordinator) {
		List<String> res = new ArrayList<String>();
		for (int i = 0; i < RUOTE_MAP.size(); i++) {
		 
			if(ordinator.numbersArrays.get(i) == null) {
				for (int j = 0; j < 5; j++) res.add("0");
			}
			else{
				for (String number : ordinator.numbersArrays.get(i)) {
					res.add(number);
				}
			}
		}
		return res;
	}
	
	public static SimpleRuota getNewSimpleRuota(String name, String...numbers) {
		return new RuoteUtils().new SimpleRuota(0, name, numbers);
	}
	
	public class SimpleRuota {
		private Integer order;  
		String name; String[] numbers;
		private SimpleRuota(Integer order, String name, String...numbers) {
			this.order = order;
			this.name = name;
			this.numbers = numbers;
		}
	}
	

}
