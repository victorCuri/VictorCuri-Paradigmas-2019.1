%1.

odd(N) :- 
M is mod(N,2),
M =\= 0.

%2.

hasN([],0).
hasN(L,N) :- 
N > 0,
N1 is N-1,
L = [_|T],
hasN(T,N1).

%3.

inc99([],[]).
inc99(L1,L2) :-
L1 = [H|T],
L2 = [H1|T1],
H1 is H + 99,
inc99(T,T1).

%4.

incN([],[],_).
incN(L1,L2,N) :-
L1 = [H|T],
L2 = [H1|T1],
H1 is H + N,
incN(T,T1,N).

%5.

comment([],[]).
comment(L1,L2) :-
L1 = [H|T],
L2 = [H1|T1],
string_concat("%% ",H,  H1),
comment(T,T1).

%6.

even(N) :- M is mod(N,2), M =:= 0.

onlyEven([],[]).
onlyEven(L1,L2) :-
L1 = [H|T],
even(H),
L2 = [H1|T1],
onlyEven(T,T1).

%7.

countdown(0,[]).
countdown(N,L) :-
N1 is N-1,
H is N,  
L = [H|T],
countdown(N1,T).

%8.

nRandoms(0,[]).
nRandoms(N,L) :-
N1 is N-1,
H is random(5),  
L = [H|T],
nRandoms(N1,T).

%9.

power(0,N,0):- N>0.
power(X,0,1):- X>0.
power(X,N,V):-X>0,N>0,N1 is N-1,power(X,N1,V1), V is V1*X.

potN0(0,[]).
potN0(N,L) :-
N1 is N-1,
power(2,N,H),
L = [H|T],
potN0(N1,T).

%10.

zipmult([],[],[]).
zipmult(L1,L2,L3) :-
L1 = [H|T],
L2 = [H1|T1],
L3 = [H2|T2],
H2 is H*H1,
zipmult(T,T1,T2).

%11.

potencias(0,[]).
potencias(N,L) :-
potN0(N,L),
reverse(L,L2,[]).

%12.

cedulas(0,_,[]).
cedulas(V,L1,L2) :-
L1 = [H|T],
V >= 0,
V2 is div(V, H),
L2 = [H1|T1],
H1 is V2,
V3 is V-V2*H,
cedulas(V3,T,T1).






