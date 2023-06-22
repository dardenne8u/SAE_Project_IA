# Solution Personelle

---
## idée principale:

***Distance(im1, im2) = Σ (p1,p2∈pixels) (R(p1) − R(p2))² + (G(p1) − G(p2))² + (B(p1) − B(p2))²***

En observant la formule de calcul de la distance entre deux images,
j'ai soudainement réalisé que les valeurs des paramètres R, G et B ne sont pas interdépendantes. 
Autrement dit, je peux potentiellement diviser cette formule en trois parties distinctes 
et chercher la valeur minimale pour chaque partie. 
En ajoutant ces trois valeurs minimales, 
le résultat final *Distance* devrait également être minimal.

## Algorithme:
1.Tout d'abord, nous créons trois histogrammes, 
correspondant respectivement aux channels **R**, **G** et **B**,
et les stockons dans des Maps.

2.Remplir les Maps, 
le nombre de pixels associés à chaque valeur de **R**, **G** ou **B**.

3.Choisir aléatoirement **n** fois la valeur de x 
en fonction du nombre de couleurs, et 
les stocker dans une liste.

Par exemple, dans le cas de deux couleurs, 
**list_red** (dans Main_Perso c'est **x_reds**) contiendra deux valeurs aléatoires de x.

Maintenant, supposons que notre image ne comporte qu'un seul channel R 
et que notre objectif est de trouver les **n** couleurs 
les plus représentatives, les **n** valeurs entre 0 et 255.

**Comment trouver les chiffres les plus représentatifs 
pour des données d'un histogramme ?** 
Ma solution est de calculer la distance.

Supposons que notre chiffre le plus représentatif soit unique et 
que nous ayons une liste [1, 1, 2, 3, 4, 9]. Dans ce cas, 
nous pouvons établir l'équation de Distance = (1-x)² + (1-x)² + 
(2-x)² + (3-x)² + (4-x)² + (9-x)². C'est une équation quadratique 
à une variable, il est donc très facile de trouver la valeur minimale, 
qui correspondra au chiffre le plus représentatif.

Cependant, lorsque nous avons deux chiffres les plus représentatifs, 
la situation devient plus complexe. Supposons que les deux chiffres 
les plus représentatifs soient respectivement 2 (x1) et 3 (x2). 
Dans ce cas, le calcul de la distance devient 
Distance=(1-2)²+(1-2)²+(2-2)²+(3-3)²+(4-3)²+(9-3)², 
c'est-à-dire Distance=(1-x1)²+(1-x1)²+(2-x1)²+(3-x2)²+(4-x2)²+(9-x2)².

Bien sûr, ici 2 et 3 ne sont certainement pas les solutions optimales, 
il est donc nécessaire de trouver une méthode pour ajuster les valeurs de 
x1(2) et x2(3) de manière à minimiser la somme des distances par rapport 
aux autres données de l'histogramme monochrome.

La méthode que j'ai envisagée consiste à utiliser l'algorithme de descente de gradient.
Tout d'abord, on obtient aléatoirement les valeurs de x1 et x2. 
Ensuite, en fixant la valeur de x2, on calcule Distance(x1+dx) et 
Distance(x1-dx), puis on choisit la tendance qui réduit la distance, 
pareil pour x2.

Ce méthode est applicable lorsque l'on souhaite trouver n nombres les plus représentatifs.

Ensuite, on peut effectuer la même procédure pour le Map du channel G et le Map du channel B, 
en récupérant les données et en les sauvegardant dans les liste.

Pourquoi avons-nous besoin de trouver les nombres les plus représentatifs ? 
Vous souvenez-vous de la formule que nous utilisons 
pour calculer la distance entre deux images ?

***Distance(im1, im2) = Σ (p1,p2∈pixels) (R(p1) − R(p2))² + (G(p1) − G(p2))² + (B(p1) − B(p2))²***

Nous pouvons effectivement minimiser les valeurs des trois parties : 
(R(p1) - R(p2))², (G(p1) - G(p2))² et (B(p1) - B(p2))², 
afin de réduire au maximum la distance.