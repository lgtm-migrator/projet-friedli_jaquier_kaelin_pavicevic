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

## Commandes disponibles

### `init`

Description : Cette commande permet d'initialiser un site statique dans le dossier de notre choix. Pour ce faire, il suffit de passer en argument le chemin relatif du dossier dans lequel on veut stocker notre site. Cela va permettre de créer/enrichir ce dossier. A l'intérieur de ce dernier, on trouvera un dossier contenant les différentes pages du site en format markdown, un dossier de templating, le fichier config.yml contenant des informations sur le site et la page d'accueil du site également au format markdown.  

Exemple :

```bash
boomshot init monNouveauSiteStatique
```
Cette commande va initialiser le site dans le dossier /monNouveauSiteStatique.
Exemple de contenu du dossier monNouveauSiteStatique après l'exécution de la commande :
```
│   config.yml
│   index.md
│
├───pages
│       image.jpeg
│       page.md
│
└───template
        layout.hbs
        menu.hbs
```

### `build`

Description : Cette command permet de créer le sous-dossier build, dans le dossier monNouveauSiteStatique, contenant les différentes pages du site.

Exemple :

```bash
boomshot build monNouveauSiteStatique
```
Si le dossier monNouveauSiteStatique n'existe pas (commande init pas effectuée ou dossier supprimé), cela produira une erreur.  

Exemple de contenu du dossier monNouveauSiteStatique après l'exécution de la commande :
```
│   config.yml
│   index.md
│
├───build
│   │   index.html
│   │
│   └───pages
│           image.jpeg
│           page.html
│
├───pages
│       image.jpeg
│       page.md
│
└───template
        layout.hbs
        menu.hbs
```

### `clean`

Description : Cette commande permet de supprimer le dossier monNouveauSiteStatique/build

Exemple :

```bash
boomshot clean monNouveauSiteStatique
```

Le dossier monNouveauSiteStatique est censé être pareil qu'après l'exécution de la commande ```init```.

### `serve`

Description : Cette commande permet de visualiser le résultat de la compilation de notre site statique. L'exécution de cette commande va build notre site puis nous afficher une URL permettant de voir notre site dans un navigateur web.  
Afin de quitter la visualisation, il suffit de taper `exit`.

Exemple :

```bash
boomshot serve monNouveauSiteStatique [-p 8888]
```
L'argument `-p` peut être remplacé par son équivalent `--port`.


## Manuel d'utilisation

Nous allons faire un petit guide pour vous aider à utiliser Boomshot.

### Initialisation d'un site statique
Nous allons créer un site qui s'appelle gymeo. Dans un premier temps, nous exécutons la commande suivante :  
 ```bash 
 $ Boomshot init gymeo
 ```
 Cette commande va créer le dossier du projet gymeo ainsi que sa structure. Ensuite nous allons modifier la page d'accueil `index.md` se trouvant à la racine du dossier `gymeo`.  
 Les trois première lignes du fchier sont des méta-données et tout ce qui est après les `"---"` sera le contenu de la page.  
 Exemple de nouvelle page :
 ```
 title: Gymeo
author: Vali la tornade technique
date: 02.06.2022
---
# Bienvenue sur Gymeo le site de gym qui vous permet de faire du sport.

![Une sportive](./images/sportive.jpg)
 ```
**Warning** Le chemin de l'image doit être relatif ou être une URL. 
Afin de garder l'arborescence du site assez propre, nous pouvons créer un dossier `images` afin d'y stocker les images.
Nous pouvons ensuite modifier/créer plus de page dans le dossier `pages`.   
Nous pouvons également modifier le fichier contenu dans le dossier `template`. Cela nous permet de modifier le menu du site ainsi que la structure de la page. Voila à quoi ressemble le fichier l'arborescence de notre dossier `gymeo` :
```
│   config.yml
│   index.md
│
├───images
│       sportive.jpg
│
├───pages
│       image.jpeg
│       page.md
│
└───template
        layout.hbs
        menu.hbs
```

### Build notre site

Une fois que nous avons fini avec ces modifications, nous allons build notre projet avec la commande suivante.

```bash
$ Boomshot build gymeo
```
Cela va créer le dossier `build` dans le dossier `gymeo`. Ce dossier contiendra les différentes pages du site au format html.
Cependant, si nous faisons des modifications sur notre site, nous devrions relancer la commande `build`. C'est pourquoi nous pouvons ajouter un tag à la commande `build`.
```bash
$ Boomshot build gymeo --watch
```
Cela aura pour effet de rebuild le site à chaque fois qu'une modification est détectée dans le dossier `gymeo`.

### Visualiser notre site

Afin de visualiser le site dans un navigateur internet, nous allons utiliser la commande suivante :

```bash
$ Boomshot serve gymeo
```
Cette commande va créer notre propre serveur web qui va permettre de visualiser notre site. Afin d'arrêter la visualisation, il suffit de taper `exit`.

### Clean notre site

Si, par la suite,  nous souhaitons supprimer le dossier `build`, il nous sufit de lancer la commande `clean`.

```bash
$ Boomshot clean gymeo
```