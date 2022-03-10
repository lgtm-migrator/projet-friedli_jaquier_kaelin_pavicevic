# Projet-DIL-Friedli_Jaquier_Kaelin_Pavicevic

## Introduction
Il s'agit d'un projet en groupe de quatre personnes réalisé dans le cadre du cours DIL de la HEIG-VD. Le but de ce projet est de créer un générateur de site statique en Java. Les membres de ce projet sont : Lazar Pavicevic, Valentin Kaelin, Alexandre Jaquier et Jonathan Friedli.

## Méthodologie de travail
Nous utilisons une méthodologie de travail agile. Chaque cycle de développement se fait en sprint d'une durée équivalente à celle des laboratoires liés au projet. Au début de chaque sprint, le groupe se concerte sur les tâches à effectuer et crée des issues sur le repository du projet qui font office de stories.

TODO

Nous commençons par créer des `issues` puis nous les assignons à un membre du groupe. Une fois l'issue résolue, la personne crée une PR. Chaque PR doit être approuvée par un autre membre du groupe avant d'être merge sur la branche Main. Nous avons fait le choix de créer une branche par issue. Cela permet de créer facilement des PR afin de résoudre ces issues.

Le rapport et les commentaires du code seront écrit en français, le reste (commits, issues, nom des branches, nom des variables, etc) seront en anglais.

Nous avons également un Kanban, nous permettant de suivre l'avancée de nos issue, qui peut être trouvé [ici](https://github.com/dil-classroom/projet-friedli_jaquier_kaelin_pavicevic/projects/3)

### Description des commits
Tous nos commits seront fait en anglais, en s'inspirant des [Conventionnal Commits](https://www.conventionalcommits.org/en/v1.0.0/).  
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
