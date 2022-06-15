package com.example.biletykino.Kontroler;

import com.example.biletykino.Model.Rezerwacja;
import com.example.biletykino.Model.SalaKinowa;
import com.example.biletykino.Model.Seans;
import com.example.biletykino.Repo.RezerwacjaRepository;
import com.example.biletykino.Repo.SalaKinowaRepository;
import com.example.biletykino.Repo.SeansRepository;
import com.example.biletykino.Singleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@RestController
public class BiletyKinoController {
    @Autowired
    private SeansRepository seansRepository;
    @Autowired
    private SalaKinowaRepository salaKinowaRepository;
    @Autowired
    private RezerwacjaRepository rezerwacjaRepository;

    @PostMapping("/dodaj_seans")
    public String addSeans(String tytuł,
                           Integer czas,
                           String data,
                           Integer id_sali) throws ParseException {
        Seans seans = new Seans();
        seans.setTytuł(tytuł);
        seans.setCzas(czas);
        seans.setSalaKinowa(salaKinowaRepository.findSalaKinowaById(id_sali));
        data=data.replace('T','-');
        SimpleDateFormat dzien=new SimpleDateFormat("yyyy-MM-dd-HH:mm");
        seans.setData(dzien.parse(data));
        seansRepository.save(seans);
        return "Seans dodany do bazy danych";
    }
    @RequestMapping("/remove")
    public ModelAndView show_remove(Model model){
        ModelAndView m = Singleton.getM();
        m.setViewName("remove.html");
        model.addAttribute("seanse",seansRepository.findAll());
        return m;
    }
    @RequestMapping("/usun_seans/{id}")
    public String delete_seans(@PathVariable Integer id){
        seansRepository.delete(seansRepository.findSeansById(id));
        return "Pomyślnie usunięto seans";
    }

    @RequestMapping("/add")
    public ModelAndView display_form() {
        ModelAndView m = Singleton.getM();
        m.setViewName("add.html");
        return m;
    }
    @RequestMapping("/")
    public ModelAndView repertuar(Model model){
        SimpleDateFormat godzina=Singleton.getGodzinaFormat();
        SimpleDateFormat dzien=Singleton.getDataFormat();
        String txt="";
        for (var i:
             seansRepository.findAllByOrderByDataAsc()) {
            txt+="<tr><td>";
            txt+=i.getTytul()+"</td><td>";
            txt+=dzien.format(i.getData())+"</td><td>";
            txt+= godzina.format(i.getData())+"</td><td>";
            txt+=i.getCzas()+"min.</td>";
            txt+="<td>Sala nr."+i.getSalaKinowa().getId()+"</td>";
            txt+="<td><a href='/rezerwacja_biletu/"+i.getId().toString()+"'><button>Rezerwuj</button></a></td>";
            txt+="</tr>";
        }
        model.addAttribute("seanse",txt);
        ModelAndView m = Singleton.getM();
        m.setViewName("repertuar.html");
        return m;
    }
    @RequestMapping("/rezerwacja_biletu/{id}")
    public ModelAndView book(@PathVariable Integer id, Model model){
        Seans wybrany=seansRepository.findSeansById(id);
        SalaKinowa sala=wybrany.getSalaKinowa();
        boolean wolne[][]=new boolean[sala.getRzędy()][sala.getMiejsca()];
        String rzedy="";
        for(int i=0;i<sala.getRzędy();i++){
            for(int j=0;j<sala.getMiejsca();j++){
                if(i==0) rzedy+="<th>"+(j+1)+"</th>";
                wolne[i][j]=true;
            }
        }
        for (var i:
             rezerwacjaRepository.findAll()) {
            if (i.getSeans().getId() == wybrany.getId()) {//Gdy ktoś ma rezerwację na ten sam seans, zaznaczamy, że miejsce tej osoby jest zajęte
                wolne[i.getRząd()-1][i.getMiejsce()-1] = false;
            }
        }
        ModelAndView m = Singleton.getM();
        m.setViewName("wybor.html");
        model.addAttribute("miejsca",wolne);
        model.addAttribute("rzedy",rzedy);
        model.addAttribute("seans",wybrany.getId());
        return m;
    }
    @PostMapping("/rezerwacja")
    public String dokonanie_rezerwacji(String imię,String nazwisko,Integer id_seansu,Integer miejsce){
        Rezerwacja rezerwacja=new Rezerwacja();
        rezerwacja.setImię(imię);
        rezerwacja.setNazwisko(nazwisko);
        rezerwacja.setSeans(seansRepository.findSeansById(id_seansu));
        int miejsceWrzędzie=miejsce%rezerwacja.getSeans().getSalaKinowa().getMiejsca();
        int rząd=(int)(miejsce/rezerwacja.getSeans().getSalaKinowa().getMiejsca()) + 1;
        if(miejsceWrzędzie==0)miejsceWrzędzie+=rezerwacja.getSeans().getSalaKinowa().getMiejsca();
        rezerwacja.setMiejsce(miejsceWrzędzie);
        rezerwacja.setRząd(rząd);
        rezerwacjaRepository.save(rezerwacja);
        return "Rezerwacja przebiegła pomyślnie<br><strong>Ważne:</strong>Szczegóły i anulacja rezerwacji znajduje się <a href='/rezerwacja/"+rezerwacja.getId()+"'>tutaj</a>";
    }
    @RequestMapping("/rezerwacja/{id}")
    public ModelAndView wyswietl_rezerwacje(@PathVariable Integer id,Model model){
        Rezerwacja rezerwacja=rezerwacjaRepository.findRezerwacjaById(id);
        SimpleDateFormat godzina=Singleton.getGodzinaFormat();
        SimpleDateFormat dzien=Singleton.getDataFormat();
        model.addAttribute("imie",rezerwacja.getImię());
        model.addAttribute("nazwisko",rezerwacja.getNazwisko());
        model.addAttribute("id",rezerwacja.getId());
        model.addAttribute("tytul",rezerwacja.getSeans().getTytul());
        model.addAttribute("sala",rezerwacja.getSeans().getSalaKinowa().getId());
        model.addAttribute("data",dzien.format(rezerwacja.getSeans().getData()));
        model.addAttribute("godzina",godzina.format(rezerwacja.getSeans().getData()));
        model.addAttribute("rzad",rezerwacja.getRząd());
        model.addAttribute("miejsce",rezerwacja.getMiejsce());
        ModelAndView m = Singleton.getM();
        m.setViewName("rezerwacja.html");
        return m;
    }
    @PostMapping("/rezerwacja/{id}/delete")
    public String delete(@PathVariable Integer id){
            rezerwacjaRepository.delete(rezerwacjaRepository.findRezerwacjaById(id));
            return "Pomyślnie anulowano rezerwację. Kliknij <a href='/'>tutaj</a> aby wrócić do strony głównej";
    }
}
