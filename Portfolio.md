## Introduction

Il s'agit d'un projet en groupe de quatre personnes réalisé dans le cadre du cours DIL de la HEIG-VD. Le but de ce projet est de créer un générateur de site statique en Java en utilisant des outils et une méthodologie de travail adaptée pour une bonne collaboration. Les membres de ce projet sont : Lazar Pavicevic, Valentin Kaelin, Alexandre Jaquier et Jonathan Friedli.

## Méthodologie de travail

Nous utilisons une méthodologie de travail agile reprenant certains aspects provenant de l'Extreme programming et du SCRUM que nous trouvons intéressants.

Nous avons choisi cette méthodologie car elle reflète la façon de travailler de notre groupe qui se veut flexible et itérative. De plus, pour ce genre de projet où les tâches sont dévoilées au fil des laboratoires, il est d'autant plus intéressant d'utiliser une approche incrémentale.

Chaque cycle de développement se fait en sprint d'une durée à peu près équivalente à celle des laboratoires liés au projet. Avant le début de chaque sprint, le groupe se concerte sur les tâches à effectuer et crée un Product backlog alimenté par des issues sur le repository du projet qui font office de stories. Il est important de noter que nous n'avons pas vraiment de Product owner à proprement parler. Les consignes des laboratoires substituent en quelque sorte cette fonction.

Les issues sont ensuite assignées et la phase de développement commence. A la fin du sprint, le groupe passe en revue le travail réalisé et valide les objectifs visés.

Comme nous découvrons des outils et des librairies au fur et à mesure que nous avançons dans le projet, nous profitons aussi de faire de la programmation en pair lorsqu'un membre du groupe rencontre un blocage dans ses tâches.

La rédaction du rapport et des commentaires du code se fait en français, le reste (commits, issues, nom des branches, nom des variables, etc) est en anglais.

### Traitement des issues

Nous commençons par créer des issues puis nous les assignons à un membre du groupe. Une fois l'issue résolue, la personne crée une pull request. Chaque pull request doit être approuvée par un autre membre du groupe avant d'être merge sur la branche Main. L'issue se clôture lorsque la pull request la concernant a été merge.

Pour le bon suivi des issues, nous utilisons un [Kanban](https://github.com/dil-classroom/projet-friedli_jaquier_kaelin_pavicevic/projects/3) avec trois colonnes : _To do, In progress_ et _Done_. En utilisant les fonctionnalités de Github, nous pouvons lier les issues au Kanban et mettre à jour leur état de résolution presque automatiquement. Une issue liée se trouve alors d'abord dans _To do_. Puis, une issue avec une draft de pull request se déplace ensuite sur _In Progress_. Finalement, une issue clôturée par une merged pull request finit dans la colonne _Done_.

### Description des commits

Tous nos commits sont faits en anglais, en s'inspirant des [Conventionnal Commits](https://www.conventionalcommits.org/en/v1.0.0/).  
Exemple:

```
feat: created commands clean and build for picocli
```

Cette méthodologie permet, en plus d'uniformiser le travail, de créer automatiquement des changelogs en fonction des divers commits.

### Organisation des branches

Nous utilisons la stratégie git flow qui consiste à isoler chaque tâche dans sa branche spécifique. Lorsqu'une issue est créée, une branche associée l'est aussi lorsqu'un développeur commence à travailler sur cette issue. Les préfixes des branches sont les suivants:

- `fb-` pour les issues concernant une nouvelle fonctionnalité
- `fix-` pour les issues concernant un bug à résoudre

Ces préfixes sont suivis d'une courte description de la tâche à réaliser.

### Critères d'acceptation des Pull Requests

Afin d'avoir des pulls requests acceptables nous respectons diverses règles :

- nom de la Pull Requests au même format que les commits
- le code doit compiler et résoudre le problème de l'issue affiliée
- un respect des conventions de nommage du langage Java (ex: variables en camelCase, nom des classes en majuscule)
- le code doit être bien formaté et dûment commenté
