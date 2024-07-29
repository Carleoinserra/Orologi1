package com.example.demo;


	
	import java.util.ArrayList;

	import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
	import org.springframework.ui.Model;
	import org.springframework.web.bind.annotation.GetMapping;
	import org.springframework.web.bind.annotation.PostMapping;
	import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


	//dichiariamo una classe semplice con annotazione controller
   
	@Controller
	public class MyController {

		@Autowired
	    private EmailService emailService;
		
		 private final WatchJDBCTemplate watchJDBCTemp;

		    @Autowired
		    public MyController(WatchJDBCTemplate watchJDBCTemp) {
		        this.watchJDBCTemp = watchJDBCTemp;
		    }
		
		
	//mappiamo i metodi con get mapping per indicare quelli che saranno i percorsi dell'applicazione
		
		
		 
		 
		 @GetMapping("/")
		 public String getDip(){
			 
			 
			 ArrayList<orologio> lista = watchJDBCTemp.ritornaOrologi();
			 //watchJDBCTemp.insertOrologi(lista);
		
			
			 return "formOro";
			 
		 }
		 
		 @PostMapping("/AddWatch")
		 public String addWatch(@RequestParam("nome") String nome,@RequestParam("marca") String marca, @RequestParam("prezzo") String prezzo,@RequestParam("tipologia") String tipologia, 
				 @RequestParam("url") String url,Model model){
			
			 orologio g1 = new orologio();
			 g1.setMarca(marca);
			 g1.setModello(nome);
			 g1.setTipologia(tipologia);
			 g1.setPrezzo(Double.parseDouble(prezzo));
			 g1.setUrlImage(url);
			 model.addAttribute("watch", g1);
			 
			 watchJDBCTemp.insertOrologio(marca,nome , g1.getPrezzo(), tipologia, url);
			 
			 return "replaceAdd";
			 
		 }
		 
		 @PostMapping("/CambiaPrezzo")
		 public String cambiaPrezzo(@RequestParam("modello") String modello, @RequestParam("prezzo") String prezzo) {
			 
			 double prezzo1 = Double.parseDouble(prezzo);
			 
			 watchJDBCTemp.updatePrezzo(prezzo1, modello);
			 
			 return "changeP";
		 }
		 @GetMapping("/store") 
		 public String getStore(Model model){
			
			 ArrayList<orologio> orologi = watchJDBCTemp.ritornaOrologi();
			 model.addAttribute("lista", orologi);
			 return "getStore";
			 
		 }
		 
		 @PostMapping("/buyWatch")
		 public String addWatch(@RequestParam(value = "ordini", required = false) String[] ordini,@RequestParam("quantities") String [] pezzi,Model model){
			
			 ArrayList <orologio> lista = watchJDBCTemp.ritornaOrologi();
			 ArrayList<Integer> qnt = new ArrayList <>();
			 ArrayList<watchOrd> listaO = new ArrayList <>();
			 
			 for (String s: pezzi) {
				 if (!s.isEmpty()) {
				int x = Integer.parseInt(s);
				qnt.add(x);
				
			 }}
			 double prezzo = 0;
			 
			 if (ordini == null) {
				 return "errorPage";
				 
			 }
			 
			 if (ordini != null ) { 
				 
				 if (ordini.length != 0) {
				 
				 for (int j = 0; j < ordini.length; j++) {
					 
					 
					 for (int i = 0; i < lista.size(); i++) {
					 if (lista.get(i).getModello().equals(ordini[j])) {
						
						// pcJDBCTemp.updatePezzi(qnt.get(j),lista.get(i).id  );
						 prezzo += lista.get(i).getPrezzo() * qnt.get(j);
						 watchOrd o1 = new watchOrd();
						o1.setModello(lista.get(i).getModello());
						 o1.setQnt(qnt.get(j));
						 listaO.add(o1);
						 
					 }
					 
				 }
			 }}
			 }
			

			 
			 for (String s: ordini) {
				 System.out.println(s);
			 }
			
			
			System.out.println(prezzo);
			
	   model.addAttribute("lista", listaO);
	  
	   model.addAttribute("prezzo", prezzo);
	   
			 
			
			 
			
			 
			 return "conferma";
		 }
		 
		 
		 @PostMapping("/confWatch")
		 public ResponseEntity<String> confermaPc(@RequestParam("modello") String [] ordini,@RequestParam("qnt") String [] pezzi,@RequestParam("prezzo") String prezzo,
				 @RequestParam("email") String email,
				 Model model){
	 ArrayList<watchOrd> listaO = new ArrayList <>();
	 ArrayList<Integer> qnt = new ArrayList <>();
	 ArrayList <orologio> lista = watchJDBCTemp.ritornaOrologi();
			 
			 for (String s: pezzi) {
				 if (!s.isEmpty()) {
				int x = Integer.parseInt(s);
				qnt.add(x);
				
			 }}
			 
			 
			
				 
				 for (int j = 0; j < ordini.length; j++) {
					 for (int i = 0; i < lista.size(); i++) {
					 if (lista.get(i).getModello().equals(ordini[j])) {
						
						 
						
						 watchOrd watch = new watchOrd();
						 watch.setModello(lista.get(i).getModello());
						 watch.setQnt(qnt.get(j));
						 listaO.add(watch);
						 
					 }
					 
				 }}
				 
				 
				 // andiamo ad aggiungere i pezzi dei prodotti acquistati nella tabella pcorders
				 for (watchOrd w1 : listaO) {
				 watchJDBCTemp.updateWatchOrders(w1.getModello(), w1.qnt);}
				 
				 
				 
				 String to = email;
				 String subject = "ordine da TalentformStore confermato";
				 String text = "";
				 text+= "Hai acquistato:  \n";
				 for (watchOrd comp : listaO) {
					 
					 text+= "Modello: " + comp.getModello() + "\n";
					 text+= "Numero pezzi: " + comp.getQnt() + "\n";
				 }
				 
				 text += "Il prezzo totale da pagare è " + prezzo + " euro";
				  emailService.sendSimpleEmail(to, subject, text);
				 
			 
				 
			 
				  return ResponseEntity.ok("Email sent successfully, grazie per  l'acquisto, torni a trovarci");
		 }
		 
		
		 
}
