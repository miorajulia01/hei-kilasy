# JOURNAL D'UTILISATION DE L'IA — C1

## Échange 1 — 09:35

**Demande :** Analyser la structure du projet et m'aider à comprendre son organisation.

**Proposition :** Séparation des couches du projet (Controller, Service, Repository, Model), création d'un service centralisé de calcul des notes et ajout de tests JUnit.

**Décision :** Refusé et annulé.

**Pourquoi :** Le module précise que l'objectif n'est pas encore d'appliquer directement les principes de Clean Architecture et de Clean Code, mais de comprendre pourquoi ils sont nécessaires. Pour ce travail, je dois donc conserver la structure existante et modifier uniquement les fichiers nécessaires.

**Vérification :** Les modifications proposées ont été annulées et la structure initiale du projet a été conservée.

## Échange 2 — 10:10

**Demande :** Identifier les points forts et les points faibles du projet par rapport à l'implémentation de C1.

**Proposition :** Analyse de la structure du projet et identification des parties à modifier pour la pénalité de retard : calcul des notes, relevé de notes et export CSV.

**Décision :** Gardé.

**Pourquoi :** Cette analyse m'a permis de mieux comprendre où intégrer la fonctionnalité et de repérer une différence d'arrondi entre le bulletin et l'export CSV.

**Vérification :** Les trois critères d'acceptation de C1 ont été pris en compte : même note pénalisée partout, aucune pénalité pour un rendu à temps et aucune note négative.

## Échange 3 — 11:30

**Demande :** Comprendre pourquoi l'application ne démarrait pas avec Maven.

**Proposition :** Vérification de la configuration et des services nécessaires au démarrage de l'application, notamment PostgreSQL et Docker.

**Décision :** Gardé.

**Pourquoi :** L'application ne démarrait pas correctement, il fallait donc identifier et résoudre le problème avant de continuer.

**Vérification :** Le problème a été résolu. La commande `curl http://localhost:8080/exercises` a ensuite retourné les exercices de démonstration.

## Échange 4 — 11:50

**Demande :** Implémenter C1 en respectant la structure actuelle du projet, sans réorganisation.

**Proposition :** Modification de trois fichiers existants :

- `SubmissionController.java` : application de la pénalité lors de la correction et utilisation de la même note dans le mail.
- `GradeReport.java` : affichage de la note pénalisée et calcul de la moyenne à partir de ces notes.
- `CsvExport.java` : export de la même note pénalisée et correction du problème d'arrondi.

**Décision :** Gardé.

**Pourquoi :** Ces modifications permettent d'appliquer la même règle de pénalité dans les différents endroits de l'application sans créer de nouvelles couches ni modifier l'organisation du projet.

**Vérification :** À effectuer après l'implémentation, en vérifiant les trois critères d'acceptation de C1.

## Bilan

- **Fichiers concernés :** `src/main/java/hei/school/SubmissionController.java`, `src/main/java/hei/school/GradeReport.java` et `src/main/java/hei/school/CsvExport.java`.
- Aucune nouvelle classe Java ni aucun nouveau dossier créé.
- Le code existant a été conservé et adapté à la demande.
- Le problème de démarrage de l'application a été résolu.
- Les tests des critères d'acceptation restent à effectuer.