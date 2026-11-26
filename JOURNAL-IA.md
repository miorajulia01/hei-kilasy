# JOURNAL D'UTILISATION DE L'IA — C2

## Échange 1

**Demande :** Analyser le projet pour préparer l'ajout d'un SMS simulé lors de la publication d'une note.

**Proposition :** Lecture du contrôleur existant, vérification de la requête SQL de correction et repérage du point d'envoi après l'email.

**Décision :** Gardé.

**Pourquoi :** La requête SQL récupère déjà `st.phone`, `st.email` et `st.full_name`. Aucune modification de la requête, aucune nouvelle classe Java et aucun nouveau dossier n'étaient nécessaires.

**Vérification :** La colonne `phone` est bien sélectionnée dans `SubmissionController.java` et la structure du projet reste inchangée.

## Échange 2

**Demande :** Décrire les points forts et les points faibles du projet par rapport au TD précédent.

**Proposition :** Analyse des points forts (règle de pénalité de C1 déjà appliquée de façon homogène, envoi de l'email regroupé dans la méthode de correction) et des points faibles (notifications dépendantes du contrôleur, email sans gestion d'erreur).

**Décision :** Gardé.

**Pourquoi :** Cette comparaison a permis de réutiliser le travail de C1 sans le réécrire et de choisir un point d'insertion minimal pour C2.

**Vérification :** Les notes pénalisées de C1 restent inchangées dans le contrôleur, le relevé et l'export CSV.

## Échange 3

**Demande :** S'assurer que l'échec du SMS n'interrompt pas la publication et que la note est enregistrée avant la notification.

**Proposition :** Maintien du `UPDATE` de la note avant les envois et encadrement de l'appel SMS par un `try/catch` avec un log d'échec.

**Décision :** Gardé.

**Pourquoi :** La note est déjà en base au moment du SMS. Sans `catch`, une panne du fournisseur SMS renverrait une erreur 500 alors que la note est publiée.

**Vérification :** Panne SMS simulée par une exception : la réponse du `PUT /submissions/{id}/grade` reste un 200 et le `GET /submissions/{id}` renvoie toujours la note.

## Échange 4

**Demande :** Vérifier s'il fallait aussi isoler l'envoi de l'email.

**Proposition :** Ajout d'un `try/catch` équivalent autour de l'email, afin que le SMS soit envoyé même si Mailpit est indisponible.

**Décision :** Gardé.

**Pourquoi :** L'étudiant reçoit au moins le SMS. Le changement reste local à la méthode de correction et ne modifie rien quand l'email fonctionne normalement.

**Vérification :** Panne de Mailpit simulée : le log `[EMAIL] échec d'envoi` apparaît, le log `[SMS]` suit, et la réponse reste un 200.

## Échange 5

**Demande :** Décider de la place du journal IA dans le dépôt GitHub.

**Proposition :** Ajout explicite de `JOURNAL-IA.md` au commit C2, en même temps que le fichier Java modifié.

**Décision :** Gardé.

**Pourquoi :** Le journal est un livrable pour la plateforme et sa trace dans le dépôt est utile. Un `git add` explicite évite d'ajouter d'autres fichiers au commit.

**Vérification :** `git status --short` affiche uniquement `SubmissionController.java` et `JOURNAL-IA.md` comme modifiés.

## Implémentation

- `SubmissionController.java` : interface `SmsSender` et implémentation simulée `ConsoleSmsSender` déclarées dans le fichier existant, puis injectées dans le contrôleur.
- Envoi du SMS après l'email, avec la note formatée en virgule décimale (`14,4/20.`) et le numéro de téléphone issu de la requête SQL existante.
- Email et SMS chacun dans un `try/catch` : un échec de l'un n'empêche pas l'autre ni la réponse HTTP 200.
- L'enregistrement de la note (`UPDATE`) reste avant les notifications.
- Aucune nouvelle classe Java ni aucun nouveau dossier : le fournisseur pourra être remplacé sans toucher au calcul de la note.

## Bilan

- **Fichiers concernés :** `src/main/java/hei/school/SubmissionController.java` et `JOURNAL-IA.md`.
- Structure du projet conservée : aucune réorganisation, aucune classe ni aucun dossier supplémentaire.
- Critères de C2 vérifiés : email identique à avant dans Mailpit, SMS simulé visible dans la console (`[SMS] +261 34 00 000 06 : Votre note pour « Tri à bulles » est disponible : 15,0/20.`), note publiée même en cas de panne SMS ou email.
- Les notes pénalisées de C1 restent inchangées dans le contrôleur, le relevé et l'export CSV.
