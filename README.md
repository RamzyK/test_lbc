# Test technique Android (kotlin) Leboncoin

Ce projet est un test technique dont le but est la création d’une application Android en Kotlin pour gérer une liste d’album en mode online et offline. Les données récupérées se trouvent [ici](https://static.leboncoin.fr/img/shared/technical-test.json)

L’application prend en charge une persistance des données grâce à une base de donnée locale Room, cette dernière est requêtée si une erreur dans l’appel http survient ou que l’application détecte qu’il n’y a aucune connectivité (Wi-Fi, 3G, 4G, etc.).

L’architecture du projet est un Model - View - ViewModel (MVVM) afin de séparer les responsabilité dans l’application pour permettre plus de lisibilité et de maintenabilité

On utilise [Glide](https://github.com/bumptech/glide) dans le projet pour gérer l’affichage des images depuis une url ainsi que la mise en cache des url pour ne pas systématiquement faire des appels réseau à chaque fois qu’on veut afficher une image


# Les écrans

L’application se divise en 3 écrans principaux:

- AlbumListActivity: Écran qui affiche la liste des albums, en les regroupant par albumId. Le clique sur un album affiche tous les album ayant le même albumId dans l’écran AlbumContentActivity

![Capture d’écran 2022-03-17 à 11 26 13](https://user-images.githubusercontent.com/38216742/158796025-6a56df82-4a3e-48a1-a414-cc71ddf6c39d.png)

- AlbumContentActivity: Écran affichant la liste des albums ayant le même albumId. Chaque cellule affiche le titre de l’album ainsi que l’image de ce dernier. Le clique sur un album affiche son détail

![Capture d’écran 2022-03-17 à 11 26 24](https://user-images.githubusercontent.com/38216742/158796036-e8aca898-af80-4341-ad54-ccd008df137b.png)

- AlbumDetailActivity: Écran affichant le détail d’un album.

![Capture d’écran 2022-03-17 à 11 26 29](https://user-images.githubusercontent.com/38216742/158796049-5c25e25a-d1d1-4a5a-b70e-2edd9c7d99c1.png)


# Programmation réactive

L’application utilise [RxKotlin](https://github.com/ReactiveX/RxKotlin) et les LiveData pour permettre la mise à jour de la vue en fonction des traitements faits au niveau du repository et du viewmodel.

# Client HTTP

L’application utilise [Retrofit](https://developer.android.com/training/data-storage/room) pour permettre de faire des requêtes HTTP et de mapper le JSON de réponse en DTO dans l’application.

# Injection de dépendances

Cette application utilise [Koin](https://insert-koin.io/) pour faire de l’injection de dépendances. La DI se fait dans le module src/main/java/com/example/lbc_albums/di.

Les modules à injecter sont divisés en 2 parties :

- DataModules: Instanciation de toute classe gérant de la donnée
- NetworkingModules: Instanciation de toute classe gérant la communication de l’application avec des services extérieurs

# Base de données

Une base de donnée locale [Room](https://developer.android.com/training/data-storage/room) est intégrée à l’application pour toujours avoir une copie des données récupérées par les appels à l’api afin de prévenir un éventuel problème du server ou un manque de connectivité.
Cette base de donnée est requêtée si aucune connexion au réseau n’est possible ou si une erreur survient lorsqu’on requête l’e endpoint pour avoir les données.

# Tests
L'application possède des test unitaires faits avec le framework JUnit ainsi que [Google Truth](https://github.com/google/truth) pour des assertions "humainement"  plus lisibles.
