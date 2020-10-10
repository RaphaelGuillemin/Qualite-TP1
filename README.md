# Travail pratique 1
##### Dans le cadre du cours qualité de logiciel et métriques
 _Travail de Laura Bégin et de Raphaël Guillemin_

# Exécution du projet
Ouvrir dans le terminal le dossier contenant le fichier `Qualite-TP1.jar`. Exécuter la commande :
```
java -jar Qualite-TP1.jar "<path-vers-dossier-contenant-fichiers-à-parser>"
```
Par exemple, la commande pourrait être : 
```
java -jar Qualite-TP1.jar "tests"
```
où "tests" est le path du dossier contenant les fichiers à parser.
À la fin de l'exécution, le programme va produire les fichiers `classes.csv` et `methodes.csv` contenant les métriques des classes et des méthodes contenues dans les fichiers .java du dossier.

# Précisions sur le projet
Voici différentes définitions que nous avons utilisées lors de la programmation de notre parser.
###### Lignes d'une classe
Toutes les lignes incluant les _import_, les _package_ jusqu'au `}` final.
###### Lignes d'une méthode
Toutes les lignes, de la définition au `}` final. 
Un commentaire qui est situé avant une méthode doit être collé à la méthode sans ligne vide entre les deux pour être considéré comme un commentaire de la méthode.
Les commentaires d'une méthode sont situés directement avant la méthode, ou à l'intérieur des `{}` ou sur la même ligne que le `}` final.
**Idem pour les classes**.
###### Cas spéciaux pour les lignes
Les lignes commençants par `@` sont considérées comme des commentaires.
On peut avoir un commentaire d'une seule ligne après le `}` de fin de classe et de fin de méthode, sur la même ligne que le `}` final
###### Conventions Java
On suppose que le programme java suit les conventions. Par exemple, `public void test(int a) {for(i=1; i <100; i++) a++;}` serait écrit comme :
```
public void test(int a) {
	for(i=1; i <100; i++) a++;
}
```
###### Enum et Interface
Enum et Interface sont considérés comme des classes.
###### Méthodes imbriquées 
Une méthode imbriqué dans une méthode (par exemple avec `@override`) est considérée comme une ligne de code de la méthode qui l'englobe.
###### Valeurs dégénérées
Pour les ratios comme classe_BC, dans le cas ou le classe_DC = 0 et/ou WMC = 0, nous avons laissé les valeurs NaN et Infinity.
Nous ne voulions pas forcer un classe_BC de 0 car cela représente mal la situation. S'il n'y a aucun commentaire et aucune classe,
un classe_BC de 0 ne reflète pas adéquatement la situation. La densité n'est pas mauvaise, la métrique n'a juste pas vraiment de sens.