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



