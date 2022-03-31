# Projet-DIL-Friedli_Jaquier_Kaelin_Pavicevic

## Introduction

Ce projet a été réalisé dans le cadre du cours DIL de la HEIG-VD. Il s'agit d'une
application en ligne de commande écrite en Java avec la
librairie [Picocli](https://picocli.info/) qui permet de faire de la génération de
sites statiques (SSG) à l'instar de [Gatsby](https://www.gatsbyjs.com/)
ou [Hugo](https://gohugo.io/).

Un portfolio décrivant notamment la méthodologie utilisée tout au long de ce projet
ainsi que les divers choix de processus logiciels réalisés est disponible à l'adresse
suivante: [Portfolio](https://github.com/dil-classroom/projet-friedli_jaquier_kaelin_pavicevic/wiki/Portfolio)

## Stack

* [Java 11](https://adoptium.net/?variant=openjdk11&jvmVariant=hotspot)
* [Picocli](https://picocli.info/)

## Fonctionnalités

L'application met à disposition des commandes permettant de générer des sites
statiques.

TODO : A compléter lorsque les commandes ont été implémentées.

## Installation

**MacOS/Linux:**

```bash
mvn clean install && unzip -o target/boomshot.zip
```

**Windows:** Utiliser git bash ou WSL ou faire un ``mvn clean install`` et unzip le
dossier à la main.

Afin d'ajouter l'application dans vos variables d'environnement:

```bash
export PATH=$PATH:`pwd`/boomshot/bin
```

Sous Windows, ajoutez le dossier ``/boomshot/bin`` dans le PATH de vos variables
d'environnement via l'interface graphique.

En fermant votre shell et en exécutant la commande ``boomshot``, une sortie de la
sorte devrait apparaître:

```bash
Usage: boomshot [-hV] [COMMAND]
A static site generator
  -h, --help      Show this help message and exit.
  -V, --version   Print version information and exit.
Commands:
  new    Init a new static website.
  clean  Clean the generated content.
  build  Build the static website.
  serve  Serve the static website.
```

### Commandes disponibles

`new`

Description :

Exemple :

```bash
boomshot new
```

`clean`

Description :

Exemple :

```bash
boomshot clean
```

`build`

Description :

Exemple :

```bash
boomshot build
```

`serve`

Description :

Exemple :

```bash
boomshot serve
```
