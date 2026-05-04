klinicko\_istrazivanje(***istrazivanje\_id*,** naziv, broj\_ispitanika)



protokol(***protokol\_id***, naziv, podaci, opis)



istrazivac(***istrazivac\_id***, ime, prezime, kvalifikacije)



istrazivanje\_dizajner(***istrazivanje\_id, istrazivac\_id***)



bolnica(***bolnica\_id***, naziv, adresa)



supstanca(***supstanca\_id***, naziv, hem\_sastav, izvor, napomena)



supstanca\_bolnica(***supstanca\_id, bolnica\_id***, kolicina, status)



istrazivanje\_supstanca(***istrazivanje\_id, supstanca\_id***)



tip\_instrumenta(***tip\_id***, naziv, opis)



instrument(***instrument\_id, bolnica\_id***, ***tip\_id***, datum\_nabavke, datum\_proizvodnje)



izvodjenje(***izvodjenje\_id, istrazivavnje\_id, bolnica\_id***, datum, status)



ucesce\_izvodjaca(***izvodjenje\_id, istrazivac\_id***, uloga, putanja\_beleski)



termin(***termin\_id, izvodjenje\_id***, datum, vreme\_pocetka, vreme\_zavrsetka)



utrosak\_termina(***termin\_id, insturment\_id, supstanca\_id***)







