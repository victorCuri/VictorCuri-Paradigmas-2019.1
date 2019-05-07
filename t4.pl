 local_crime(quinta, ap).
 local_crime(sexta, ap).

 vitima(anita).
 vitima(bernado).

 insanidade(maria).
 insanidade(adriano).  

 pobre(bia).      
 pobre(pedro).   
 pobre(maria).      
 pobre(bernado).

 relacionou(anita,bernado).
 relacionou(bernado,caren).
 relacionou(anita,pedro).
 relacionou(pedro,alice).
 relacionou(alice,henrique).
 relacionou(henrique,maria).
 relacionou(maria,adriano).
 relacionou(adriano,caren).

 ciumes(X) :- relacionou(anita,X).
 ciumes(X) :- relacionou(anita,Y), relacionou(Y,X).

 data_local(segunda, sm, pedro).
 data_local(terca, sm, pedro).
 data_local(quarta, poa, pedro).
 data_local(quinta, sm, pedro).
 data_local(sexta,ap,pedro).

 data_local(segunda, poa, caren).
 data_local(terca, poa, caren).
 data_local(quarta, poa, caren).
 data_local(quinta, sm, caren).
 data_local(sexta, ap, caren).

 data_local(segunda, ap, henrique).
 data_local(terca, poa, henrique).
 data_local(terca, ap, henrique).

 data_local(segunda, ap, bia).
 data_local(terca, poa, bia).
 data_local(quarta, poa, bia).
 data_local(quinta, sm, bia).
 data_local(sexta, ap, bia).

 data_local(quarta, sm, adriano).
 data_local(quinta, ap, adriano).
 data_local(sexta, ap, adriano).

 data_local(segunda, ap, alice).
 data_local(terca, poa, alice).
 data_local(quarta, poa, alice).
 data_local(quinta, ap, alice).
 data_local(sexta, ap, alice).

 data_local(segunda, sm, bernado).
 data_local(terca, sm, bernado).
 data_local(quarta, poa, bernado).
 data_local(quinta, sm, bernado).
 data_local(sexta, ap, bernado).

 data_local(segunda, ap, maria).
 data_local(terca, sm, maria).
 data_local(quarta, sm, maria).
 data_local(quinta, sm, maria).
 data_local(sexta, ap, maria).

 arma(bastao, quinta, poa).
 arma(bastao, quarta, sm).
 arma(martelo, quinta, ap).
 arma(martelo, quarta, ap).

 chave(segunda, sm).
 chave(terca, poa).

 data_dia(X) :- data_local(DIA,ap,X), local_crime(DIA,ap).

 roubou_arma(X) :- arma(_,DIA,LOCAL), data_local(DIA,LOCAL,X).
 roubou_chave(X) :- chave(DIA,LOCAL), data_local(DIA,LOCAL,X).
 roubou_chave(bia).

 motivo(X) :- ciumes(X) ; insanidade(X) ; pobre(X).
 acesso(X) :- roubou_arma(X), data_dia(X), roubou_chave(X).

 assassino(X) :- motivo(X), acesso(X).
