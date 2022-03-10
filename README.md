# Projet-DIL-Friedli_Jaquier_Kaelin_Pavicevic

## Introduction
Il s'agit d'un projet en groupe de quatre personnes réalisé dans le cadre du cours DIL de la HEIG-VD. Le but de ce projet est de créer un générateur de site statique en Java en utilisant des outils et une méthodologie de travail adaptée pour une bonne collaboration. Les membres de ce projet sont : Lazar Pavicevic, Valentin Kaelin, Alexandre Jaquier et Jonathan Friedli.

## Méthodologie de travail
Nous utilisons une méthodologie de travail agile proche du SCRUM. Chaque cycle de développement se fait en sprint d'une durée équivalente à celle des laboratoires liés au projet. Au début de chaque sprint, le groupe se concerte sur les tâches à effectuer et crée des issues sur le repository du projet qui font office de stories. Les issues sont ensuite assignées et la phase de développement commence. A la fin du sprint, le groupe passe en revue le travail réalisé et valide les objectifs visés.

Comme nous décrouvons des outils et des librairies au fur et à mesure que nous avançons dans le projet, nous profitons de faire du Pair Programming lorsqu'un membre du groupe rencontre un blocage dans ses assignments.

Le rapport et les commentaires du code sont écrits en français, le reste (commits, issues, nom des branches, nom des variables, etc) sont en anglais.

// TODO Parler du manque de scrum master

### Traitement des issues
Nous commençons par créer des issues puis nous les assignons à un membre du groupe. Une fois l'issue résolue, la personne crée une Pull Request. Chaque Pull Request doit être approuvée par un autre membre du groupe avant d'être merge sur la branche Main. L'issue se clôt lorsque la Pull Request la concernant a été merge.

Pour le bon suivi des issues, nous utilisons un [Kanban](https://github.com/dil-classroom/projet-friedli_jaquier_kaelin_pavicevic/projects/3) avec trois colonnes : To do, In progress et Done. Les issues sont automatiquement placées dans les bonnes colonnes en fonction de leur état de résolution.

### Description des commits
Tous nos commits sont fait en anglais, en s'inspirant des [Conventionnal Commits](https://www.conventionalcommits.org/en/v1.0.0/).  
Exemple: 
```
feat: created commands clean and build for picocli  
```

Cette méthodologie permet, en plus d'uniformiser le travail, de créer automatiquement des changelogs en fonction des divers commits.

### Organisation des branches
Nous utilisons la stratégie git flow qui consiste à isoler chaque tâche dans sa branche spécifique. Lorsqu'une issue est créée, une branche associée l'est aussi lorsqu'un développeur commence à travailler sur cette issue. Les préfixes des branches sont les suivants:
* ``fb-`` pour les issues concernant une nouvelle fonctionnalité
* ``fix-`` pour les issues concernant un bug à résoudre

Ces préfixes sont suivis d'une courte description de la tâche à réaliser.

### Critères d'acceptation des Pull Requests
Afin d'avoir des pulls requests acceptables nous respectons diverses règles :
* le code doit compiler et résoudre le problème de l'issue affiliée
* un respect des conventions de nommage du langage Java (ex: variables en camelCase)
* un bon formattage du code
* nom de la Pull Requests au même format que les commits
