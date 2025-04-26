% ----------------------------
% Basic Relationships (Facts)
% ----------------------------

% parent(Parent, Child)
parent(john, mary).
parent(john, tom).
parent(lisa, mary).
parent(lisa, tom).

parent(mary, alice).
parent(mary, ben).
parent(paul, alice).
parent(paul, ben).

parent(tom, chris).
parent(tom, emma).
parent(susan, chris).
parent(susan, emma).

parent(ben, david).
parent(sara, david).

% Gender facts
male(john).
male(tom).
male(paul).
male(ben).
male(chris).
male(david).

female(lisa).
female(mary).
female(susan).
female(sara).
female(alice).
female(emma).

% ----------------------------
% Derived Relationships (Rules)
% ----------------------------

% grandparent(Grandparent, Grandchild)
grandparent(GP, GC) :-
    parent(GP, P),
    parent(P, GC).

% sibling(Person1, Person2)
sibling(X, Y) :-
    parent(P, X),
    parent(P, Y),
    X \= Y.

% cousin(Person1, Person2)
cousin(X, Y) :-
    parent(P1, X),
    parent(P2, Y),
    sibling(P1, P2),
    X \= Y.

% child(Child, Parent)
child(C, P) :-
    parent(P, C).

% descendant(Person, Descendant)
descendant(X, Y) :-
    parent(X, Y).

descendant(X, Y) :-
    parent(X, Z),
    descendant(Z, Y).
