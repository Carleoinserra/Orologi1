package com.example.demo;


	
	import java.util.ArrayList;

	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.stereotype.Controller;
	import org.springframework.ui.Model;
	import org.springframework.web.bind.annotation.GetMapping;
	import org.springframework.web.bind.annotation.PostMapping;
	import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


	//dichiariamo una classe semplice con annotazione controller
   
	@Controller
	public class MyController {

		
		 private final WatchJDBCTemplate watchJDBCTemp;

		    @Autowired
		    public MyController(WatchJDBCTemplate watchJDBCTemp) {
		        this.watchJDBCTemp = watchJDBCTemp;
		    }
		
		
	//mappiamo i metodi con get mapping per indicare quelli che saranno i percorsi dell'applicazione
		
		
		 
		 
		 @GetMapping("/")
		 public String getDip(){
			 
			 
		
			
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
}
